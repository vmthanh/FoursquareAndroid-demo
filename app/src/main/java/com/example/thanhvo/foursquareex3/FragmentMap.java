package com.example.thanhvo.foursquareex3;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
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

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by ThanhVo on 12/28/2015.
 */
public class FragmentMap extends Fragment implements  LocationListener {
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
    OnFragmentInteractionListenre onCallback;

    public interface OnFragmentInteractionListenre{
        public void onFragmentInteraction(ArrayList<FoursquareVenue>venueList);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            onCallback = (OnFragmentInteractionListenre)getActivity();
        }catch (ClassCastException e)
        {
            throw new ClassCastException(" Must implement OnFragmentInteractionListenre");

        }
    }

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

    private class Foursquare extends AsyncTask {
        final String CLIENT_ID = "BZ2B2O1SBXHJJ324IWFLQHM3HUWW2AFZHEHPQI22PPVU3AU5";
        final String CLIENT_SECRET = "G3AYGEK1F234CTWE1AZGDWFMMIRBELM2X32KUX52LH1M2IV1";
        private String lat;
        private String lng;
        private String temp;
        private ArrayList<FoursquareVenue> vennuesList;
        private GoogleMap gMap;
        public Foursquare(String lat, String lng, GoogleMap gMap)
        {
            this.lat = lat;
            this.lng = lng;
            this.gMap = gMap;


        }
        @Override
        protected Object doInBackground(Object[] params) {

            temp = makeCall("https://api.foursquare.com/v2/venues/search?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v=20130815"+"&ll="+this.lat+","+this.lng);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (temp == null)
            {

            }else{
                vennuesList = (ArrayList<FoursquareVenue>) parseFoursquare(temp);

                for(int i=0;i<vennuesList.size();++i)
                {
                    FoursquareVenue temp = vennuesList.get(i);
                    LatLng venueLocation = new LatLng(Double.parseDouble(temp.getLat()),Double.parseDouble(temp.getLng()));
                    this.gMap.addMarker(new MarkerOptions().position(venueLocation).title(temp.getName()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.venue_marker)));
                }
                if (onCallback !=null)
                {
                    onCallback.onFragmentInteraction(vennuesList);
                }
            }
        }



        private  ArrayList<FoursquareVenue> parseFoursquare(String respone) {
            ArrayList<FoursquareVenue> temp = new ArrayList<FoursquareVenue>();
            try{
                JSONObject jsonObject = new JSONObject(respone);
                if (jsonObject.has("response"))
                {
                    if(jsonObject.getJSONObject("response").has("venues"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("venues");
                        for(int i=0; i<jsonArray.length(); ++i)
                        {
                            FoursquareVenue poi = new FoursquareVenue();
                            if (jsonArray.getJSONObject(i).has("name"))
                            {
                                poi.setName(jsonArray.getJSONObject(i).getString("name"));
                                poi.setId(jsonArray.getJSONObject(i).getString("id"));
                                if (jsonArray.getJSONObject(i).has("location")) {
                                        if (jsonArray.getJSONObject(i).getJSONObject("location").has("lat")){
                                            poi.setLat(String.valueOf(jsonArray.getJSONObject(i).getJSONObject("location").getDouble("lat")));
                                        }
                                        if (jsonArray.getJSONObject(i).getJSONObject("location").has("lng")){
                                            poi.setLng(String.valueOf(jsonArray.getJSONObject(i).getJSONObject("location").getDouble("lng")));
                                        }
                                        if (jsonArray.getJSONObject(i).getJSONObject("location").has("distance")){
                                            poi.setDistance(String.valueOf(jsonArray.getJSONObject(i).getJSONObject("location").getDouble("distance")));
                                        }
                                        if (jsonArray.getJSONObject(i).has("categories")) {
                                            if (jsonArray.getJSONObject(i).getJSONArray("categories").length() > 0) {
                                                if (jsonArray.getJSONObject(i).getJSONArray("categories").getJSONObject(0).has("icon")) {
                                                    poi.setCategory(jsonArray.getJSONObject(i).getJSONArray("categories").getJSONObject(0).getString("name"));
                                                }
                                            }
                                        }
                                    String fullAddress = "";
                                    if (jsonArray.getJSONObject(i).getJSONObject("location").has("address")){
                                        fullAddress = fullAddress + " " +String.valueOf(jsonArray.getJSONObject(i).getJSONObject("location").getString("address"));
                                    }

                                    if (jsonArray.getJSONObject(i).getJSONObject("location").has("crossStreet")){
                                        fullAddress = fullAddress + " " +String.valueOf(jsonArray.getJSONObject(i).getJSONObject("location").getString("crossStreet"));
                                    }

                                    if (jsonArray.getJSONObject(i).getJSONObject("location").has("city")){
                                        fullAddress = fullAddress + " " +String.valueOf(jsonArray.getJSONObject(i).getJSONObject("location").getString("city"));
                                    }

                                    if (jsonArray.getJSONObject(i).getJSONObject("location").has("state")){
                                        fullAddress = fullAddress + " " +String.valueOf(jsonArray.getJSONObject(i).getJSONObject("location").getString("state"));
                                    }

                                    if (jsonArray.getJSONObject(i).getJSONObject("location").has("country")){
                                        fullAddress = fullAddress + " " +String.valueOf(jsonArray.getJSONObject(i).getJSONObject("location").getString("country"));
                                    }
                                    poi.setCrossStreet(fullAddress);
                                        temp.add(poi);
                                }
                            }
                        }
                    }
                }
            }catch (Exception e)
            {
                e.printStackTrace();
                return new ArrayList<FoursquareVenue>();
            }
            return temp;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }

        private String makeCall(String url)
        {
            StringBuffer buffer_string = new StringBuffer(url);
            String replyString = "";
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(buffer_string.toString());
            try {
                HttpResponse response = httpclient.execute(httpGet);
                InputStream is = response.getEntity().getContent();
                BufferedInputStream bis = new BufferedInputStream(is);
                ByteArrayBuffer baf = new ByteArrayBuffer(20);
                int current = 0;
                while((current=bis.read())!=-1)
                {
                    baf.append((byte)current);

                }
                replyString = new String(baf.toByteArray());
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return replyString.trim();
        }

    }


}
