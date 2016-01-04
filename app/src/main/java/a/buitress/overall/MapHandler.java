package a.buitress.overall;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created by Marce on 31/12/2015.
 */
public class MapHandler implements OnMapReadyCallback {

    private GoogleMap _map;
    private GoogleApiClient _googleApiClient;
    private LocationRequest _locationRequest;

    public MapHandler(GoogleApiClient _googleApiClient) {
        setupLocationRequest();
    }

    private void setupLocationRequest() {
        _locationRequest = new LocationRequest();
        _locationRequest.setInterval(1000);
        _locationRequest.setFastestInterval(1000);
        _locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        _map = googleMap;
    }
}
