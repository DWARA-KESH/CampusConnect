package dwuu.demo.grievenceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private TextView textView;
    private String[] words = {"Hello!", "Vanakkam!","Bonjour!", "Hallo!", "Namaste!" };
    private int currentWordIndex = 0;
    private int currentCharIndex = 0;

    private Handler handler = new Handler() {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        textView = findViewById(R.id.textView3);

        handler.sendEmptyMessage(0);

        CheckBox checkBoxShowPassUp = findViewById(R.id.checkBox2);
        EditText passwordEditText = findViewById(R.id.signupPasswordText);
        EditText confirmPasswordEditText = findViewById(R.id.signupConfPassText);

        checkBoxShowPassUp.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int inputType = isChecked ?
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                    InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;

            passwordEditText.setInputType(inputType);
            confirmPasswordEditText.setInputType(inputType);
        });
    }

    public void goToLoginPage(View view) {
        finish();
    }

    public void signupButtonClicked(View view) {
        EditText emailEditText = findViewById(R.id.signupEmailText);
        EditText usernameEditText = findViewById(R.id.signupUsernameText);
        EditText passwordEditText = findViewById(R.id.signupPasswordText);
        EditText confirmPasswordEditText = findViewById(R.id.signupConfPassText);

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
        } else {
            if (password.equals(confirmPassword)) {
                DatabaseHelper databaseHelper = new DatabaseHelper(this);
                boolean checkUserEmail = databaseHelper.checkEmail(email);

                if (!checkUserEmail) {
                    boolean insert = databaseHelper.insertData(email, username, password);
                    if (insert) {
                        SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", email);
                        editor.putBoolean("isLoggedIn", true);
                        editor.apply();

                        Toast.makeText(this, "Signup Successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, HomePage.class);
                        intent.putExtra("email",email);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Signup Failed!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "User already exists! Please login", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
        }
    }
}