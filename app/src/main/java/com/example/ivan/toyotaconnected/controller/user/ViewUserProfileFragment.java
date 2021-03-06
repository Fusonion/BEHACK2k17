package com.example.ivan.toyotaconnected.controller.user;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ivan.toyotaconnected.R;
import com.example.ivan.toyotaconnected.controller.HomeActivity;
import com.example.ivan.toyotaconnected.dao.ContentProvider;
import com.example.ivan.toyotaconnected.dao.ContentProviderFactory;
import com.example.ivan.toyotaconnected.model.user.User;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

/**
 * Activity to edit the user profile
 *
 * @author tejun, chase
 */
public class ViewUserProfileFragment extends Fragment {

    // Intent message ids
    private static final int SELECT_PHOTO = 1;

    // Field views
    private EditText nameField;
    private EditText emailField;
    private EditText addressField;
    private ImageView profilePicture;

    // User passed into this activity
    private User user;

    /**
     * Default constructor with no args
     */
    public ViewUserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        // Set up the fields for the user profile
        nameField = (EditText) view.findViewById(R.id.user_profile_name_field);
        emailField = (EditText) view.findViewById(R.id.user_profile_email);
        addressField = (EditText) view.findViewById(R.id.user_profile_address);
        profilePicture = (ImageView) view.findViewById(R.id.profile_picture);
        profilePicture.setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
        });

        // Get the user from the session
        ContentProvider contentProvider = ContentProviderFactory.getDefaultContentProvider();
        user = contentProvider.getLoggedInUser();

        // Set up the text pre-defined values
        nameField.setText(user.getName());
        emailField.setText(user.getEmail());
        addressField.setText(user.getAddress());

        // Populate the profile picture
        String picture = user.getProfilePicture();
        if (picture != null) {
            profilePicture.setImageBitmap(User.stringToBitmap(picture));
        }

        // Done button setup
        Button profileDoneButton = (Button) view.findViewById(R.id.profile_done_button);
        profileDoneButton.setOnClickListener((view2) -> onProfileDonePressed());

        // Cancel button setup
        Button profileCancelButton = (Button) view.findViewById(R.id.profile_cancel_button);
        profileCancelButton.setOnClickListener((view1) -> onCancelPressed());

        return view;
    }

    /**
     * Method to exit the activity to main.
     */
    private void onProfileDonePressed() {
        // Update the user model from the fields
        String nameText = nameField.getText().toString();
        if (TextUtils.isEmpty(nameText)) {
            nameField.setError("Must enter a name!");
            nameField.requestFocus();
            return;
        }
        user.setName(nameText);

        String emailText = emailField.getText().toString();
        if (TextUtils.isEmpty(emailText)) {
            emailField.setError("Must enter a email!");
            emailField.requestFocus();
            return;
        }
        if (!User.isEmailValid(emailText)) {
            emailField.setError("Email is not valid!");
            emailField.requestFocus();
            return;
        }
        user.setEmail(emailText);

        String addressText = addressField.getText().toString();
        if (TextUtils.isEmpty(addressText)) {
            addressField.setError("Must enter an address!");
            addressField.requestFocus();
            return;
        }
        user.setAddress(addressText);

        // Set the profile picture
        Drawable drawable = profilePicture.getDrawable();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        user.setProfilePicture(User.bitmapToString(bitmap));

        // Store the user in our content provider, overriding all previous data for that user
        ContentProvider contentProvider = ContentProviderFactory.getDefaultContentProvider();
        contentProvider.setUser(user);

        // Also store it in the session
        contentProvider.setLoggedInUser(user);

        // Transition back to the map fragment
        ((HomeActivity) getActivity()).setHeaderInfo(user);
        Activity act = getActivity();
        act.onBackPressed();
    }

    /**
     * Method to exit the activity back to caller.
     */
    private void onCancelPressed() {
        Activity act = getActivity();
        act.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = this.getActivity().getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        profilePicture.setImageBitmap(Bitmap.createScaledBitmap(selectedImage, 100, 100, false));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }
}


