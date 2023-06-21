package volapp.example.volonter.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import volapp.example.volonter.Interface.ItemClickListener;
import com.example.volonter.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyEventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView my_events_item_text, my_events_item_place, my_events_item_lastname, my_events_item_name;
    public ImageView my_image_events;
    public ItemClickListener listener;
    public Button plus_rep_button, cancel_application_button;

    public LinearLayout fiolayout;

    public MyEventViewHolder(View itemView) {
        super(itemView);

        my_events_item_name = itemView.findViewById(R.id.my_events_item_name);
        my_events_item_lastname = itemView.findViewById(R.id.my_events_item_lastname);
        my_events_item_text = itemView.findViewById(R.id.my_events_item_text);
        my_events_item_place = itemView.findViewById(R.id.my_events_item_place);
        plus_rep_button = itemView.findViewById(R.id.plus_rep_button);
        cancel_application_button = itemView.findViewById(R.id.cancel_application_button);
        my_image_events = itemView.findViewById(R.id.my_image_events);
        fiolayout = itemView.findViewById(R.id.fio_layout);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference users = db.getReference("User");

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        users.child(userId).child("post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String post = snapshot.getValue(String.class);
                if (post.equals("Вожатый")) {
                    my_events_item_place.setVisibility(View.GONE);
                    cancel_application_button.setVisibility(View.GONE);
                }
                if (post.equals("Волонтер")) {
                    fiolayout.setVisibility(View.GONE);
                    plus_rep_button.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                return;
            }
        });
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(),false);
    }
}
