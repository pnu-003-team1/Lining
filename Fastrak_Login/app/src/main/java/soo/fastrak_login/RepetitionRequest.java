package soo.fastrak_login;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Soo on 2019-04-07.
 */

public class RepetitionRequest extends StringRequest {
    final static private String URL = "http://54.164.52.65:3000/users/repetition";
    private Map<String, String> parameters;

    public RepetitionRequest(String userID, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("email", userID);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
