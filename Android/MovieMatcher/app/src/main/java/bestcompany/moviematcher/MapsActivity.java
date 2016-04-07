package bestcompany.moviematcher;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private static final String SERVER_ADDRESS = "13.82.49.1";
    private Protocol protocol = null;
    private GoogleMap mMap;

    String moviename = "Pulp Fiction";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

       // setUpMapIfNeeded(); now

        Intent intent = getIntent();
        moviename = intent.getStringExtra("Name");
        Log.v("yeaa",moviename);


        new ConnectTask().execute();
    }






/*
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }
*/



    public void setUpMap()
    {

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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(34.206232, 118.391026);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }




    private class ConnectTask extends AsyncTask<Void, Void, Protocol> {

        @Override
        protected Protocol doInBackground(Void... params) {
            Protocol p = null;
            try {
                InputStream caCert = getAssets().open("ca.crt");
                p = new Protocol(caCert, SERVER_ADDRESS);
            } catch (Exception e) {}

            return p;
        }

        @Override
        protected void onPostExecute(Protocol p) {
            if (p != null) {
                protocol = p;
                new GetLocationsTask().execute(moviename);
            } else {
                // failed to create connection
            }
        }
    }

    private class GetLocationsTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String data = "";
            Map<String, String> map = new HashMap<>();
            map.put("Name", params[0]);

            try {
                data = protocol.securePostRequest("MovieMatcherServer/location.php", true, map);
                Log.v("Here", data);
                Log.v("Here","mee");
            } catch (Exception e) {
                Log.v("Here",e.toString());
            }

            return data;
        }

        @Override
        protected void onPostExecute(String data) {
            if (data.length() > 0) {
                String[] locations = data.split("@");
                Log.v("Here",locations[0]);

                for(int i=0;i<locations.length;i++)
                {
                    String[] loc = locations[i].split("\\|");

                    for (int j = 0; j < loc.length; j++) {
                        double Lat = Double.parseDouble(loc[1]);
                        double Lng = Double.parseDouble(loc[2]);
                        LatLng location = new LatLng(Lat,Lng);
                         mMap.addMarker(new MarkerOptions().position(location));


                    }

                }
                // add markers here

            } else {
                // failed to get locations
            }
        }
    }



}
