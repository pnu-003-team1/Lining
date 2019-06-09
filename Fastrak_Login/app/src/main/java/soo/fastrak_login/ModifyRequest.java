package soo.fastrak_login;

/**
 * Created by Soo on 2019-05-12.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ModifyRequest extends StringRequest {
    final static private String URL = "http://54.164.52.65:3000/users/modify";
    private Map<String, String> parameters;

    public ModifyRequest(String userID, String userPassword, String userTel, String userName, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("email", userID);
        parameters.put("pw", userPassword);
        parameters.put("phone", userTel);
        parameters.put("name", userName);
    }

    public Map<String, String> getParams() {return parameters;}
}
