package bad.twitterfriendschannel;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private List<LatLng> markers = new ArrayList<LatLng>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Button starbucksButton = (Button) findViewById(R.id.button);
        starbucksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (LatLng ll : markers){
                    System.out.print(ll.toString()+",");
                }
                System.out.println();
            }
        });
        Button signOutButton = (Button) findViewById(R.id.button2);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MapsActivity.this,LoginActivity.class);
                startActivity(i);

            }
        });


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
        mMap.setOnMarkerClickListener(this);
        // Add a marker in Sydney and move the camera
        LatLng starbucks = new LatLng(37.7838092, -122.4523144);
        LatLng YoungSeok = new LatLng(37.8, -122.3);
        LatLng James = new LatLng(37.9, -122.2);
        LatLng Shuvo = new LatLng(37.95, -122.15);
        LatLng Steven = new LatLng(37.6, -122.4);


        mMap.addMarker(new MarkerOptions().position(starbucks).title(
                "Young-Seok")).showInfoWindow();
        mMap.addMarker(new MarkerOptions().position(YoungSeok).title(
                "Starbucks")).showInfoWindow();
        mMap.addMarker(new MarkerOptions().position(James).title(
                "James")).showInfoWindow();
        mMap.addMarker(new MarkerOptions().position(Shuvo).title(
                "Shuvo")).showInfoWindow();
        mMap.addMarker(new MarkerOptions().position(Steven).title(
                "Steven")).showInfoWindow();

        mMap.moveCamera(CameraUpdateFactory.newLatLng(starbucks));
    }
    public boolean onMarkerClick(Marker marker) {

        LatLng position = marker.getPosition();
        boolean found = false;
        int index = 0;
        for (LatLng ll : markers){
            if (ll.toString().equalsIgnoreCase(position.toString())){
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                found =true;
                break;
            }
            index++;
        }
        if (found){
           markers.remove(index);
            return true;
        }
        markers.add(marker.getPosition());
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        LatLng YoungSeok = new LatLng(37.8, -122.3);
        mMap.addMarker(new MarkerOptions().position(YoungSeok).title(
                "Starbucks")).showInfoWindow();
        return false;
    }
}
