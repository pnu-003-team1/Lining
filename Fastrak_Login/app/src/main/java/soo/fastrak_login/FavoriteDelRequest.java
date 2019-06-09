package soo.fastrak_login;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FavoriteDelRequest extends StringRequest {
    private static String URL = "http://54.164.52.65:3000/users/delfav";
    private Map<String ,String> parameters;

    FavoriteDelRequest(String bemail, String email, Response.Listener listener){
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("bemail", bemail);
        parameters.put("email", email);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}