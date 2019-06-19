package com.example.bpwcurrency;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bpwcurrencyModel.User;

public class LoginActivity extends AppCompatActivity {
    private EditText emailLogin, passwordLogin;
    private DrawerLayout drawerLayout;
    private String email, password;
    private DatabaseHelper databaseHelper;

    public static String loginEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.login_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home_menu:
                        Intent intentHome = new Intent(LoginActivity.this, MainActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intentHome);
                        break;
                    case R.id.login_menu:
                        drawerLayout.closeDrawer((GravityCompat.START));
                        break;
                    case R.id.register_menu:
                        Intent intentRegis = new Intent(LoginActivity.this, RegisterActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intentRegis);
                        break;
                }
                return false;
            }
        });

        emailLogin = findViewById(R.id.email_login);
        passwordLogin = findViewById(R.id.password_login);

        TextView txtRegister = findViewById(R.id.text_register_here_login);
        SpannableString ss = new SpannableString("Don't have an account? Register here!");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setColor(Color.rgb(0, 0, 0));
            }
        };
        ss.setSpan(clickableSpan, 32, 36, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtRegister.setText(ss);
        txtRegister.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(LoginActivity.this, AboutUsActivity.class));
        return super.onOptionsItemSelected(item);
    }


    public void btnLoginClick(View view) {
        email = emailLogin.getText().toString();
        password = passwordLogin.getText().toString();

        if (email.equals("") || password.equals("") || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (email.equals("") && password.equals("")) {
                Toast.makeText(this, "Email or Password must be Filled!", Toast.LENGTH_SHORT).show();
            } else if (email.equals("")) {
                Toast.makeText(this, "E-mail must be Filled!", Toast.LENGTH_SHORT).show();
            } else if (password.equals("")) {
                Toast.makeText(this, "Password must be Filled!", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
            }

        } else {
            databaseHelper = new DatabaseHelper(LoginActivity.this);
            User currentUser = databaseHelper.checkUser(new User(null, email, password, null, 0, 0, 0, 0));
            if (currentUser != null) {
                Intent intentHome = new Intent(LoginActivity.this, HomeActivity.class);
                finishAffinity();
                startActivity(intentHome);
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                loginEmail = email;
            } else {
                Toast.makeText(this, "Failed to login", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
