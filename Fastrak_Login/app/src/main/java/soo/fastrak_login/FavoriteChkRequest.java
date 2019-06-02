package soo.fastrak_login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FavoriteChkRequest extends StringRequest {
    private static String URL = "http://54.164.52.65:3000/users/isFav";
    private Map<String, String> parameters;

    FavoriteChkRequest(String bemail, String bname, String bphone, String baddr, String email, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("bemail", bemail);
        parameters.put("bname", bname);
        parameters.put("bphone", bphone);
        parameters.put("baddr", baddr);
        parameters.put("email", email);
    }

    public Map<String, String> getParams(){
        return parameters;
    }
}