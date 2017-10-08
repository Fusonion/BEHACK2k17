package com.example.ivan.toyotaconnected.dao;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.ivan.toyotaconnected.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Specific type of content provider that uses firebase
 *
 * @author tejun
 */
class FirebaseContentProvider extends SessionContentProvider implements ContentProvider {

    private final DatabaseReference mDatabase;

    FirebaseContentProvider() {
        super();

        // Firebase database authentication
        FirebaseDatabase dBInstance = FirebaseDatabase.getInstance();
        mDatabase = dBInstance.getReference();
    }

    @Override
    public void getAllUsers(Consumer<List<User>> callback) {
        DatabaseReference databaseRef = mDatabase.child("users");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<>();

                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    users.add(user.getValue(User.class));
                }

                callback.accept(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Do nothing
            }
        });
    }

    @Override
    public void getSingleUser(Consumer<User> callback, String username) {
        DatabaseReference databaseRef = mDatabase.child("users");
        DatabaseReference databaseRef2 = databaseRef.child(username);
        databaseRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    // Found a user with a matching username
                    // Extract out the user object from firebase
                    User user = dataSnapshot.getValue(User.class);

                    // Call the provided callback
                    callback.accept(user);
                } else {
                    callback.accept(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Do nothing yet
            }
        });
    }

    @Override
    public void setUser(User user) {
        DatabaseReference faceBookDatabase = mDatabase.child("users");
        DatabaseReference faceBookDatabase2 = faceBookDatabase.child(user.getUsername());
        faceBookDatabase2.setValue(user);
    }





}
