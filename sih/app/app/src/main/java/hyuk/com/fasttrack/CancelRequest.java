package hyuk.com.fasttrack;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CancelRequest extends StringRequest {
    private static String URL = "http://54.164.52.65:3000/reservation/remove";
    private Map<String, String> parameters;

    public CancelRequest(String phone, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("phone", phone);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
