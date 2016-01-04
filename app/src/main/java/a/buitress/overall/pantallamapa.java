package a.buitress.overall;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

public class pantallamapa extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public static final LatLng GUADALAJARA = new LatLng(20.659698, -103.349609);

    private GoogleMap _map;
    private GoogleApiClient _googleApiClient;
    private LocationRequest _locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallamapa);
        setupLocationRequest();
        buildGoogleApiClient();
        getMap();
    }

    private void setupLocationRequest() {
        _locationRequest = new LocationRequest();
        _locationRequest.setInterval(5 * 1000);
        _locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void getMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
    }

    private void buildGoogleApiClient() {
        _googleApiClient = new
                GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    public void onMapReady(GoogleMap map) {
        _map = map;
        _map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        moveCamera(GUADALAJARA, 12);
        _googleApiClient.connect();
    }

    private void moveCamera(LatLng location, int zoom) {
        _map.moveCamera(CameraUpdateFactory.newLatLng(location));
        _map.moveCamera(CameraUpdateFactory.zoomTo(zoom));
    }


    @Override
    public void onConnected(Bundle bundle) {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                _googleApiClient, _locationRequest, this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(_googleApiClient, this);
        if (_googleApiClient.isConnected()) {
            _googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if(location.hasAccuracy()) {
            if(location.getAccuracy() < 10.0) {
                moveCamera(new LatLng(location.getLatitude(), location.getLongitude()), 10);
                _map.addMarker(new MarkerOptions().title("Posicion actual").draggable(true));
                toastMessage("Accuracy: " + location.getAccuracy());
                _googleApiClient.disconnect();
            }
        }
    }



    private void sendLocationUpdate(Location location) {
        UpdateLocationTask locationTask = new UpdateLocationTask(iniciosesion._correoLogin, this);
        locationTask.execute(location.getLatitude(), location.getLongitude());
    }

    public void updateMarkers(HashMap<String, LatLng> locations, HashMap<String, String> usersList) {
        try {
            _map.clear();
            for (HashMap.Entry<String, LatLng> l : locations.entrySet()) {
                LatLng loc = l.getValue();
                _map.addMarker(new MarkerOptions().position(loc).title("ID:" + l.getKey()).snippet(usersList.get(l.getKey())));
            }
        } catch (Exception e) {
            toastMessage(e.getMessage());
        }
    }

    public void toastMessage(String message) {
        toastMessage(message, Toast.LENGTH_SHORT);
    }

    private void toastMessage(String message, int length) {
        Toast.makeText(pantallamapa.this, message, length).show();
    }
}

