package uws.ah.bustrackergeo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TargetActivity extends AppCompatActivity {

    TextView textViewLatLng, textViewName;
    Button deleteBtn;
    FloatingActionButton backBtn;

    DatabaseReference bRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        textViewLatLng=findViewById(R.id.textViewLatLng);
        textViewName=findViewById(R.id.textViewName);
        deleteBtn = findViewById(R.id.deleteButton);
        backBtn = findViewById(R.id.backBtn);
        LatLng ll= (LatLng) getIntent().getExtras().get("LATLNG");
        String name = getIntent().getStringExtra("NAME");
        String bKey = getIntent().getStringExtra("KEY");
        textViewLatLng.setText(""+ll);
        textViewName.setText(name);
        bRef = FirebaseDatabase.getInstance().getReference().child("Locations").child(bKey);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        startActivity(new Intent(TargetActivity.this, ManageBusStopsActivity.class));
                    }
                });

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TargetActivity.this, ManageBusStopsActivity.class));
            }
        });



    }
}