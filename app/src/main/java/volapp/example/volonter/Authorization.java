package volapp.example.volonter;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.volonter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import volapp.example.volonter.model.User;
import volapp.example.volonter.preferences.UserPreferences;

public class Authorization extends AppCompatActivity {


    private EditText login, password;
    private Button loginButton, singnupButton;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    ConstraintLayout root;

    String text;

//    RadioButton radio_vol = findViewById(R.id.radio_volonter);
//    RadioButton radio_voj = findViewById(R.id.radio_vojatiy);

//    public static Activity a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);


        UserPreferences pref= new UserPreferences(this);
        if (pref.getEntered()) {
            Intent intent = new Intent(Authorization.this, Account.class);
            startActivity(intent);
        }

        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        singnupButton = findViewById(R.id.singnup_button);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("User");

        root = findViewById(R.id.root_element);

        singnupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterWindow();
            }
        });

        //Авторизация
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(Authorization.this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(login.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        UserPreferences pref= new UserPreferences(view.getContext());
                                        pref.setEntered(true);
                                        Intent intent = new Intent(Authorization.this, Account.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(Authorization.this, "Неправильный логин или пароль", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
//                auth();
            }
        });
    }

    private void showRegisterWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View register_window = inflater.inflate(R.layout.register_window, null);
        dialog.setView(register_window);

        EditText name = register_window.findViewById(R.id.name_field);
        EditText lastname = register_window.findViewById(R.id.lastname_field);
        EditText age = register_window.findViewById(R.id.age_field);
        EditText login = register_window.findViewById(R.id.login_field);
        EditText password = register_window.findViewById(R.id.password_field);
        // Выпадающее меню
        Spinner post = register_window.findViewById(R.id.post_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.post_spinner, android.R.layout.simple_spinner_dropdown_item);
        post.setAdapter(adapter);

        // Кнопка регистрации
        dialog.setPositiveButton("Готово", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(TextUtils.isEmpty(name.getText().toString()) ||
                        TextUtils.isEmpty(lastname.getText().toString()) ||
                        TextUtils.isEmpty(login.getText().toString()) ||
                        (password.getText().toString().length() < 5))  {
                    Snackbar.make(root, "Заполните все поля", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                //Добавление в FIREBASE
                auth.createUserWithEmailAndPassword(login.getText().toString(), password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                User user = new User();
                                user.setName(name.getText().toString());
                                user.setLastname(lastname.getText().toString());
                                user.setAge(age.getText().toString());
                                user.setLogin(login.getText().toString());
                                user.setPassword(password.getText().toString());
                                user.setPoints(0);
                                user.setPost(post.getSelectedItem().toString());

                                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()) //ключ при регистрации
                                        .setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Snackbar.make(root, "Вы зарегистрировались", Snackbar.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
            }
        });

        dialog.show();
    }
}