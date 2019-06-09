package soo.fastrak_login;

/**
 * Created by Soo on 2019-05-12.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReservationRequest extends StringRequest {

    private static String URL ="http://54.164.52.65:3000/reservation/add";
    private Map<String, String> parameters;

    ReservationRequest(String bemail, String bname, String email, String phone, int total, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("bemail", bemail);
        parameters.put("bname", bname);
        parameters.put("email", email);
        parameters.put("phone", phone);
        parameters.put("total", Integer.toString(total));
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}