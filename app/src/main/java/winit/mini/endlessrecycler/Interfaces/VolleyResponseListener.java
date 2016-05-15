package winit.mini.endlessrecycler.Interfaces;

import com.android.volley.VolleyError;

import org.json.JSONObject;


public interface VolleyResponseListener {
    void onError(VolleyError message);

    void onResponse(JSONObject response);
}