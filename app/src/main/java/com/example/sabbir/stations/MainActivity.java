package com.example.sabbir.stations;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner,spinner2,spinner3,spinner4,spinner5;
    private TextView tv1,tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1=(TextView)findViewById(R.id.tv1);

        spinner=(Spinner) findViewById(R.id.spinner);
        spinner2=(Spinner) findViewById(R.id.spinner2);
        spinner3=(Spinner) findViewById(R.id.spinner3);
        spinner4=(Spinner) findViewById(R.id.spinner4);
        spinner5=(Spinner) findViewById(R.id.spinner5);

        ArrayAdapter adapter=ArrayAdapter.createFromResource(this,R.array.dhaka_divison,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(MainActivity.this);

        ArrayAdapter adapter2=ArrayAdapter.createFromResource(this,R.array.ctg_divison,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);

        spinner2.setOnItemSelectedListener(MainActivity.this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position)
        {
            case 1:
               new MyTask().execute();
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class MyTask extends AsyncTask<Void,Void,List<MyLocations>>
    {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading");
            progressDialog.show();
        }

        @Override
        protected List doInBackground(Void... params) {
            ArrayList<MyLocations> locationsList=new ArrayList<>();

            String json = null;
            try {
                InputStream is = getAssets().open("locations.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            try {
                JSONObject obj = new JSONObject(json);
                JSONArray m_jArry = obj.getJSONArray("locations");

                for (int i = 0; i < m_jArry.length(); i++) {
                    JSONObject jo_inside = m_jArry.getJSONObject(i);
                    MyLocations location = new MyLocations();
                    //location.setLat((float) jo_inside.getDouble("lat"));
                    //location.setLng((float) jo_inside.getDouble("lng"));
                    location.setKey(jo_inside.getString("key"));
                    //location.setRadius(jo_inside.getInt("radius"));
                    location.setName(jo_inside.getString("name"));
                    location.setAudio_file(jo_inside.getString("audio_file"));


                    //Add your values in your `ArrayList` as below:
                    locationsList.add(location);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return locationsList;
        }

        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            progressDialog.dismiss();

            String s=list.get(1).toString();
            tv1.setText(s);
        }
    }


/*    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().open("movies.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }*/

   /* public void showJson() {

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("formules");
            ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                Log.d("Details-->", jo_inside.getString("formule"));
                String formula_value = jo_inside.getString("formule");
                String url_value = jo_inside.getString("url");

                //Add your values in your `ArrayList` as below:
                m_li = new HashMap<String, String>();
                m_li.put("formule", formula_value);
                m_li.put("url", url_value);

                formList.add(m_li);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/


    /*public class MyAsyncTask extends AsyncTask<String,String,List>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<Movie> doInBackground(String... params) {

            List<Movie> movies=new ArrayList<>();
            Gson gson=new Gson();
            movies=new GsonBuilder().create().fromJson(loadJSONFromAsset(), (Type) movies);

            StringBuffer  postList=new StringBuffer();
            for (Movie movie:movies)
            {
                postList.append("\nName"+movie.getMovie());
            }

            return postList;

        }

        @Override
        protected void onPostExecute(List s) {
            super.onPostExecute(s);
        }
    }*/

    public ArrayList<MyLocations> loadJSONFromAsset() {
        ArrayList<MyLocations> locList = new ArrayList<>();
        String json = null;
        try {
            InputStream is = getAssets().open("locations.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray m_jArry = obj.getJSONArray("locations");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                MyLocations location = new MyLocations();
                //location.setLat((float) jo_inside.getDouble("lat"));
                //location.setLng((float) jo_inside.getDouble("lng"));
                location.setKey(jo_inside.getString("key"));
                //location.setRadius(jo_inside.getInt("radius"));
                location.setName(jo_inside.getString("name"));
                location.setAudio_file(jo_inside.getString("audio_file"));


                //Add your values in your `ArrayList` as below:
                locList.add(location);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return locList;
    }
}
