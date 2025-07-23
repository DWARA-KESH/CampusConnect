package dwuu.demo.grievenceapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;

public class splashPreLoader extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_pre_loader);

        LottieAnimationView animationView = findViewById(R.id.lottieAnimationView);
        animationView.setAnimation("mainPreLoader.json");
        animationView.playAnimation();

        new Handler().postDelayed(
                () -> {
                    SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                    String email = sharedPreferences.getString("email", "");
                    boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

                    if (isLoggedIn) {
                        Intent i = new Intent(this, HomePage.class);
                        i.putExtra("email", email);
                        startActivity(i);
                        finish();
                    } else {
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    }
                },
                1500
        );
    }


}
