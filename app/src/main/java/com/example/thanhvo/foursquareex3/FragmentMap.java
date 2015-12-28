package com.example.thanhvo.foursquareex3;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by ThanhVo on 12/28/2015.
 */
public class FragmentMap extends Fragment implements OnMapReadyCallback, LocationListener {
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        if (currentLocation.getPosition().latitude != lat || currentLocation.getPosition().longitude != lng) {
            mMap.clear();
            LatLng myLocation = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(myLocation).title("It's me").icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
            new Foursquare(String.valueOf(lat), String.valueOf(lng), mMap).execute();
        }

    }

    public FragmentMap() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private GoogleMap mMap;
    private Marker currentLocation;
    private MapView mapView;

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMap = mapView.getMap();
        PackageManager pm = getActivity().getPackageManager();
        if (pm.checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION, getActivity().getPackageName()) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        LatLng myLocation = new LatLng(40.7463956, -73.9852992);
        currentLocation = mMap.addMarker(new MarkerOptions().position(myLocation).title("It's me").icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(bestProvider, 60000, 0, this);
        return view;

    }
}
