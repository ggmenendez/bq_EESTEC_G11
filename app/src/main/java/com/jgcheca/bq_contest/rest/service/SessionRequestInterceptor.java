package com.jgcheca.bq_contest.rest.service;

import android.util.Base64;

import retrofit.RequestInterceptor;

/**
 * Created by asus on 03/06/2015.
 */
public class SessionRequestInterceptor implements RequestInterceptor {


    @Override
    public void intercept(RequestFacade request) {

        /*String credentials = "Jose.GCheca@gmail.com:bqcontest";
        String string = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        request.addHeader("Authorization", string);
        request.addHeader("Accept", "application/json");*/
    }
}
