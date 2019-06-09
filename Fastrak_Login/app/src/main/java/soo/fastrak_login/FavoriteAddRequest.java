package soo.fastrak_login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FavoriteAddRequest extends StringRequest{
    private static String URL = "http://54.164.52.65:3000/users/favorite";
    private Map<String, String> parameters;

    FavoriteAddRequest(String bemail, String bname, String baddr, String bphone, String email, double latitude, double longitude, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("bemail", bemail);
        parameters.put("bname", bname);
        parameters.put("baddr", baddr);
        parameters.put("bphone", bphone);
        parameters.put("email", email);
        parameters.put("latitude", latitude + "");
        parameters.put("longitude", longitude + "");
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}