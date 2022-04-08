package uws.ah.bustrackergeo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText etLoginEmail;
    TextInputEditText etLoginPassword;
    TextView tvRegisterHere, forgotPasword;
    Button btnLogin;

    private DatabaseReference userDbRef;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPass);
        tvRegisterHere = findViewById(R.id.tvRegisterHere);
        btnLogin = findViewById(R.id.btnLogin);
        forgotPasword = findViewById(R.id.forgotPassword);

        mAuth = FirebaseAuth.getInstance();
        userDbRef = FirebaseDatabase.getInstance().getReference().child("Users");

        btnLogin.setOnClickListener(this);
        tvRegisterHere.setOnClickListener(this);
        forgotPasword.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvRegisterHere:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;

            case R.id.btnLogin:
                loginUser();
                break;

            case R.id.forgotPassword:
                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
                break;
        }
    }


    private void loginUser() {
        String email = etLoginEmail.getText().toString().trim();
        String password = etLoginPassword.getText().toString().trim();

        if (email.isEmpty()){
            etLoginEmail.setError("Email is required!");
            etLoginEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etLoginEmail.setError("Valid email is required!");
            etLoginEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            etLoginPassword.setError("Password is required!");
            etLoginPassword.requestFocus();
            return;
        }

        if (password.length() < 6){
            etLoginPassword.setError("Password is required to be more than 6 characters!");
            etLoginPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user.isEmailVerified()){
                        //login to user screen
                        checkElevatedPriv(user.getUid());

                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this, "Check Your email to verify your account", Toast.LENGTH_LONG).show();

                    }




                }else{
                    Toast.makeText(LoginActivity.this, "Failed to login, please check your details!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void checkElevatedPriv(String uid) {
        userDbRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Boolean privLevel = (Boolean) snapshot.child("elevatedPriv").getValue();
                    if (privLevel){
                        startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
                    } else {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(LoginActivity.this, "Failed to login, please check your details!", Toast.LENGTH_LONG).show();

            }
        });
    }
}