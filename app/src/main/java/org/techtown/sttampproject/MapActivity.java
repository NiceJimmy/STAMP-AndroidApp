package org.techtown.sttampproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.skt.Tmap.TMapTapi;
import com.skt.Tmap.TMapView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String TAG = Main2Activity.class.getSimpleName();



    private Location mLastKnownLocation;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private CameraPosition mCameraPosition;
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private final LatLng mDefaultLocation = new LatLng(37.56, 126.97);
    private static final int DEFAULT_ZOOM = 17;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    private static final int M_MAX_ENTRIES = 5;
    private String[] mLikelyPlaceNames;
    private String[] mLikelyPlaceAddresses;
    private String[] mLikelyPlaceAttributions;
    private LatLng[] mLikelyPlaceLatLngs;

    SupportMapFragment mapFragment;

//    double lat2;
//    double lon2;
     Marker m1;

    boolean bLog = false;
    Uri uri;
    String ID;

    TMapView tmapview;

    String address_1;
    String address_2;

    float lat;
    float lon;

    float current_lat;
    float current_lon;


    final Geocoder geocoder = new Geocoder(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        setContentView(R.layout.activity_map);


        Intent intent = getIntent();
        address_1 = intent.getStringExtra("address_1");
        address_2 = intent.getStringExtra("address_2");

        get_XY();




        tmapview = new TMapView(MapActivity.this);
        tmapview.setSKTMapApiKey("44af6a68-7a4f-46f0-a2c6-3e1b35aa450f");

        mGeoDataClient = Places.getGeoDataClient(this, null);
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        Intent intent3 = getIntent();
////        double lon = intent3.getDoubleExtra("Longitude", 0); // 더블이나 인트는 디폴드값을 지정해야한다.
////        double lat = intent3.getDoubleExtra("Latitude", 0);
//
//        double lon = 36.5;// 더블이나 인트는 디폴드값을 지정해야한다.
//        double lat = 38.6;
//
//        Log.d("lan2", "넘어온 위도값 : " + lat);
//        Log.d("lan2", "넘어온 위도값 : " + lon);
//
//        lat2 = lat;
//        lon2 = lon;

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) { // 화면전환시 액티비티에 해주는 사후처리인듯 하다.
        if (mMap != null) {                               // 추후 더 자세히 알아봅시다.
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }


    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override

            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_contents,
                        (FrameLayout) findViewById(R.id.map), false);

                TextView title = ((TextView) infoWindow.findViewById(R.id.title));
                title.setText(marker.getTitle());

                TextView snippet = ((TextView) infoWindow.findViewById(R.id.snippet));
                snippet.setText(marker.getSnippet());

                return infoWindow;
            }
        });


        getLocationPermission();


        updateLocationUI();


//        getDeviceLocation();


        MarkerOptions makerOptions = new MarkerOptions();
        makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.

                .position(new LatLng(lat, lon))
                .title("마커"); // 타이틀.



        m1 = mMap.addMarker(makerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(lat,lon), DEFAULT_ZOOM));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.equals(m1)) {

                    TMapTapi tMapTapi = new TMapTapi(MapActivity.this);

                    HashMap pathInfo = new HashMap();
                    pathInfo.put("rGoName", address_1+" "+address_2);
                    pathInfo.put("rGoX", String.valueOf(lon));
                    pathInfo.put("rGoY", String.valueOf(lat));

                    pathInfo.put("rStName", "출발지");
                    pathInfo.put("rStX", String.valueOf(current_lon));
                    pathInfo.put("rStY", String.valueOf(current_lat));


                    tMapTapi.invokeRoute(pathInfo);


                    tMapTapi.invokeNavigate(address_1+" "+address_2, lon,lat, 0, true);

                }

                return false;
            }
        });


    }


