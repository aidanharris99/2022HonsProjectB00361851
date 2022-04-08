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

import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText etRegEmail;
    TextInputEditText etRegPassword;
    TextInputEditText etRegFullName;
    TextView tvLoginHere;
    Button btnRegister;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        etRegFullName = findViewById(R.id.etRegFullName);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPass);
        tvLoginHere = findViewById(R.id.tvLoginHere);
        btnRegister = findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();
        tvLoginHere.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvLoginHere:
                Intent intent = (new Intent(this, LoginActivity.class));
                startActivity(intent);

            case R.id.btnRegister:
                registerUser();
        }


    }

    private void registerUser() {
        String email = etRegEmail.getText().toString();
        String password = etRegPassword.getText().toString().trim();
        String fullName = etRegFullName.getText().toString().trim();

        if (fullName.isEmpty()){
            etRegFullName.setError("Full name is required!");
            etRegFullName.requestFocus();
            return;
        }

        if (email.isEmpty()){
            etRegEmail.setError("Email is required!");
            etRegEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etRegEmail.setError("Valid email is required!");
            etRegEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            etRegPassword.setError("Password is required!");
            etRegPassword.requestFocus();
            return;
        }

        if (password.length() < 6){
            etRegPassword.setError("Password is required to be more than 6 characters!");
            etRegPassword.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(fullName, email, Boolean.FALSE);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "User has been registered", Toast.LENGTH_LONG).show();
                                        Intent intent =(new Intent(RegisterActivity.this, LoginActivity.class));
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(RegisterActivity.this, "Failed to register, Try again!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }else{
                            Toast.makeText(RegisterActivity.this, "Failed to register, Try again!", Toast.LENGTH_LONG).show();
                        }

                    }
                });


    }
}