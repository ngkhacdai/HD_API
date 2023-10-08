package com.example.hd_api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hd_api.models.Product;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetAllProduct extends AppCompatActivity {
    ArrayList<Product> list;
    RecyclerView rv;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_product);
        list = new ArrayList<>();
        rv = findViewById(R.id.recyclerView);
        SharedPreferences preferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
        String accessToken = preferences.getString("access_token", null);
        String url = "https://foodapp-7o77.onrender.com/v1/api/admin/getallproduct";
        GetAsyncTask getAsyncTask = new GetAsyncTask(url, accessToken);
        getAsyncTask.execute();
        try {
            // Get the result from the AsyncTask
            JSONArray result = getAsyncTask.get();

            if (result != null) {
                // Handle the successful result (JSON object)
                for (int i = 0; i < result.length(); i++) {
                    JSONObject productObject = result.getJSONObject(i);
                    String _id = productObject.getString("_id");
                    String name = productObject.getString("name");
                    String description  = productObject.getString("description");
                    int price = productObject.getInt("price");
                    String category  = productObject.getString("category");
                    String image  = productObject.getString("image");
                    int stockQuantity   = productObject.getInt("stockQuantity");
                    Product product = new Product(_id,name,description,price,category,image,stockQuantity);
                    list.add(product);
                }
                ProductAdapter adapter = new ProductAdapter(list);
                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                rv.setLayoutManager(layoutManager);
                rv.setAdapter(adapter);

            } else {
                // Handle the error case
                Toast.makeText(this, "Failed to make the API request", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error executing AsyncTask", Toast.LENGTH_SHORT).show();
        }
    }
    public class GetAsyncTask extends AsyncTask<Void, Void, JSONArray> {
        private static final String TAG = "GetAsyncTask";
        private final String apiUrl;
        private final String token;

        public GetAsyncTask(String apiUrl, String token) {
            this.apiUrl = apiUrl;
            this.token = token;

        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            try {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

                // Add the token to the request header
                connection.setRequestProperty("token", token);

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    in.close();

                    // Parse the JSON response and return it as a JSONArray
                    return new JSONArray(response.toString());
                } else {
                    Log.e(TAG, "GET request failed with response code: " + responseCode);
                    return null; // Handle the error case appropriately
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "Exception: " + e.getMessage());
                return null; // Handle the error case appropriately
            }
        }

    }

    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
        ArrayList<Product> list;

        public ProductAdapter(ArrayList<Product> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
            Product product = list.get(position);
            holder.tvid.setText(product.get_id());
            String urlimage = "https://foodapp-7o77.onrender.com/uploads/"+ product.getImage();
            Picasso.get().load(urlimage).into(holder.imageView);
            holder.tvname.setText(product.getName());
            holder.tvprice.setText(product.getPrice()+"");
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            Product product;
            ImageView imageView;
            TextView tvid,tvname,tvprice;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.image);
                tvid = itemView.findViewById(R.id.tvid);
                tvname = itemView.findViewById(R.id.tvname);
                tvprice = itemView.findViewById(R.id.tvprice);
            }
        }
    }
}