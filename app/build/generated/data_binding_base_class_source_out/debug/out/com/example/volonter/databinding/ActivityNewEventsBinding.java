// Generated by view binder compiler. Do not edit!
package com.example.volonter.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.volonter.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityNewEventsBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView accountApp;

  @NonNull
  public final EditText dataField;

  @NonNull
  public final Spinner directionSpinner;

  @NonNull
  public final EditText nameEventsField;

  @NonNull
  public final TextView naprText;

  @NonNull
  public final EditText organizerField;

  @NonNull
  public final EditText placeField;

  private ActivityNewEventsBinding(@NonNull ConstraintLayout rootView, @NonNull TextView accountApp,
      @NonNull EditText dataField, @NonNull Spinner directionSpinner,
      @NonNull EditText nameEventsField, @NonNull TextView naprText,
      @NonNull EditText organizerField, @NonNull EditText placeField) {
    this.rootView = rootView;
    this.accountApp = accountApp;
    this.dataField = dataField;
    this.directionSpinner = directionSpinner;
    this.nameEventsField = nameEventsField;
    this.naprText = naprText;
    this.organizerField = organizerField;
    this.placeField = placeField;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityNewEventsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityNewEventsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_new_events, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityNewEventsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.account_app;
      TextView accountApp = ViewBindings.findChildViewById(rootView, id);
      if (accountApp == null) {
        break missingId;
      }

      id = R.id.data_field;
      EditText dataField = ViewBindings.findChildViewById(rootView, id);
      if (dataField == null) {
        break missingId;
      }

      id = R.id.direction_spinner;
      Spinner directionSpinner = ViewBindings.findChildViewById(rootView, id);
      if (directionSpinner == null) {
        break missingId;
      }

      id = R.id.name_events_field;
      EditText nameEventsField = ViewBindings.findChildViewById(rootView, id);
      if (nameEventsField == null) {
        break missingId;
      }

      id = R.id.napr_text;
      TextView naprText = ViewBindings.findChildViewById(rootView, id);
      if (naprText == null) {
        break missingId;
      }

      id = R.id.organizer_field;
      EditText organizerField = ViewBindings.findChildViewById(rootView, id);
      if (organizerField == null) {
        break missingId;
      }

      id = R.id.place_field;
      EditText placeField = ViewBindings.findChildViewById(rootView, id);
      if (placeField == null) {
        break missingId;
      }

      return new ActivityNewEventsBinding((ConstraintLayout) rootView, accountApp, dataField,
          directionSpinner, nameEventsField, naprText, organizerField, placeField);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
