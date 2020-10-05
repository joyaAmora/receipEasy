package com.example.receipeasy.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.receipeasy.api.MarthaQueue;
import com.example.receipeasy.api.MarthaRequest;
import com.example.receipeasy.api.UserRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UserService {
    private static UserService instance = null;

    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    private UserService() {
        currentUser = null;
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }

        return instance;
    }

    public void logIn(String username, String password, final UserRequestListener listener, Context context) {
        JSONObject loginParams = new JSONObject();
        try {
            loginParams.put("username", username);
            loginParams.put("password", password);
        }
       catch (JSONException e) {
            e.printStackTrace();
       }
        MarthaRequest loginRequest = new MarthaRequest("select-user-login", loginParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject userJson = response.getJSONArray("data").getJSONObject(0);

                    currentUser = new User(userJson);
                    listener.onResponse(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onResponse(false);
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Recipeasy", "onErrorResponse: " + error.toString());
                listener.onResponse(false);
            }
    });

        MarthaQueue.getInstance(context).send(loginRequest);

    }

    public void signUp(final String username, String password, final UserRequestListener listener, Context context) {
        if (username.isEmpty() || password.isEmpty()) {
            listener.onResponse(false);
        }
        else {
            JSONObject signUpParams = new JSONObject();
            try {
                signUpParams.put("username", username);
                signUpParams.put("password", password);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            MarthaRequest signupRequest = new MarthaRequest("insert-user-signup", signUpParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        int id = response.getInt("lastInsertId");

                        currentUser = new User(id, username);
                        listener.onResponse(true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        listener.onResponse(false);
                    }

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Recipeasy", "onErrorResponse: " + error.toString());
                    listener.onResponse(false);
                }
            });
            MarthaQueue.getInstance(context).send(signupRequest);
        }
    }

    public void logOut() {
        currentUser = null;
    }
}
