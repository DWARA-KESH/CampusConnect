package dwuu.demo.grievenceapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private NetworkChangeReceiver networkChangeReceiver;
    private boolean isReceiverRegistered = false;
    private TextView textView;
    private final String[] words = {"Hello!", "Vanakkam!","Bonjour!", "Hallo!", "Namaste!" };
    private int currentWordIndex = 0;
    private int currentCharIndex = 0;

    private boolean isTextViewClicked = false;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //register receiver
        registerNetworkChangeReceiver();

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Initialize TextView for typewriter effect
        textView = findViewById(R.id.textView3);
        handler.sendEmptyMessage(0);

        // Checkbox for showing password
        CheckBox checkBoxShowPassIn = findViewById(R.id.checkBox);
        EditText passwordEditText = findViewById(R.id.loginPasswordText);

        checkBoxShowPassIn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int inputType = isChecked ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                    InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
            passwordEditText.setInputType(inputType);
        });


        final TextView myTextView = findViewById(R.id.textView9);
        myTextView.setOnClickListener(v -> {
            if (isTextViewClicked) {
                myTextView.setTextColor(getResources().getColor(R.color.blue));
            } else {
                myTextView.setTextColor(getResources().getColor(R.color.purple_700));
            }
            isTextViewClicked = !isTextViewClicked;
        });

        // Login button click listener
        Button loginBtn = findViewById(R.id.button3);
        loginBtn.setOnClickListener(v -> {
            EditText emailEditText = findViewById(R.id.loginEmailText);

            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
            } else {
                Boolean checkCredentials = databaseHelper.checkEmailPassword(email, password);

                if(checkCredentials) {
                    SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", email);
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    Toast.makeText(MainActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent  = new Intent(MainActivity.this, HomePage.class);
                    intent.putExtra("email",email);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Typewriter effect handler
    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (currentCharIndex <= words[currentWordIndex].length()) {
                textView.setText(words[currentWordIndex].substring(0, currentCharIndex));
                currentCharIndex++;
                sendEmptyMessageDelayed(0, 150);
            } else {
                currentWordIndex = (currentWordIndex + 1) % words.length;
                currentCharIndex = 0;
                sendEmptyMessageDelayed(0, 1500);
            }
        }
    };

    public void goToSignUpPage(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister receiver
        unregisterNetworkChangeReceiver();
    }

    private void registerNetworkChangeReceiver() {
        if (!isReceiverRegistered) {
            networkChangeReceiver = new NetworkChangeReceiver();
            registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            isReceiverRegistered = true;
        }
    }

    private void unregisterNetworkChangeReceiver() {
        if (isReceiverRegistered && networkChangeReceiver != null) {
            unregisterReceiver(networkChangeReceiver);
            isReceiverRegistered = false;
        }
    }

    private class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!isNetworkAvailable()) {
                showNoInternetDialog();
            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showNoInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No internet connection. Please check your network settings.")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> finish());
        AlertDialog alert = builder.create();
        alert.show();
    }

}