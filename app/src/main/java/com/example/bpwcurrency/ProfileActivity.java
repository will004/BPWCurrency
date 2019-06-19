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
import android.widget.TextView;
import android.widget.Toast;

import com.example.bpwcurrencyModel.User;

public class ProfileActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private TextView username;
    private TextView email;
    private TextView password;
    private TextView phone;
    private TextView balanceIDR;
    private TextView balanceUSD;
    private TextView balanceSGD;
    private TextView balanceJPY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.profile_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home_menu:
                        Intent intentHome = new Intent(ProfileActivity.this, HomeActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intentHome);
                        break;
                    case R.id.profile_menu:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.logout_menu:
                        Intent intentLogout = new Intent(ProfileActivity.this, MainActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finishAffinity();
                        startActivity(intentLogout);
                        break;
                }
                return false;
            }
        });

        username = findViewById(R.id.user_name);
        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_password);
        phone = findViewById(R.id.user_phone);
        balanceIDR = findViewById(R.id.user_balanceAccount);
        balanceUSD = findViewById(R.id.user_balanceUSD);
        balanceSGD = findViewById(R.id.user_balanceSGD);
        balanceJPY = findViewById(R.id.user_balanceJPY);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        User logged = databaseHelper.showUser(LoginActivity.loginEmail);

        username.setText(logged.getName());
        email.setText(logged.getEmail());
        password.setText(logged.getPassword());
        phone.setText(logged.getPhone());
        balanceIDR.setText(logged.getBalanceAccount()+" IDR");
        balanceUSD.setText(logged.getBalanceUSD()+" USD");
        balanceJPY.setText(logged.getBalanceJPY()+" JPY");
        balanceSGD.setText(logged.getBalanceSGD()+" SGD");

    }

    public void updateBalance(View view) {
        //Code jika button update balance di klik
        startActivity(new Intent(ProfileActivity.this, UpdateBalanceActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(ProfileActivity.this, AboutUsUserActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
