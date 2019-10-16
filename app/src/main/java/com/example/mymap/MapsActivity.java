package com.example.mymap;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText mAddress;
    // Limits for the geocoder search (Colombia)
    public static final double lowerLeftLatitude = 1.396967;
    public static final double lowerLeftLongitude= -78.903968;
    public static final double upperRightLatitude= 11.983639;
    public static final double upperRigthLongitude= -71.869905;

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

        mAddress= (EditText) findViewById(R.id.txt_direccion);
        //Pone el estilo que recibe del json
        mMap.setMapStyle(MapStyleOptions
                .loadRawResourceStyle(this, R.raw.style_json));




        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        // Agregar un marcador en bogotá
        LatLng bogota = new LatLng(4.65, -74.05);

       /* Marker bogotaAzul = mMap.addMarker(new MarkerOptions()
                .position(bogota)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));*/

       /* Marker bogotaBike = mMap.addMarker(new MarkerOptions()
                .position(bogota)
                .title("Chefsito1")
                .snippet("De 2 pm a 5 pm")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.mipmap.ic_chef)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bogota));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(20));*/


        //Habilita boton para localización
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        //Habilita brujula

        mMap.getUiSettings().setCompassEnabled(true);

       //Habilitar los “gestures” como “pinch to zoom”
        mMap.getUiSettings().setZoomGesturesEnabled(true);

       //Habilitar los botones de zoom
        mMap.getUiSettings().setZoomControlsEnabled(true);




        mAddress.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Inicialización del objeto
                Geocoder mGeocoder = new Geocoder(getBaseContext());
                if (actionId == EditorInfo.IME_ACTION_SEND){
                    String addressString = mAddress.getText().toString();
                    if (!addressString.isEmpty()) {
                        try {
                            List<Address> addresses = mGeocoder.getFromLocationName(addressString, 2);
                            if (addresses != null && !addresses.isEmpty()) {
                                Address addressResult = addresses.get(0);
                                LatLng position = new LatLng(addressResult.getLatitude(), addressResult.getLongitude());
                                if (mMap != null) {
                                    //Agregar Marcador al mapa
                                    Marker chefMark = mMap.addMarker(new MarkerOptions()
                                            .position(position)
                                            .title("Chefsito2")
                                            .snippet("De 3 pm a 5 pm")
                                            .icon(BitmapDescriptorFactory
                                                    .fromResource(R.mipmap.ic_chef)));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                                    mMap.moveCamera(CameraUpdateFactory.zoomTo(20));
                                }
                            } else {Toast.makeText(MapsActivity.this, "Dirección no encontrada", Toast.LENGTH_SHORT).show();}
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {Toast.makeText(MapsActivity.this, "La dirección esta vacía", Toast.LENGTH_SHORT).show();}
                }
                return false;
            }
        });






    }
}
