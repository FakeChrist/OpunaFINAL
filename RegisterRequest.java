package aspx.ukindex.ac.brighton.httpswww.opuna2;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://opuna.co.uk/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String Firstname,String Lastname,int Age, String School, String Stype, String Email, String Username, String Password, Response.Listener<String> listener){

        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("Firstname", Firstname);
        params.put("Lastname", Lastname);
        params.put("Age", Age + "");
        params.put("School", School);
        params.put("Stype", Stype);
        params.put("Email", Email);
        params.put("Username", Username);
        params.put("Password", Password);
    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }
}
