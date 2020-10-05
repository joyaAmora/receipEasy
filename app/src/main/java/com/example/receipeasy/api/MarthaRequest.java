package com.example.receipeasy.api;

import android.util.Base64;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MarthaRequest extends JsonObjectRequest {
    private final static String AUTH = ("cGhhbmV1Zjp4amtrZ25jaw==");

    private static String getUrl(String query) {
        return String.format("http://martha.jh.shawinigan.info/queries/%s/execute", query);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers =  new HashMap<>();
        headers.put("auth", AUTH);

        return headers;
    }

    public MarthaRequest(String query, @Nullable JSONObject jsonRequest, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.POST, getUrl(query), jsonRequest, listener, errorListener);
    }
}
