package volapp.example.volonter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.volonter.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import volapp.example.volonter.preferences.UserPreferences;

public class Account extends AppCompatActivity {
    private CircleImageView imageAccount;
    private TextView account_name, account_lastname, account_post, status;
    private Button my_events_button;
    private StorageReference storageProfilePictureRef;
    private StorageTask uploadTask;

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.profile:
                    return true;
                case R.id.events:
                    startActivity(new Intent(getApplicationContext(), Events.class));
                    finish();
                    return true;
                case R.id.logout:
                    UserPreferences uPref = new UserPreferences(getApplicationContext());
                    uPref.setEntered(false);
                    startActivity(new Intent(getApplicationContext(), Authorization.class));
                    finish();
                    return true;
            }
            return false;
        });


        imageAccount = findViewById(R.id.imageAccount);
        account_name = findViewById(R.id.account_name);
        account_lastname = findViewById(R.id.account_lastname);
        account_post = findViewById(R.id.account_post);
        status = findViewById(R.id.status);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        storageProfilePictureRef = FirebaseStorage.getInstance().getReference().child("Profile pictures");

        userDisplay(imageAccount, account_name,account_lastname, account_post);

        my_events_button = findViewById(R.id.my_events_button);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference users = db.getReference("User");
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        users.child(userId).child("points").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer rating = snapshot.getValue(Integer.class);
                if (rating<=200) {
                    progressBar.setProgress(rating);
                    progressBar.getProgressDrawable().setColorFilter(Color.rgb(227, 138, 73), android.graphics.PorterDuff.Mode.SRC_IN);
                    status.setText("Медь");
                } else if (rating>200 && rating<=400) {
                    progressBar.setProgress(rating-200);
                    progressBar.getProgressDrawable().setColorFilter(Color.rgb(142, 85, 0), android.graphics.PorterDuff.Mode.SRC_IN);
                    status.setText("Бронза");
                } else if (rating>400 && rating<=600) {
                    progressBar.setProgress(rating-400);
                    progressBar.getProgressDrawable().setColorFilter(Color.rgb(114, 114, 114), android.graphics.PorterDuff.Mode.SRC_IN);
                    status.setText("Серебро");
                } else if (rating>600 && rating<=800) {
                    progressBar.setProgress(rating-600);
                    progressBar.getProgressDrawable().setColorFilter(Color.rgb(255, 206, 82 ), android.graphics.PorterDuff.Mode.SRC_IN);
                    status.setText("Золото");
                } else if (rating>800 && rating<=1000) {
                    progressBar.setProgress(rating-800);
                    progressBar.getProgressDrawable().setColorFilter(Color.rgb(98, 222, 222), android.graphics.PorterDuff.Mode.SRC_IN);
                    status.setText("Алмаз");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw new RuntimeException();
            }
        });

        users.child(userId).child("post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String post = snapshot.getValue(String.class);
                if (post.equals("Вожатый")) {
                    my_events_button.setText("Активные заявки");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Account.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


        my_events_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MyEventsActivity.class));
                finish();
            }
        });
    }

    private void userDisplay(final CircleImageView imageAccount, final TextView account_name, final TextView account_lastname, final TextView account_post) {

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("User").child(currentFirebaseUser.getUid());

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.child("image").exists())
                    {
                        String image = dataSnapshot.child("image").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String lastname = dataSnapshot.child("lastname").getValue().toString();
                        String post = dataSnapshot.child("post").getValue().toString();

                        Picasso.get().load(image).into(imageAccount);
                        account_name.setText(name);
                        account_lastname.setText(lastname);
                        account_post.setText(post);
                    }
                    if (dataSnapshot.child("name").exists())
                    {
                        String name = dataSnapshot.child("name").getValue().toString();
                        String lastname = dataSnapshot.child("lastname").getValue().toString();
                        String post = dataSnapshot.child("post").getValue().toString();

                        account_name.setText(name);
                        account_lastname.setText(lastname);
                        account_post.setText(post);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}