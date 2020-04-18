package com.example.phareaon.ui.map;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;

import com.example.phareaon.MainActivity;
import com.example.phareaon.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.phareaon.ui.list.dummy.DummyContent.loadJson;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        positionnementCameraInMap(mMap);
        addPhareInMap(mMap);
    }

    public void positionnementCameraInMap(GoogleMap map){
        CameraPosition googlePlex = CameraPosition.builder()
                .target(new LatLng(46.898887, 1.712814))
                .zoom(5)
                .bearing(0)
                .tilt(0)
                .build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 100, null);
    }

    public void addPhareInMap(GoogleMap map) {
        int height = 150;
        int width = 150;
        BitmapDrawable marker = (BitmapDrawable)getResources().getDrawable(R.drawable.marker_map);
        Bitmap bitmapMarker = marker.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(bitmapMarker, width, height, false);

        try {
            String str = loadJson();
            JSONObject jObjConnection = new JSONObject(str);
            JSONObject jsonBix = jObjConnection.getJSONObject("phares");
            JSONArray jsonA=jsonBix.getJSONArray("liste");

            for(int i =0 ; i < jsonA.length() ; i++) { // lecture
                JSONObject nomObj = (JSONObject) jsonA.get(i);


                LatLng latLng = new LatLng(nomObj.getDouble("lat"), nomObj.getDouble("lng"));

                map.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(nomObj.getString("name"))
                        .snippet(nomObj.getString("region"))
                        .zIndex(1000)
                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

                int period = nomObj.getInt("period");
                final Circle c = addCirclePhare(map,latLng);
                clignottementCercle(c, period);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Circle addCirclePhare(GoogleMap mMap, LatLng latLng) {
        return mMap.addCircle(new CircleOptions()
                .center(latLng)
                .strokeColor(getResources().getColor(R.color.colorCircleMap))
                .fillColor(getResources().getColor(R.color.colorCircleMap))
                .radius(100000));
    }

    public void clignottementCercle(final Circle c, int period) {
        Timer myTimer = new Timer();

        TimerTask myTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(c.getRadius() == 0) {
                            c.setRadius(100000);
                        } else {
                            c.setRadius(0);
                        }
                    }
                });
            }
        };
        myTimer.schedule(myTask, 0, period);
    }
}
