package com.example.thanhvo.foursquareex3;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by ThanhVo on 12/29/2015.
 */
public class DetailVenueActivity extends Activity{
    private String id;
    private String name;
    private String address;
    private String category;
    private String distance;
    private String imgUrl;
    private Bitmap bitmap;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_venue_activity);
        this.id = getIntent().getStringExtra("id");
        this.name = getIntent().getStringExtra("name");
        this.address = getIntent().getStringExtra("address");
        this.category = getIntent().getStringExtra("category");
        this.distance = getIntent().getStringExtra("distance");
        TextView nameView = (TextView)findViewById(R.id.detail_name);
        TextView addressView = (TextView)findViewById(R.id.detail_crossstreet);
        TextView categoryView = (TextView)findViewById(R.id.detail_category);
        TextView distanceView = (TextView)findViewById(R.id.detail_distance);
        nameView.setText(this.name);
        addressView.setText("Address: " + this.address);
        categoryView.setText("Category: " + this.category);
        distanceView.setText("Distance: " + this.distance);
       // new LoadVenueIcon(this.id).execute();
        //imageView = (ImageView)findViewById(R.id.imageIcon);
        //new DownloadImageTask().execute("https://irs0.4sqi.net/img/general/100x100/26739064_mUxQ4CGrobFqwpcAIoX6YoAdH0xCDT4YAxaU6y65PPI.jpg");
    }

    private class DownloadImageTask extends AsyncTask<String,String,Bitmap>{
        protected Bitmap doInBackground(String... urls) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(urls[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

    private class LoadVenueIcon extends AsyncTask{
        private String venueId;
        private String temp;

        public LoadVenueIcon(String id)
        {
            this.venueId = id;
        }
        @Override
        protected Object doInBackground(Object[] params) {
            this.temp = makeCall("https://api.foursquare.com/v2/venues/"+this.venueId+"/photos");
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (temp == null)
            {

            }else{
               imgUrl = parseImage(temp);
            }
        }

        private String parseImage(String respone)
        {
            String tempUrl="";
            try{

                JSONObject jsonObject = new JSONObject(respone);
                if (jsonObject.has("response")){
                    if(jsonObject.getJSONObject("response").has("photos")){
                        JSONObject jsonPhoto = jsonObject.getJSONObject("response").getJSONObject("photos");
                        if (jsonPhoto.has("items")){
                            JSONArray jsonArray = jsonPhoto.getJSONArray("items");
                            tempUrl = jsonArray.getJSONObject(0).getString("prefix")+"100x100"+jsonArray.getJSONObject(0).getString("suffix");

                        }

                    }
                }
            }catch (Exception e)
            {
                e.printStackTrace();
                return "";
            }
            return tempUrl;
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
