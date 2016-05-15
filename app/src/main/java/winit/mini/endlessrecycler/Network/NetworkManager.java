package winit.mini.endlessrecycler.Network;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.androidannotations.annotations.EBean;
import org.json.JSONObject;

import winit.mini.endlessrecycler.Interfaces.VolleyResponseListener;

@EBean(scope = EBean.Scope.Singleton)
public class NetworkManager {
    JsonObjectRequest jsonObjRequest;
    private final String TAG_REQUEST = "TAG";
    RequestQueue mVolleyQueue;

    public void makeJsonObjectRequest(Context context, String url, JSONObject jsonBody, final VolleyResponseListener listener) {
        mVolleyQueue = Volley.newRequestQueue(context);
        jsonObjRequest = new JsonObjectRequest
                (url, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error);
                    }
                });

        //Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions. Volley does retry for you if you have specified the policy.
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonObjRequest.setTag(TAG_REQUEST);
        mVolleyQueue.add(jsonObjRequest);
    }

}
