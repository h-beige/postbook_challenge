package com.example.postbookchallenge.domain.favourites;

import android.content.SharedPreferences;

import com.example.postbookchallenge.PostBookChallengeApplication;
import com.example.postbookchallenge.domain.entities.PostEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;

import static com.example.postbookchallenge.infrastructure.PreferenceKeys.PREF_FAVOURITE;

@Singleton
public class Favourites {

    private static Set<String> favouritePostIds;

    @Inject
    Favourites() {
    }

    @NonNull
    public Set<String> getFavouritePosts() {
        if(favouritePostIds != null) {
            return favouritePostIds;
        }

        SharedPreferences preferences = PostBookChallengeApplication.getPreferences();
        if(!preferences.contains(PREF_FAVOURITE)) {
            return setFavouritesPosts(new HashSet<>());
        }

        String json = preferences.getString(PREF_FAVOURITE, null);
        if(json == null) {
            return setFavouritesPosts(new HashSet<>());
        }

        Type setType = new TypeToken<HashSet<String>>(){}.getType();
        favouritePostIds = new Gson().fromJson(json, setType);

        return favouritePostIds;
    }

    private Set<String> setFavouritesPosts(@NonNull Set<String> newFavouritePostIds) {

        favouritePostIds = newFavouritePostIds;

        SharedPreferences preferences = PostBookChallengeApplication.getPreferences();
        SharedPreferences.Editor editor = preferences.edit();

        String json = new Gson().toJson(favouritePostIds);
        editor.putString(PREF_FAVOURITE, json);

        editor.apply();

        return favouritePostIds;
    }

    public boolean isFavourite(PostEntity postEntity) {
        Set<String> favouritePostIds = getFavouritePosts();
        return favouritePostIds.contains(postEntity.getKey());
    }

    public void setFavourite(PostEntity postEntity, boolean favourite) {
        Set<String> favouritePostIds = getFavouritePosts();
        if(favourite) {
            favouritePostIds.add(postEntity.getKey());
        }
        else {
            favouritePostIds.remove(postEntity.getKey());
        }

        setFavouritesPosts(favouritePostIds);
    }
}