//
//    private void getDeviceLocation() {
//
//        try {
//            if (mLocationPermissionGranted) {
//                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
//                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Location> task) {
//                        if (task.isSuccessful()) {
//
//                            mLastKnownLocation = task.getResult();
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                                    new LatLng(mLastKnownLocation.getLatitude(),
//                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
//
//                            current_lat = (float)mLastKnownLocation.getLatitude();
//                            current_lon = (float)mLastKnownLocation.getLongitude();
//
//                        } else {
//                            Log.d(TAG, "Current location is null. Using defaults.");
//                            Log.e(TAG, "Exception: %s", task.getException());
//                            mMap.moveCamera(CameraUpdateFactory
//                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
//                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
//                        }
//                    }
//                });
//            }
//        } catch (SecurityException e) {
//            Log.e("Exception: %s", e.getMessage());
//        }
//    }

    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

//    private void showCurrentPlace() {
//        if (mMap == null) {
//            return;
//        }
//
//        if (mLocationPermissionGranted) {
//
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//                return;
//            }
//            final Task<PlaceLikelihoodBufferResponse> placeResult =
//                    mPlaceDetectionClient.getCurrentPlace(null);
//            placeResult.addOnCompleteListener
//                    (new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
//                        @SuppressLint("RestrictedApi")
//                        @Override
//                        public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
//                            if (task.isSuccessful() && task.getResult() != null) {
//                                PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();
//
//                                // Set the count, handling cases where less than 5 entries are returned.
//                                int count;
//                                if (likelyPlaces.getCount() < M_MAX_ENTRIES) {
//                                    count = likelyPlaces.getCount();
//                                } else {
//                                    count = M_MAX_ENTRIES;
//                                }
//
//                                int i = 0;
//                                mLikelyPlaceNames = new String[count];
//                                mLikelyPlaceAddresses = new String[count];
//                                mLikelyPlaceAttributions = new String[count];
//                                mLikelyPlaceLatLngs = new LatLng[count];
//
//                                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
//
//                                    mLikelyPlaceNames[i] = (String) placeLikelihood.getPlace().getName();
//                                    mLikelyPlaceAddresses[i] = (String) placeLikelihood.getPlace()
//                                            .getAddress();
//                                    mLikelyPlaceAttributions[i] = (String) placeLikelihood.getPlace()
//                                            .getAttributions();
//                                    mLikelyPlaceLatLngs[i] = placeLikelihood.getPlace().getLatLng();
//
//                                    i++;
//                                    if (i > (count - 1)) {
//                                        break;
//                                    }
//                                }
//
//
//                                likelyPlaces.release();
//
//
//                                openPlacesDialog();
//
//                            } else {
//                                Log.e(TAG, "Exception: %s", task.getException());
//                            }
//                        }
//                    });
//        } else {
//
//            Log.i(TAG, "The user did not grant location permission.");
//
//
//            mMap.addMarker(new MarkerOptions()
//                    .title(getString(R.string.default_info_title))
//                    .position(mDefaultLocation)
//                    .snippet(getString(R.string.default_info_snippet)));
//
//
//            getLocationPermission();
//        }
//    }

//    private void openPlacesDialog() {
//
//        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                LatLng markerLatLng = mLikelyPlaceLatLngs[which];
//                String markerSnippet = mLikelyPlaceAddresses[which];
//                if (mLikelyPlaceAttributions[which] != null) {
//                    markerSnippet = markerSnippet + "\n" + mLikelyPlaceAttributions[which];
//                }
//
//
//                mMap.addMarker(new MarkerOptions()
//                        .title(mLikelyPlaceNames[which])
//                        .position(markerLatLng)
//                        .snippet(markerSnippet));
//
//
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng,
//                        DEFAULT_ZOOM));
//            }
//        };
//
//
//        AlertDialog dialog = new AlertDialog.Builder(this)
//                .setTitle(R.string.pick_place)
//                .setItems(mLikelyPlaceNames, listener)
//                .show();
//    }


    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public void get_XY(){

        List<Address> list = null;

        try {
            list = geocoder.getFromLocationName(
                    address_1, // 지역 이름
                    10); // 읽을 개수
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("test","입출력 오류 - 서버에서 주소변환시 에러발생");
        }

        if (list != null) {
            if (list.size() == 0) {
//                tv2.setText("해당되는 주소 정보는 없습니다");
            } else {


                lat = (float)list.get(0).getLatitude();
                lon = (float)list.get(0).getLongitude();

                Log.d("latlon","위도,경도 : " + lat+" "+lon);

            }
        }



    }


}
