package volapp.example.volonter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volonter.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import volapp.example.volonter.ViewHolder.MyViewHolder;
import volapp.example.volonter.model.Event;
import volapp.example.volonter.model.EventApplication;

public class Events extends AppCompatActivity {

    private TextView events_app, events_open_text;
    private Button events_button1;

    FirebaseDatabase db;
    DatabaseReference events, eventsRef, users, register, user;
    ConstraintLayout element;
    String id;

    private RecyclerView recyclerView, categoryRecycler;
    RecyclerView.LayoutManager layoutManager;
    SearchView search_event;
    ArrayList<Event> event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.events);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.profile:
                    startActivity(new Intent(getApplicationContext(), Account.class));
                    finish();
                    return true;
                case R.id.events:
                    return true;
                case R.id.logout:
                    startActivity(new Intent(getApplicationContext(), Authorization.class));
                    finish();
                    return true;
            }
            return false;
        });


        events_app = findViewById(R.id.events_app);
        events_button1 = findViewById(R.id.events_button1);

        element = findViewById(R.id.events_element);
        ;

        // Карточки с мероприятиями
        eventsRef = FirebaseDatabase.getInstance().getReference().child("Event");
        recyclerView = findViewById(R.id.recycler_events);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        db = FirebaseDatabase.getInstance();
        users = db.getReference("User");

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        users.child(userId).child("post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String post = snapshot.getValue(String.class);
                if (post.equals("Волонтер")) {
                    events_button1.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Events.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        events_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showActivityNewEvents();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Event> options = new FirebaseRecyclerOptions.Builder<Event>()
                .setQuery(eventsRef, Event.class).build();

        FirebaseRecyclerAdapter<Event, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Event, MyViewHolder> (options) {
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
                MyViewHolder holder = new MyViewHolder(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position,@NonNull Event model) {
                holder.events_item_text.setText(model.getName_events());
                holder.events_item_date.setText("Дата: " + model.getDate());
                holder.events_item_organizer.setText("Организатор: " + model.getOrganizer());
                holder.events_item_place.setText("Место: " + model.getPlace());
                holder.events_item_direction.setText("Направление: " + model.getDirection());
                holder.join_event_button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
//                        Логика для добавления в EventApplications
                        db = FirebaseDatabase.getInstance();
                        register = db.getReference("EventApplication");
                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        String currUsername = db.getReference("User").child(userId).child("name").toString();
                        String key = register.push().getKey();

                        EventApplication application = new EventApplication();
                        application.setKey(key);
                        application.setMem_id(userId);
                        application.setName_event(model.getName_events());
                        application.setPlace(model.getPlace());
                        application.setDate(model.getDate());
                        application.setOrg(model.getOrganizer());

                        register.child(key).setValue(application)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Snackbar.make(element,"Заявка отправлена", Snackbar.LENGTH_SHORT).show();
                                    }
                                });
                        }
                });
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }



    private void showActivityNewEvents() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View activity_new_events = inflater.inflate(R.layout.activity_new_events, null);
        dialog.setView(activity_new_events);

        EditText name = activity_new_events.findViewById(R.id.name_events_field);
        EditText date = activity_new_events.findViewById(R.id.data_field);
        EditText organizer = activity_new_events.findViewById(R.id.organizer_field);
        EditText place = activity_new_events.findViewById(R.id.place_field);
        Spinner direction = activity_new_events.findViewById(R.id.direction_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.napravlenie, android.R.layout.simple_spinner_dropdown_item);
        direction.setAdapter(adapter);

        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(TextUtils.isEmpty(name.getText().toString()) ||
                        TextUtils.isEmpty(date.getText().toString()) ||
                        TextUtils.isEmpty(organizer.getText().toString()) ||
                        TextUtils.isEmpty(place.getText().toString())) {
                    Snackbar.make(element, "Заполните все поля", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                //Создание нового мероприятия
                db = FirebaseDatabase.getInstance();
                events = db.getReference("Event");

                id = events.push().getKey();

                Event event = new Event();
                event.setName_events(name.getText().toString());
                event.setDate(date.getText().toString());
                event.setOrganizer(organizer.getText().toString());
                event.setPlace(place.getText().toString());
                event.setDirection(direction.getSelectedItem().toString());
                events.child(id).setValue(event)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Snackbar.make(element,"Мероприятие добавлено", Snackbar.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        dialog.show();
    }
}