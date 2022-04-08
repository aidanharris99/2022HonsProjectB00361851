package uws.ah.bustrackergeo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class AddBusStopActivity extends AppCompatActivity {

    private EditText busStopNameTxt2;
    private Button saveBtn;
    private DatabaseReference rootDataBaseref;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus_stop);

        saveBtn = findViewById(R.id.createLatLngButton);
        busStopNameTxt2 = findViewById(R.id.mapBusStopName2);

        rootDataBaseref = FirebaseDatabase.getInstance().getReference().child("Locations");

        double lat = (double) getIntent().getExtras().get("LAT");
        double lng = (double) getIntent().getExtras().get("LNG");

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomLatLng cusLatLng = new CustomLatLng(lat, lng);
                String busStopNameStr = busStopNameTxt2.getText().toString();

                if (busStopNameStr.isEmpty()){
                    busStopNameTxt2.setError("Stop name is required!");
                    busStopNameTxt2.requestFocus();
                    return;
                }

                HashMap hashMap = new HashMap();
                hashMap.put("LatitudeLongitude", cusLatLng);
                hashMap.put("Name", busStopNameStr);

                String key = busStopNameStr.toLowerCase();

                rootDataBaseref.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddBusStopActivity.this, "Your Location was successfully added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddBusStopActivity.this, ManageBusStopsActivity.class);
                        startActivity(intent);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddBusStopActivity.this, "Your Location was NOT added", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}