package com.example.mohamedzoz.popularmovies.Provider;

import ckm.simple.sql_provider.UpgradeScript;
import ckm.simple.sql_provider.annotation.ProviderConfig;
import ckm.simple.sql_provider.annotation.SimpleSQLConfig;

/**
 * Created by Mohamed on 07/11/2016.
 */


@SimpleSQLConfig(
        name = "MovieProvider",
        authority = "just.some.movieprovider.authority",
        database = "movies.db",
        version = 1)

public class MovieProviderConfig implements ProviderConfig {
    @Override
    public UpgradeScript[] getUpdateScripts() {
        return new UpgradeScript[0];
    }
}
