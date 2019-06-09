package soo.fastrak_login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FavoYHRequest extends StringRequest {
    private Map<String, String> parameters;

    FavoYHRequest(String URL, Response.Listener<String> listener){
        super(Method.GET, URL, listener, null);

        parameters = new HashMap<>();
    }

    public Map<String, String> getParams(){
        return parameters;
    }
}