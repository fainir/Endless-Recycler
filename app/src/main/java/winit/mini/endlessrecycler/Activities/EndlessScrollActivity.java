package winit.mini.endlessrecycler.Activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import winit.mini.endlessrecycler.Adapters.LotteriesDataAdapter;
import winit.mini.endlessrecycler.Interfaces.OnLoadMoreListener;
import winit.mini.endlessrecycler.Interfaces.VolleyResponseListener;
import winit.mini.endlessrecycler.Services.MappingService;
import winit.mini.endlessrecycler.R;
import winit.mini.endlessrecycler.Services.ApiService;
import winit.mini.endlessrecycler.Models.Lottery;
import winit.mini.endlessrecycler.Models.PostBody;
import winit.mini.endlessrecycler.Utils.AppUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_endless_scroller)
public class EndlessScrollActivity extends AppCompatActivity implements VolleyResponseListener, OnLoadMoreListener {

    @Bean
    ApiService apiService;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.recycler_view)
    RecyclerView lotteriesRecycler;

    private LotteriesDataAdapter dataAdapter;

    private int pageNumber;

    private List<Lottery> lotteries;

    protected Handler handler;

    private PostBody postBody;

    @AfterViews
    public void init() {
        initToolbar();
        setPostData();
        initRecycler();
        getLotteries();
    }

    public void initToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(getString(R.string.app_name));
            }
        }
    }

    public void initRecycler() {
        pageNumber = 0;
        lotteries = new ArrayList<>();
        handler = new Handler();
        lotteriesRecycler.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        lotteriesRecycler.setLayoutManager(layoutManager);
        dataAdapter = new LotteriesDataAdapter(lotteries, lotteriesRecycler, getApplicationContext());
        lotteriesRecycler.setAdapter(dataAdapter);
        getLotteries();
        dataAdapter.setOnLoadMoreListener(this);
    }

    private void getLotteries() {
        postBody.setPageIndex(pageNumber);
        apiService.getTwentyHotLotteries(this, MappingService.postBodyToJSONObject(postBody), this);
    }

    @Override
    public void onLoadMore() {
        //Add null , so the adapter will check view_type and show progress bar at bottom
        lotteries.add(null);
        dataAdapter.notifyItemInserted(lotteries.size() - 1);
        ++pageNumber;
        getLotteries();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(getString(R.string.lotteries_api_array_name));
            if (jsonArray != null) {
                if (pageNumber > 0) {
                    lotteries.remove(lotteries.size() - 1);
                    dataAdapter.notifyItemRemoved(lotteries.size());
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    lotteries.add(MappingService.jsonObjectToLottery(jsonArray.getJSONObject(i)));
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            dataAdapter.notifyItemInserted(lotteries.size());
                        }
                    });
                }
                dataAdapter.setLoaded();
            } else {
                Log.d("Lotteries: ", "null");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(VolleyError error) {
        if (error instanceof ParseError) {
            Log.d("VolleyError: ", error.getMessage());
            //No More Lotteries
            dataAdapter.showErrorMessage();
        } else if (error instanceof NetworkError) {
            Log.d("VolleyError: ", error.getMessage());
            AppUtils.showToastLong(this, getString(R.string.error_has_occurred));
        } else if (error instanceof ServerError) {
            Log.d("VolleyError: ", error.getMessage());
            AppUtils.showToastLong(this, getString(R.string.error_has_occurred));
        } else if (error instanceof AuthFailureError) {
            Log.d("VolleyError: ", error.getMessage());
            AppUtils.showToastLong(this, getString(R.string.error_has_occurred));
        } else if (error instanceof NoConnectionError) {
            Log.d("VolleyError: ", error.getMessage());
            AppUtils.showToastLong(this, getString(R.string.error_has_occurred));
        } else if (error instanceof TimeoutError) {
            Log.d("VolleyError: ", error.getMessage());
            AppUtils.showToastLong(this, getString(R.string.error_has_occurred));
        }
    }

    // Stub method for this exercise
    public void setPostData() {
        String[] countries = {"IL"};
        int minAge = 30;
        postBody = new PostBody(countries, minAge, pageNumber);
    }
}
