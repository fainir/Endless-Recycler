package winit.mini.endlessrecycler.Services;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import winit.mini.endlessrecycler.Models.Lottery;
import winit.mini.endlessrecycler.Models.PostBody;

public class MappingService {

    public static JSONObject postBodyToJSONObject(PostBody postBody) {
        Gson gson = new Gson();
        String json = gson.toJson(postBody);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonBody;
    }

    public static Lottery jsonObjectToLottery(JSONObject jsonObject ) {
        Gson gson = new Gson();
        return gson.fromJson(jsonObject.toString(), Lottery.class);
    }
}
