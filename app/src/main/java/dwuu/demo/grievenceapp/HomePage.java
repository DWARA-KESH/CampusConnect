package dwuu.demo.grievenceapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;


public class HomePage extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private TextView usernameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        Toolbar toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(findViewById(R.id.toolbar));

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View headerView = navigationView.getHeaderView(0);
        usernameText = headerView.findViewById(R.id.header_title);

        String email = getIntent().getStringExtra("email");

        String username = getUsername(email);
        usernameText.setText("Welcome, " + username);

        HomeFragment homeFragment = HomeFragment.newInstance(email);
        navigationView.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, homeFragment)
                        .addToBackStack(null)
                        .commit();
            } else if (id == R.id.nav_about) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new AboutFragment())
                        .addToBackStack(null)
                        .commit();
            } else if (id == R.id.nav_logout) {
                SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("email");
                editor.putBoolean("isLoggedIn", false);
                editor.apply();
                startActivity(new Intent(this, MainActivity.class));
                finish();

            } else if (id == R.id.nav_navi) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new ImageContainerFragment())
                        .addToBackStack(null)
                        .commit();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

        @Override
        public void onBackPressed() {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                } else {
                    super.onBackPressed();
                }
            }
        }

    private String getUsername(String email) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        return databaseHelper.getUsername(email);
    }
}
