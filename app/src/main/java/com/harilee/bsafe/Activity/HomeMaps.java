package com.harilee.bsafe.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.harilee.bsafe.Model.AssignCabModel;
import com.harilee.bsafe.Model.NearbyModel;
import com.harilee.bsafe.Model.PathModel;
import com.harilee.bsafe.Model.RideCompleted;
import com.harilee.bsafe.R;
import com.harilee.bsafe.Utils.Config;
import com.harilee.bsafe.Utils.Utility;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeMaps extends FragmentActivity implements OnMapReadyCallback, Presenter.MapsInterface {

    private static final int ERROR_DIALOG_REQUEST = 9001;
    @BindView(R.id.ic_gps)
    ImageView icGps;
    @BindView(R.id.drawer_main)
    DrawerLayout drawerMain;
    @BindView(R.id.help_line_bt)
    Button helpLineBt;
    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout pullToRefresh;
    @BindView(R.id.account)
    ImageView account;
    @BindView(R.id.ride_completed_bt)
    Button rideCompletedBt;
    @BindView(R.id.card_view)
    CardView cardView;
    private Presenter presenter;
    private ArrayList markerPoints = new ArrayList();
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private String TAG = "Map";
    private Dialog dialog;
    private boolean isReferesh = false;
    private Location currentLocation;
    public ArrayList<NearbyModel.Datum> nearbyModels = new ArrayList<>();
    private RoundedBottomSheetDialog mBottomSheetDialog;
    private LatLng mOrigin, mDestination;
    private Polyline mPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_maps);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.e("TAG", "onCreate: BOUNCE " + Utility.getUtilityInstance().getPreference(HomeMaps.this
                , Config.FCM));
        Log.e(TAG, "onCreate: " + refreshedToken);
        ButterKnife.bind(this);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.theme_white));
        dialog = new Dialog(this);
        presenter = new Presenter(this, HomeMaps.this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        releaseRefresh();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        pullToRefresh.setOnRefreshListener(() -> {

            presenter.getPath(currentLocation.getLatitude(), currentLocation.getLongitude()
                    , Utility.getUtilityInstance().getPreference(this, Config.LAT)
                    , Utility.getUtilityInstance().getPreference(this, Config.LNG));
            pullToRefresh.setRefreshing(false);
        });
        if (isServicesOK()) {
            getLocationPermission();

        }
    }


    @OnClick({R.id.ic_gps, R.id.help_line_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_gps:
                getDeviceLocation();
                break;
            case R.id.help_line_bt:
                isReferesh = true;
                getCabNearby();
                releaseRefresh();
                break;
        }
    }


    @OnClick(R.id.account)
    public void onViewClicked() {
        startActivity(new Intent(HomeMaps.this, Profile.class));
    }

    private void releaseRefresh() {
        if (isReferesh) {
            pullToRefresh.setEnabled(true);
        } else {
            pullToRefresh.setEnabled(false);
        }
    }

    private void getCabNearby() {

        if (currentLocation != null)
            Log.e(TAG, "getCabNearby: " + currentLocation.getLongitude() + currentLocation.getLatitude());
        Utility.showGifPopup(this, true, dialog);
        presenter.searchCab(currentLocation.getLatitude(), currentLocation.getLongitude());

    }


    @Override
    public void showMessages(String message) {
        Utility.showGifPopup(this, false, dialog);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cancelLoader() {
        Utility.showGifPopup(this, false, dialog);
    }

    @Override
    public void getResponse(NearbyModel nearbyModel) {
        Log.e(TAG, "getResponse: " + nearbyModel.getData().get(0).getAdminId());
        nearbyModels.clear();
        nearbyModels.addAll(nearbyModel.getData());
        mBottomSheetDialog = new RoundedBottomSheetDialog(this);
        View sheetView = getLayoutInflater().inflate(R.layout.nearby, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

        RecyclerView recyclerView = sheetView.findViewById(R.id.nearby_list);
        NearbyAdapter nearbyAdapter = new NearbyAdapter(this, nearbyModels);
        recyclerView.setAdapter(nearbyAdapter);


    }


    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        FrameLayout mapframe = findViewById(R.id.map);
        mapFragment.getMapAsync(this);
        // mapFragment.getMapAsync(HomeScreen.this);
    }

    public boolean isServicesOK() {

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(HomeMaps.this);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            //  Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            // Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(HomeMaps.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            showMessages("You can't make map request");
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

        }
    }


    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGranted) {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM);

                        } else {
                            Log.d(TAG, "onComplete: current location is null");

                            showMessages("Unable to get current location");
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        releaseRefresh();
        isReferesh = false;

    }

    @Override
    protected void onStart() {
        super.onStart();
        isReferesh = false;
        releaseRefresh();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }


    public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.ViewHolder> {


        private Context context;
        private ArrayList<NearbyModel.Datum> data;

        public NearbyAdapter(Context context, ArrayList<NearbyModel.Datum> nearbyModels) {

            this.data = nearbyModels;
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
            holder.nearbyCard.setOnClickListener(v -> {
                assignCab(data.get(position).getAdminId(), data.get(position).getFcmToken()
                        , data.get(position).getLat(), data.get(position).getLng());
            });

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

    private void assignCab(String adminId, String fcmToken, String lat, String lng) {

        Utility.showGifPopup(this, true, dialog);
        Utility.getUtilityInstance().setPreference(this, Config.LAT, lat);
        Utility.getUtilityInstance().setPreference(this, Config.LNG, lng);
        Utility.getUtilityInstance().setPreference(this, Config.ADMIN_ID, adminId);
        Utility.getUtilityInstance().setPreference(this, Config.FCM_TOKEN, fcmToken);
        presenter.assignCab(adminId
                , Utility.getUtilityInstance().getPreference(this, Config.NUM)
                , fcmToken);
    }

    @Override
    public void getResponse(AssignCabModel assignCabModel) {


        Utility.showGifPopup(this, false, dialog);
        mBottomSheetDialog.cancel();
        LatLng destination = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        LatLng origin = new LatLng(Double.parseDouble(assignCabModel.getLat())
                , Double.parseDouble(assignCabModel.getLng()));

        showMessages("Finding the shortest path...");
        setMarker(origin, destination);

    }

    private void setMarker(LatLng origin, LatLng destination) {
        // Creating MarkerOptions
        MarkerOptions options = new MarkerOptions();
        // Setting the position of the marker
        options.position(destination);

        MarkerOptions originMarker = new MarkerOptions();
        originMarker.position(origin);

        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        originMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        // Add new marker to the Google Map Android API V2
        mMap.addMarker(options);
        mMap.addMarker(originMarker);
    }

    @Override
    public void getResponse(PathModel pathModel) {

//        rideCompletedBt.setOnClickListener(v ->{
//            presenter.rideCompleted(Utility.getUtilityInstance().getPreference(this, Config.ADMIN_ID)
//            , Utility.getUtilityInstance().getPreference(this, Config.FCM_TOKEN));
//        });
        if (pathModel.getSuccess().equalsIgnoreCase("yes")) {
            {
                cardView.setVisibility(View.GONE);
                rideCompletedBt.setVisibility(View.VISIBLE);
                final long period = 10000;
                new Timer().schedule(new TimerTask() {
                    int i = 0;

                    @Override
                    public void run() {
                        if (pathModel.getPath().size() > 0) {
                            updateMarker(pathModel, i);
                            i++;
                        }
                    }
                }, 1000, period);
            }
        }
    }

    @Override
    public void getResponse(RideCompleted rideCompleted) {

    }

    private void updateMarker(PathModel pathModel, int i) {

        String pathStr = pathModel.getPath().get(i);
        String[] arrOfPath = pathStr.split("@", 2);
        Log.e(TAG, "updateMarker: "+arrOfPath[0] );
        Log.e(TAG, "updateMarker: "+arrOfPath[1] );
        LatLng newOrigin = new LatLng(Double.parseDouble(arrOfPath[0])
                , Double.parseDouble(arrOfPath[1]));
        LatLng destination = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        setMarker(newOrigin, destination);

    }


}
