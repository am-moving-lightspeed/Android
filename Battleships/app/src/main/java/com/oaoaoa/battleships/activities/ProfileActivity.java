package com.oaoaoa.battleships.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.oaoaoa.battleships.misc.AnimationsProvider;

import com.oaoaoa.battleships.R;
import com.oaoaoa.battleships.misc.Constants.FirebaseConstants;
import com.squareup.picasso.Picasso;

import java.util.Locale;
import java.util.Objects;

import es.dmoral.toasty.Toasty;



public class ProfileActivity extends AppCompatActivity {

    private ImageView mUserImage;
    private EditText  mUsername;
    private Button    mSave;

    private StorageReference  mStorageReference;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth      mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        AnimationsProvider.startBackgroundGradientAnimation(
            findViewById(R.id.constraintLayout_profile_container)
        );

        mUserImage = findViewById(R.id.userImage);
        mSave      = findViewById(R.id.button_profile_save);
        mUsername  = findViewById(R.id.editText_profile_username);

        mStorageReference  = FirebaseStorage.getInstance()
                                            .getReference("images");
        mDatabaseReference = FirebaseDatabase.getInstance()
                                             .getReference(FirebaseConstants.PROFILE);
        mFirebaseAuth      = FirebaseAuth.getInstance();

        setUserImageEvents();
        setSaveButtonEvents();


        mDatabaseReference.child(mFirebaseAuth.getUid())
                          .addChildEventListener(new ChildEventListener() {

                              @Override
                              public void onChildAdded(@NonNull DataSnapshot snapshot,
                                                       @Nullable String previousChildName) {

                                  if (Objects.equals(snapshot.getKey(), FirebaseConstants.PROFILE_NAME)) {
                                      mUsername.setText(Objects.requireNonNull(snapshot.getValue()).toString());
                                  }
                                  if (Objects.equals(snapshot.getKey(), FirebaseConstants.IMAGE_PATH)) {
                                      Picasso.get().load(Objects.requireNonNull(snapshot.getValue()).toString()).into(
                                          mUserImage);
                                  }
                              }


                              @Override
                              public void onChildChanged(@NonNull DataSnapshot snapshot,
                                                         @Nullable String previousChildName) {}


                              @Override
                              public void onChildRemoved(@NonNull DataSnapshot snapshot) {}


                              @Override
                              public void onChildMoved(@NonNull DataSnapshot snapshot,
                                                       @Nullable String previousChildName) {}


                              @Override
                              public void onCancelled(@NonNull DatabaseError error) {}
                          });
    }


    // region Events
    private void setUserImageEvents() {

        mUserImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                openFileChooser();
            }
        });
    }


    private void setSaveButtonEvents() {

        mSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String name = mUsername.getText().toString().trim();

                if (!name.isEmpty()) {
                    mDatabaseReference.child(mFirebaseAuth.getUid())
                                      .child(FirebaseConstants.PROFILE_NAME)
                                      .setValue(name);

                    showToast(
                        R.string.profile_toasty_saved,
                        R.color.colour_lightSeaGreen
                    );
                }
                else {
                    showToast(
                        R.string.profile_toasty_invalidName,
                        R.color.colour_maroon
                    );
                }
            }
        });
    }
    // endregion


    private void openFileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }


    private void showToast(int text, int colour) {

        Toasty.custom(ProfileActivity.this,
                      text,
                      null,
                      colour,
                      Toast.LENGTH_SHORT,
                      false,
                      true)
              .show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            Picasso.get().load(uri).into(mUserImage);

            String child = String.format(
                Locale.US,
                "%d.%s",
                System.currentTimeMillis(),
                MimeTypeMap.getSingleton()
                           .getExtensionFromMimeType(getContentResolver().getType(uri))
            );

            StorageReference fileReference = mStorageReference.child(child);

            fileReference.putFile(uri)
                         .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                             @Override
                             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                 taskSnapshot.getStorage()
                                             .getDownloadUrl()
                                             .addOnCompleteListener(new OnCompleteListener<Uri>() {

                                                 @Override
                                                 public void onComplete(@NonNull Task<Uri> task) {

                                                     String upload = Objects.requireNonNull(task.getResult())
                                                                            .toString();

                                                     mDatabaseReference.child(Objects.requireNonNull(mFirebaseAuth.getUid()))
                                                                       .child("ImagePath")
                                                                       .setValue(FirebaseConstants.IMAGE_PATH);
                                                 }
                                             });
                             }
                         });
        }
    }

}