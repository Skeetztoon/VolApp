package volapp.example.volonter.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import volapp.example.volonter.model.User;

public class UserPreferences {
    SharedPreferences myPrefs;
    SharedPreferences.Editor prefEditor;
    Context context;

    private static final String FILE_NAME = "CurrentUser";

    public UserPreferences(Context context) {
        this.context = context;
        myPrefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }


    public void setUser(User user){
        prefEditor = myPrefs.edit();
        prefEditor.putString("name", user.getName());
        prefEditor.putString("lastname", user.getLastname());
        prefEditor.putString("age", user.getAge());
        prefEditor.putString("login", user.getLogin());
        prefEditor.putString("password", user.getPassword());
        prefEditor.putString("post", user.getPost());
        prefEditor.putInt("points", user.getPoints());
        prefEditor.apply();
    }

    public User getUser(){
        User user = new User();
        user.setName(myPrefs.getString("name", ""));
        user.setLastname(myPrefs.getString("lastname", ""));
        user.setAge(myPrefs.getString("age", ""));
        user.setLogin(myPrefs.getString("login", ""));
        user.setPassword(myPrefs.getString("password", ""));
        user.setPost(myPrefs.getString("post", ""));
        user.setPoints(myPrefs.getInt("points", 0));
        return user;
    }

    public void setEntered(boolean bool) {
        prefEditor = myPrefs.edit();
        prefEditor.putBoolean("HAS_ENTERED", bool);
        prefEditor.apply();

    }

    public boolean getEntered() {
        return myPrefs.getBoolean("HAS_ENTERED", false);
    }
}