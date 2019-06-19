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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bpwcurrencyModel.User;

public class DetailCurrencyActivity extends AppCompatActivity {
    private String currencyName;
    private Double currencySell, currencyBuy;
    private TextView tvCurrName, tvCurrSell, tvCurrBuy;
    private DrawerLayout drawerLayout;

    private boolean sell = false, buy = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_currency);

        sell = false;
        buy = false;


        Intent i = getIntent();
        currencyName = i.getExtras().getString("currencyName");
        currencySell = i.getExtras().getDouble("currencySell");
        currencyBuy = i.getExtras().getDouble("currencyBuy");


        tvCurrName = findViewById(R.id.tvCurrencyName);
        tvCurrSell = findViewById(R.id.tvCurrencySell);
        tvCurrBuy = findViewById(R.id.tvCurrencyBuy);

        tvCurrName.setText(currencyName);
        tvCurrSell.setText("" + currencySell);
        tvCurrBuy.setText("" + currencyBuy);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.layout_detailCurrency);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home_menu:
                        Intent intentHome = new Intent(DetailCurrencyActivity.this, HomeActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intentHome);
                        finish();
                        break;
                    case R.id.profile_menu:
                        Intent intentLogin = new Intent(DetailCurrencyActivity.this, ProfileActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intentLogin);
                        break;
                    case R.id.logout_menu:
                        Intent intentLogout = new Intent(DetailCurrencyActivity.this, MainActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finishAffinity();
                        startActivity(intentLogout);
                        break;
                }
                return false;
            }
        });
    }


    public void btnJualClick(View view) {
        LinearLayout balanceInfo = findViewById(R.id.balanceInformation);
        balanceInfo.setVisibility(view.VISIBLE);

        EditText edtSell = findViewById(R.id.edt_buy_sell);
        edtSell.setVisibility(view.VISIBLE);
        edtSell.setHint("How much you want to sell?");

        Button btnProcess = findViewById(R.id.btnProcess);
        btnProcess.setVisibility(view.VISIBLE);

        DatabaseHelper databaseHelper = new DatabaseHelper(DetailCurrencyActivity.this);
        User logged = databaseHelper.showUser(LoginActivity.loginEmail);

        TextView balance = findViewById(R.id.balanceDetail);

        if(currencyName.equals("USD")){
            balance.setText(logged.getBalanceUSD()+" USD");
        }
        else if(currencyName.equals("SGD")){
            balance.setText(logged.getBalanceSGD()+" SGD");
        }
        else if(currencyName.equals("JPY")){
            balance.setText(logged.getBalanceJPY()+" JPY");
        }

        sell = true;
    }

    public void btnBeliClick(View view) {
        LinearLayout balanceInfo = findViewById(R.id.balanceInformation);
        balanceInfo.setVisibility(view.VISIBLE);

        EditText edtBuy = findViewById(R.id.edt_buy_sell);
        edtBuy.setVisibility(view.VISIBLE);
        edtBuy.setHint("How much you want to buy?");

        Button btnProcess = findViewById(R.id.btnProcess);
        btnProcess.setVisibility(view.VISIBLE);

        DatabaseHelper databaseHelper = new DatabaseHelper(DetailCurrencyActivity.this);
        User logged = databaseHelper.showUser(LoginActivity.loginEmail);

        TextView balance = findViewById(R.id.balanceDetail);

        balance.setText(logged.getBalanceAccount()+" IDR");

        buy = true;
    }

    public void btnProcessClick(View view) {
        //code untuk process
        EditText edtValue = findViewById(R.id.edt_buy_sell);
        DatabaseHelper databaseHelper = new DatabaseHelper(DetailCurrencyActivity.this);
        User logged = databaseHelper.showUser(LoginActivity.loginEmail);
        double inputValue;
        try{
            inputValue = Double.parseDouble(edtValue.getText().toString());
        }
        catch (Exception e){
            Toast.makeText(this, "Input must be number", Toast.LENGTH_SHORT).show();
            return;
        }

        if(sell){
            if(currencyName.equals("USD")){
                //USD to IDR

                //ambil USD, cek dulu sama input
                if(inputValue > logged.getBalanceUSD()){
                    Toast.makeText(this, "Insufficient fund", Toast.LENGTH_SHORT).show();
                    sell = true;
                    return;
                }
                else{
                    double idr = currencyBuy * inputValue;
                    double temp = logged.getBalanceAccount();
                    temp += idr;

                    //kurangin USD
                    databaseHelper.decreaseCurrency(inputValue, LoginActivity.loginEmail, "USD");

                    //update balance account
                    databaseHelper.updateBalance(temp, LoginActivity.loginEmail);
                    startActivity(new Intent(DetailCurrencyActivity.this, HomeActivity.class));
                    finish();
                }
            }
            else if(currencyName.equals("SGD")){
                if(inputValue > logged.getBalanceSGD()){
                    Toast.makeText(this, "Insufficient fund", Toast.LENGTH_SHORT).show();
                    sell = true;
                    return;
                }
                else{
                    double idr = currencyBuy * inputValue;
                    double temp = logged.getBalanceAccount();
                    temp += idr;

                    //kurangin SGD
                    databaseHelper.decreaseCurrency(inputValue, LoginActivity.loginEmail, "SGD");

                    //update balance account
                    databaseHelper.updateBalance(temp, LoginActivity.loginEmail);
                    startActivity(new Intent(DetailCurrencyActivity.this, HomeActivity.class));
                    finish();
                }
            }
            else if(currencyName.equals("JPY")){
                if(inputValue > logged.getBalanceJPY()){
                    Toast.makeText(this, "Insufficient fund", Toast.LENGTH_SHORT).show();
                    sell = true;
                    return;
                }
                else{
                    double idr = currencyBuy * inputValue;
                    double temp = logged.getBalanceAccount();
                    temp += idr;

                    //kurangin JPY
                    databaseHelper.decreaseCurrency(inputValue, LoginActivity.loginEmail, "JPY");

                    //update balance account
                    databaseHelper.updateBalance(temp, LoginActivity.loginEmail);
                    startActivity(new Intent(DetailCurrencyActivity.this, HomeActivity.class));
                    finish();
                }
            }
            sell = false;
        }
        else if(buy){
            if(currencyName.equals("USD")){
                //IDR to USD

                double price = currencySell * inputValue;

                if(price > logged.getBalanceAccount()){
                    Toast.makeText(this, "Insufficient account", Toast.LENGTH_SHORT).show();
                    buy = true;
                    return;
                }
                else{
                    //update balance
                    double temp = logged.getBalanceAccount();
                    temp -= price;
                    databaseHelper.updateBalance(temp, LoginActivity.loginEmail);

                    databaseHelper.increaseCurrency(inputValue, LoginActivity.loginEmail, "USD");
                    startActivity(new Intent(DetailCurrencyActivity.this, HomeActivity.class));
                    finish();
                }
            }
            else if(currencyName.equals("SGD")){
                double price = currencySell * inputValue;

                if(price > logged.getBalanceAccount()){
                    Toast.makeText(this, "Insufficient account", Toast.LENGTH_SHORT).show();
                    buy = true;
                    return;
                }
                else{
                    //update balance
                    double temp = logged.getBalanceAccount();
                    temp -= price;
                    databaseHelper.updateBalance(temp, LoginActivity.loginEmail);

                    //update ke kolom SGD
                    databaseHelper.increaseCurrency(inputValue, LoginActivity.loginEmail, "SGD");

                    startActivity(new Intent(DetailCurrencyActivity.this, HomeActivity.class));
                    finish();
                }
            }
            else if(currencyName.equals("JPY")){
                double price = currencySell * inputValue;

                if(price > logged.getBalanceAccount()){
                    Toast.makeText(this, "Insufficient account", Toast.LENGTH_SHORT).show();
                    buy = true;
                    return;
                }
                else{
                    //update balance
                    double temp = logged.getBalanceAccount();
                    temp -= price;
                    databaseHelper.updateBalance(temp, LoginActivity.loginEmail);

                    databaseHelper.increaseCurrency(inputValue, LoginActivity.loginEmail, "JPY");

                    startActivity(new Intent(DetailCurrencyActivity.this, HomeActivity.class));
                    finish();
                }
            }
            buy = false;
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
        startActivity(new Intent(DetailCurrencyActivity.this, AboutUsUserActivity.class));
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
