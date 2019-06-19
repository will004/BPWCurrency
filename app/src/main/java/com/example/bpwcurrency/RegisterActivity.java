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
import android.text.TextUtils;
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

public class RegisterActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private String name, email, password, coPassword, phone;
    private EditText nameRegis, emailRegis, passwordRegis, coPasswordRegis, phoneRegis;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.register_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home_menu:
                        Intent intentHome = new Intent(RegisterActivity.this, MainActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intentHome);
                        break;
                    case R.id.login_menu:
                        Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intentLogin);
                        break;
                    case R.id.register_menu:
                        drawerLayout.closeDrawer((GravityCompat.START));
                        break;
                }
                return false;
            }
        });
        TextView txtLogin = findViewById(R.id.text_login_here_register);
        SpannableString ss = new SpannableString("Already have an account? Sign In here!");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View txtLogin) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setColor(Color.rgb(0, 0, 0));
            }
        };
        ss.setSpan(clickableSpan, 33, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtLogin.setText(ss);
        txtLogin.setMovementMethod(LinkMovementMethod.getInstance());

        nameRegis = findViewById(R.id.name_register);
        emailRegis = findViewById(R.id.email_register);
        passwordRegis = findViewById(R.id.password_register);
        coPasswordRegis = findViewById(R.id.coPassword_register);
        phoneRegis = findViewById(R.id.phone_register);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(RegisterActivity.this, AboutUsActivity.class));
        return super.onOptionsItemSelected(item);
    }

    public void btnRegisterClick(View view) {
        name = nameRegis.getText().toString();
        email = emailRegis.getText().toString();
        password = passwordRegis.getText().toString();
        coPassword = coPasswordRegis.getText().toString();
        phone = phoneRegis.getText().toString();
        boolean digitsOnly = TextUtils.isDigitsOnly(phoneRegis.getText());

        if (name.isEmpty() && email.isEmpty() && password.isEmpty() && coPassword.isEmpty() && phone.isEmpty()) {
            Toast.makeText(this, "All fields must be Filled!", Toast.LENGTH_SHORT).show();
        } else if (name.isEmpty() || name.length() < 3 || name.length() > 25) {
            Toast.makeText(this, "Name must be Filled between 3 and 25 character!", Toast.LENGTH_SHORT).show();
        } else if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "E-mail must be Filled with the correct format!", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty() || password.length() <= 6) {
            Toast.makeText(this, "Password must be Filled with more than 6 character!", Toast.LENGTH_SHORT).show();
        } else if (coPassword.isEmpty() || !coPassword.equals(password)) {
            Toast.makeText(this, "Password and Confirm Password must be the same!", Toast.LENGTH_SHORT).show();
        } else if (phone.isEmpty() || phone.length() < 10 || phone.length() > 12 || !digitsOnly) {
            Toast.makeText(this, "Phone must be Filled with 10 to 12 numbers (number only)", Toast.LENGTH_SHORT).show();
        } else {
            databaseHelper = new DatabaseHelper(RegisterActivity.this);
            databaseHelper.addUser(new User(name, email, password, phone, 0, 0, 0, 0));
            Toast.makeText(this, "Register Success, you must login first", Toast.LENGTH_SHORT).show();
            Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intentLogin);
            finish();
        }
    }
}
