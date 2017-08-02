package id.sch.smktelkom_mlg.learn.ttd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    Button btnViewProducts;

    private static final String URL_DATA = "http://192.168.0.242/ibuk/get_data.php";
    public static final String ARRAY = "obat";
    private static final String TAG_ID = "ID_OBAT";
    private static final String TAG_NAMA = "NAMA_OBAT";

    ArrayList<HashMap<String, String>> productsList;

    ListView listView;
    ListAdapter adapter;


    String[] mobileArray = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_products);

        // Hashmap for ListView
        productsList = new ArrayList<HashMap<String, String>>();



         listView = (ListView) findViewById(R.id.list);


        loadRecyclerViewData();


        Log.i("ARRAY3", productsList.toString());

        adapter = new SimpleAdapter(this, productsList,
                R.layout.list_item, new String[]
                { TAG_ID, TAG_NAMA},
                new int[] { R.id.id, R.id.nama });

//        ArrayAdapter adapter1 = new ArrayAdapter(this,R.layout.list_item, productsList);


        Log.i("ARRAY4", productsList.toString());






    }

    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        try{
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray(ARRAY);

                            Log.i("ARRAY1", array.toString());




                            for (int i = 0; i < array.length(); i++) {
                                JSONObject c = array.getJSONObject(i);

                                Log.i("NAMA", c.getString(TAG_ID));

                                // Storing each json item in variable
                                String id = c.getString(TAG_ID);
                                String nama = c.getString(TAG_NAMA);

                                // creating new HashMap
                                HashMap<String, String> map = new HashMap<String, String>();

                                // adding each child node to HashMap key => value
                                map.put(TAG_ID, id);
                                map.put(TAG_NAMA, nama);

                                // adding HashList to ArrayList
                                productsList.add(map);

                            }

                            Log.i("ARRAY2", productsList.toString());

                            listView.setAdapter(adapter);

                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplication(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                        Log.i("ERRORNYA", volleyError.getMessage());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
