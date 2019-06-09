package soo.fastrak_login;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Soo on 2019-04-05.
 */

public class LoginRequest extends StringRequest {
    final static private String URL = "http://54.164.52.65:3000/users/login";
    private Map<String, String> parameters;

    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("email", userID);
        parameters.put("pw", userPassword);
    }

    public Map<String, String> getParams(){return parameters;}
}
