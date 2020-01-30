package com.harilee.bsafe.Activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.harilee.bsafe.Model.NearbyModel;
import com.harilee.bsafe.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NearbyClass extends AppCompatActivity {

    @BindView(R.id.nearby_list)
    RecyclerView nearbyList;
    private NearbyAdapter adapter;
    private ArrayList<NearbyModel.Datum> nearbyModels = new ArrayList<>();
    private String TAG = "hello";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby);
        ButterKnife.bind(this);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.theme_white));
        adapter = new NearbyAdapter(this, nearbyModels);
        nearbyList.setAdapter(adapter);
        adapter.notifyDataSetChanged();



    }

    public void getResponse(NearbyModel nearbyModel) {

        Log.e(TAG, "onCreate: " + nearbyModel.getData().size());
        nearbyModels.addAll(nearbyModel.getData());


    }

    public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.ViewHolder> {


        private Context context;
        private ArrayList<NearbyModel.Datum> data;

        public NearbyAdapter(Context context, ArrayList<NearbyModel.Datum> nearbyModels) {

            this.data= nearbyModels;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.cab_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.adminId.setText(data.get(position).getAdminId());
            holder.distanceTv.setText(data.get(position).getDistance().toString());
            holder.nameTv.setText(data.get(position).getName());
            holder.vehicleNoTv.setText(data.get(position).getVehicleNumber());

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.name_tv)
            TextView nameTv;
            @BindView(R.id.vehicle_no_tv)
            TextView vehicleNoTv;
            @BindView(R.id.admin_id)
            TextView adminId;
            @BindView(R.id.distance_tv)
            TextView distanceTv;
            @BindView(R.id.nearby_card)
            CardView nearbyCard;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

}
