package uws.ah.bustrackergeo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ManageBusStopsActivity extends AppCompatActivity {

    EditText inputSearch;
    RecyclerView recyclerView;
    FloatingActionButton floatingbtn;
    ImageButton homebtn;

    private DatabaseReference rootDataBaseref;

    private FirebaseRecyclerOptions<BusStop> options;
    private FirebaseRecyclerAdapter<BusStop, MyViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bus_stops);

        inputSearch = findViewById(R.id.inputSearch);
        recyclerView=findViewById(R.id.recyclerViewLocations);
        floatingbtn=findViewById(R.id.floatingbtn);
        homebtn=findViewById(R.id.homebtn);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        rootDataBaseref = FirebaseDatabase.getInstance().getReference().child("Locations");

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageBusStopsActivity.this, AdminMainActivity.class);
                startActivity(intent);
            }
        });

        floatingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageBusStopsActivity.this, MapsActivityAddLocation.class);
                startActivity(intent);
            }
        });

        loadData("");
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString()!=null){
                    loadData(editable.toString());
                }else{
                    loadData("");
                }

            }
        });

    }

    private void loadData(String data) {
        Query query = rootDataBaseref.orderByChild("Name").startAt(data).endAt(data+"\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<BusStop>().setQuery(query, BusStop.class).build();
        adapter = new FirebaseRecyclerAdapter<BusStop, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull BusStop BusStop) {
                final CustomLatLng customLatLng =BusStop.getLatitudeLongitude();
                final String Name = BusStop.getName();

                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), TargetActivity.class);
                        intent.putExtra("KEY", getRef(position).getKey());
                        intent.putExtra("LATLNG", customLatLng);
                        intent.putExtra("NAME", Name);
                        startActivity(intent);

                    }
                });
                holder.textViewName.setText(BusStop.getName());

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_layout,parent,false);
                return new MyViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}