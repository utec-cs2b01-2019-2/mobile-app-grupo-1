package com.example.passngo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.passngo.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.fabiomsr.moneytextview.MoneyTextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static java.lang.String.valueOf;


public class HomeActivity extends AppCompatActivity {

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final MoneyTextView Balance = (MoneyTextView) findViewById(R.id.Balance);
        TextView FullnameDisplay = (TextView) findViewById(R.id.FullnameDisplay);

        //String fullname = getIntent().getExtras().getString("fullname");

        FullnameDisplay.setText(getIntent().getExtras().getString("fullname"));
        Balance.setAmount(Float.parseFloat(getIntent().getExtras().getString("balance")));

        //refreshBalance(Balance);

    }


    public void addBalance(View view){
        final MoneyTextView Balance = (MoneyTextView) findViewById(R.id.Balance);
        float addbalance = 10;//(EditText) findViewById(R.id.inputField);
        int id = getIntent().getExtras().getInt("user_id");
        HashMap<String, String> message = new HashMap<>();
        message.put("id", Integer.toString(id));
        message.put("addbalance", Float.toString(addbalance));

        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2:8000/balance/add",
                new JSONObject(message),

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String currentbalance = response.getString("balance");
                            Balance.setAmount(Float.parseFloat(currentbalance));


                        } catch (JSONException e) {
                            Balance.setAmount(-1);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showMessage("Balance not found!");
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }



    public void refreshBalance(View view) {
        final MoneyTextView Balance = (MoneyTextView) findViewById(R.id.Balance);
        // recheck balance
        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "http://10.0.2.2:8000/balance/current",
                null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String currentbalance = response.getString("balance");
                            Balance.setAmount(Float.parseFloat(currentbalance));

                        } catch (JSONException e) {
                            Balance.setAmount(-1);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showMessage("Couldn't refresh!");
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

}

