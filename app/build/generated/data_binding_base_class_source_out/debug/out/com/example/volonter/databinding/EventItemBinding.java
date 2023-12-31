// Generated by view binder compiler. Do not edit!
package com.example.volonter.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.volonter.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class EventItemBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView eventsItemDate;

  @NonNull
  public final TextView eventsItemDirection;

  @NonNull
  public final TextView eventsItemOrganizer;

  @NonNull
  public final TextView eventsItemPlace;

  @NonNull
  public final TextView eventsItemText;

  @NonNull
  public final ImageView imageEvents;

  @NonNull
  public final Button joinEventButton;

  private EventItemBinding(@NonNull CardView rootView, @NonNull TextView eventsItemDate,
      @NonNull TextView eventsItemDirection, @NonNull TextView eventsItemOrganizer,
      @NonNull TextView eventsItemPlace, @NonNull TextView eventsItemText,
      @NonNull ImageView imageEvents, @NonNull Button joinEventButton) {
    this.rootView = rootView;
    this.eventsItemDate = eventsItemDate;
    this.eventsItemDirection = eventsItemDirection;
    this.eventsItemOrganizer = eventsItemOrganizer;
    this.eventsItemPlace = eventsItemPlace;
    this.eventsItemText = eventsItemText;
    this.imageEvents = imageEvents;
    this.joinEventButton = joinEventButton;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static EventItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static EventItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.event_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static EventItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.events_item_date;
      TextView eventsItemDate = ViewBindings.findChildViewById(rootView, id);
      if (eventsItemDate == null) {
        break missingId;
      }

      id = R.id.events_item_direction;
      TextView eventsItemDirection = ViewBindings.findChildViewById(rootView, id);
      if (eventsItemDirection == null) {
        break missingId;
      }

      id = R.id.events_item_organizer;
      TextView eventsItemOrganizer = ViewBindings.findChildViewById(rootView, id);
      if (eventsItemOrganizer == null) {
        break missingId;
      }

      id = R.id.events_item_place;
      TextView eventsItemPlace = ViewBindings.findChildViewById(rootView, id);
      if (eventsItemPlace == null) {
        break missingId;
      }

      id = R.id.events_item_text;
      TextView eventsItemText = ViewBindings.findChildViewById(rootView, id);
      if (eventsItemText == null) {
        break missingId;
      }

      id = R.id.image_events;
      ImageView imageEvents = ViewBindings.findChildViewById(rootView, id);
      if (imageEvents == null) {
        break missingId;
      }

      id = R.id.join_event_button;
      Button joinEventButton = ViewBindings.findChildViewById(rootView, id);
      if (joinEventButton == null) {
        break missingId;
      }

      return new EventItemBinding((CardView) rootView, eventsItemDate, eventsItemDirection,
          eventsItemOrganizer, eventsItemPlace, eventsItemText, imageEvents, joinEventButton);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
