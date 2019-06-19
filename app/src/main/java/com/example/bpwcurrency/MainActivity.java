package com.example.bpwcurrency;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bpwcurrencyModel.Currency;
import com.example.bpwcurrencyModel.CurrencyAdapter;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Currency> currencies;
    private DatabaseHelper databaseHelper;

    private void getDataFromAPI() {
        currencies = new ArrayList<>();

        String url = "https://www.adisurya.net/kurs-bca/get";

        //update data from API, only take USD, SGD, and JPY
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //get JSONObject data
                try {
                    JSONObject data = response.getJSONObject("Data");

                    //get currencies
                    JSONObject usd = data.getJSONObject("USD");
                    JSONObject sgd = data.getJSONObject("SGD");
                    JSONObject jpy = data.getJSONObject("JPY");

                    currencies.add(
                            new Currency(
                                    "USD",
                                    usd.getDouble("Jual"),
                                    usd.getDouble("Beli")
                            )
                    );

                    currencies.add(
                            new Currency(
                                    "SGD",
                                    sgd.getDouble("Jual"),
                                    sgd.getDouble("Beli")
                            )
                    );

                    currencies.add(
                            new Currency(
                                    "JPY",
                                    jpy.getDouble("Jual"),
                                    jpy.getDouble("Beli")
                            )
                    );

                    Toast.makeText(MainActivity.this, "Updated on: " + response.getString("LastUpdate"), Toast.LENGTH_SHORT).show();

                    //add currency to ListView
                    ListView currencyLV = findViewById(R.id.list_currency);

                    CurrencyAdapter currencyAdapter = new CurrencyAdapter(MainActivity.this);

                    for (int i = 0; i < currencies.size(); i++) {
                        Currency currCurrency = currencies.get(i);
                        currencyAdapter.addCurrency(currCurrency);
                    }

                    currencyLV.setAdapter(currencyAdapter);

                    currencyLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                    });

                    databaseHelper = new DatabaseHelper(MainActivity.this);
                    databaseHelper.addCurrency(currencies);

                } catch (Exception e) {
                    Log.i("catch", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error", error.getMessage());
                Toast.makeText(MainActivity.this, "Error fetch data from API", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonObjectRequest);
    }

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home_menu:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.login_menu:
                        Intent intentLogin = new Intent(MainActivity.this, LoginActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intentLogin);
                        break;
                    case R.id.register_menu:
                        Intent intentRegis = new Intent(MainActivity.this, RegisterActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intentRegis);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        getDataFromAPI();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
