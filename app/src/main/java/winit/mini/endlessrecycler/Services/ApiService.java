package winit.mini.endlessrecycler.Services;

import android.content.Context;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.json.JSONObject;

import winit.mini.endlessrecycler.Core.AppConstants;
import winit.mini.endlessrecycler.Interfaces.VolleyResponseListener;
import winit.mini.endlessrecycler.Network.NetworkManager;

@EBean(scope = EBean.Scope.Singleton)
public class ApiService {

    @Bean
    NetworkManager networkManager;

    public void getTwentyHotLotteries(Context context, JSONObject jsonBody, final VolleyResponseListener listener) {
        networkManager.makeJsonObjectRequest(context, AppConstants.Http.GET_HOT, jsonBody, listener);
    }
}
