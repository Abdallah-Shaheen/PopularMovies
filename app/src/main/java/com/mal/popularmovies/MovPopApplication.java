package com.mal.popularmovies;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by AbdAllah Shaheen on 8/30/2016.
 */
public class MovPopApplication extends Application {
    @Override
    public void onCreate() {

        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }
}
