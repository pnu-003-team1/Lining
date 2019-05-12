package com.example.practice;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest{

    final static private String URL = "http://54.164.52.65:3000/users/join";
    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPassword, String userName, String userTel, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("email", userID);
        parameters.put("pw", userPassword);
        parameters.put("name", userName);
        parameters.put("phone", userTel);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
