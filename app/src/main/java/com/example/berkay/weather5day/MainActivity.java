package com.example.berkay.weather5day;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button mSendButton;
    private TextView mInfoText;
    private EditText mCityText;
    private RecyclerView mRecyclerView;
    private WeatherModel myWeatherModel;
    private WeatherAdapter adapter;
    private ProgressDialog progress;
    ApiService myApi;

    private static String BASE_URL = "http://api.openweathermap.org/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSendButton = (Button)findViewById(R.id.sendRequest);
        mInfoText = (TextView) findViewById(R.id.resultText);
        mCityText = (EditText) findViewById(R.id.cityName);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        progress = new ProgressDialog(this);
        progress.setTitle("Connecting");
        progress.setMessage("Please Wait...");
        progress.setCancelable(false);
        final Retrofit rf = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();


        final Call<WeatherModel> call;
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.show();
                myApi = rf.create(ApiService.class);
                Call<WeatherModel> modelCall = myApi.getWeatherService(mCityText.getText().toString(), "9b19d60baa38c692f1bdb4db53e2d0ce");
                modelCall.enqueue(new Callback<WeatherModel>() {
                    @Override
                    public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                        mInfoText.setText("Results for "+response.body().getCity().getName()+", "+response.body().getCity().getCountry());
                        adapter = new WeatherAdapter(MainActivity.this, response.body().getList());
                        RecyclerView.LayoutManager manager = new LinearLayoutManager(MainActivity.this);
                        mRecyclerView.setLayoutManager(manager);
                        mRecyclerView.setAdapter(adapter);
                        progress.dismiss();
                    }

                    @Override
                    public void onFailure(Call<WeatherModel> call, Throwable t) {
                        progress.dismiss();
                        mInfoText.setText("ERROR");
                    }
                });
            }
        });
    }
}
