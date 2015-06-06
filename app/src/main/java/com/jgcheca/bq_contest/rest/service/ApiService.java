package com.jgcheca.bq_contest.rest.service;

import com.google.gson.JsonObject;
import com.jgcheca.bq_contest.User;
import com.jgcheca.bq_contest.rest.model.AppKiuwan;

import java.util.List;


import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;

/**
 * Created by asus on 03/06/2015.
 */
public interface ApiService {

    public static final String BASE_URL = "https://api.kiuwan.com";

    @GET("/info")
    void login(@Header("Authorization")String hash, Callback<JsonObject> session);

    @GET("/users")
    void getUsers(Callback<List<User>> cb);


    @GET("/apps/list")
    void getApps(Callback<List<AppKiuwan>> cb);
}
