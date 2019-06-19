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
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bpwcurrencyModel.User;

public class UpdateBalanceActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private TextView currBalance;
    private EditText newBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_balance);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.layout_updateBalance);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home_menu:
                        Intent intentHome = new Intent(UpdateBalanceActivity.this, HomeActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intentHome);
                        finish();
                        break;
                    case R.id.profile_menu:
                        Intent intentLogin = new Intent(UpdateBalanceActivity.this, ProfileActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intentLogin);
                        break;
                    case R.id.logout_menu:
                        Intent intentLogout = new Intent(UpdateBalanceActivity.this, MainActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finishAffinity();
                        startActivity(intentLogout);
                        break;
                }
                return false;
            }
        });

        currBalance = findViewById(R.id.tvCurrentBalance);
        newBalance = findViewById(R.id.etNewBalance);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        User logged = databaseHelper.showUser(LoginActivity.loginEmail);
        currBalance.setText(logged.getBalanceAccount()+"");
    }

    public void btnUpdate(View view){
        String input = newBalance.getText().toString();

        double insertNewBalance;

        try{
            insertNewBalance = Double.parseDouble(input);
            DatabaseHelper databaseHelper = new DatabaseHelper(UpdateBalanceActivity.this);
            databaseHelper.updateBalance(insertNewBalance, LoginActivity.loginEmail);
            startActivity(new Intent(UpdateBalanceActivity.this, ProfileActivity.class));
        }
        catch (Exception e){
            Toast.makeText(this, "Input must be number", Toast.LENGTH_SHORT).show();
        }
    }
}
