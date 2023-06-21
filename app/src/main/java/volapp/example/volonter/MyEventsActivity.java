package volapp.example.volonter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volonter.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import volapp.example.volonter.ViewHolder.MyEventViewHolder;
import volapp.example.volonter.model.EventApplication;

public class MyEventsActivity extends AppCompatActivity{

    FirebaseDatabase db;
    DatabaseReference eventsRef, users, user, currEvent;

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;



    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.events);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.profile:
                    startActivity(new Intent(getApplicationContext(), Account.class));
                    finish();
                    return true;
                case R.id.events:
                    startActivity(new Intent(getApplicationContext(), Events.class));
                    finish();
                    return true;
                case R.id.logout:
                    startActivity(new Intent(getApplicationContext(), Authorization.class));
                    finish();
                    return true;
            }
            return false;
        });

        TextView my_events_app = findViewById(R.id.my_events_app);
        db = FirebaseDatabase.getInstance();
        users = db.getReference("User");

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        users.child(userId).child("post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String post = snapshot.getValue(String.class);
                if (post.equals("Вожатый")) {
                    my_events_app.setText("Активные участия");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyEventsActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        // Карточки с мероприятиями
        eventsRef = FirebaseDatabase.getInstance().getReference().child("EventApplication");
        recyclerView = findViewById(R.id.my_recycler_events);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<EventApplication> options = new FirebaseRecyclerOptions.Builder<EventApplication>()
                .setQuery(eventsRef, EventApplication.class).build();

        FirebaseRecyclerAdapter<EventApplication, MyEventViewHolder> adapter = new FirebaseRecyclerAdapter<EventApplication, MyEventViewHolder> (options) {
            @Override
            public MyEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_event_item, parent, false);
                MyEventViewHolder holder = new MyEventViewHolder(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull MyEventViewHolder holder, int position,@NonNull EventApplication model) {
                holder.my_events_item_text.setText(model.getName_event());
                holder.my_events_item_place.setText("Место: " + model.getPlace());
                holder.plus_rep_button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
//                        Логика для добавления баллов
                        db = FirebaseDatabase.getInstance();
                        user = db.getReference("User");
                        user.child(model.getMem_id()).child("points").setValue(ServerValue.increment(50));
                        currEvent = db.getReference("EventApplication").child(model.getKey());
                        currEvent.removeValue();
                    }
                });



                holder.cancel_application_button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
//                        Логика для удаления из EventApplications
                        db = FirebaseDatabase.getInstance();
                        currEvent = db.getReference("EventApplication").child(model.getKey());
                        currEvent.removeValue();
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}