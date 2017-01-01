package com.example.berkay.weather5day;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.berkay.weather5day.R.id.descriptionText;

/**
 * Created by Berkay on 1.01.2017.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.MyViewHolder> {
    private Context mContext;
    private List<WeatherModel.ListBean> mWeatherList;

    public WeatherAdapter(Context context, List<WeatherModel.ListBean> weather){
        mContext = context;
        mWeatherList = weather;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.rec_row, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return  vh;
    }

    @Override
    public int getItemCount() {
        return mWeatherList.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.timeText.setText(mWeatherList.get(position).getDt_txt());
        holder.mainText.setText(mWeatherList.get(position).getWeather().get(0).getMain()
                +", "+mWeatherList.get(position).getWeather().get(0).getDescription());
        int temp = (int)(mWeatherList.get(position).getMain().getTemp() - 273.15);
        holder.tempText.setText(String.valueOf(temp+"°C"));
        String url = "http://openweathermap.org/img/w/"+mWeatherList.get(position).getWeather().get(0).getİcon()+".png";
        Picasso.with(mContext)
                .load(url)
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.icon);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView timeText, mainText, tempText;
        public ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            timeText = (TextView) itemView.findViewById(R.id.timeText);
            mainText = (TextView) itemView.findViewById(R.id.mainText);
            tempText = (TextView) itemView.findViewById(descriptionText);
            icon = (ImageView) itemView.findViewById(R.id.iconImg);

        }
    }
}
