package com.project2.orchid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button register;
    EditText user, password, diachi, ten;
    DatabaseReference mData;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    int REQUEST_CODE_IMAGE = 1;
    ImageView imageAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user = findViewById(R.id.register_user);
        password = findViewById(R.id.register_pass);
        ten = findViewById(R.id.register_ten);
        register = findViewById(R.id.btn_register);
        diachi = findViewById(R.id.register_diachi);
        imageAdd = findViewById(R.id.cardview_avatar);

        mAuth = FirebaseAuth.getInstance();

        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, REQUEST_CODE_IMAGE);//one can be replaced with any action code
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getText()==null || password.getText()==null || ten.getText()==null || diachi.getText()==null){
                    Toast.makeText(RegisterActivity.this, "Vui lòng thêm đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    DangKi();
                }
            }
        });
    }


    private void DangKi() {
        String email = user.getText().toString();
        String pass = password.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                            final FirebaseUser user = mAuth.getCurrentUser();

                            mData = FirebaseDatabase.getInstance().getReference();
                            final StorageReference storageRef = storage.getReferenceFromUrl("gs://orchid-29b28.appspot.com");

                            final Date date = Calendar.getInstance().getTime();
                            StorageReference mountainsRef = storageRef.child("image" + date.getTime() + ".png");

                            // Get the data from an ImageView as bytes
                            imageAdd.setDrawingCacheEnabled(true);
                            imageAdd.buildDrawingCache();
                            Bitmap bitmap = ((BitmapDrawable) imageAdd.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                            byte[] data = baos.toByteArray();

                            UploadTask uploadTask = mountainsRef.putBytes(data);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!urlTask.isSuccessful()) ;
                                    Uri downloadUrl = urlTask.getResult();

                                    // tạo node trên Database
                                    User user1 = new User(user.getUid(), ten.getText().toString(), String.valueOf(downloadUrl),
                                            user.getEmail(), diachi.getText().toString(), date);
                                    mData.child("User").child(user.getUid()).setValue(user1, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                            if (databaseError == null) {
                                            } else {
                                            }
                                        }
                                    });
                                }
                            });
                        } else {
                            Toast.makeText(RegisterActivity.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    imageAdd.setImageURI(selectedImage);
                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    imageAdd.setImageURI(selectedImage);
                }
                break;
        }
    }
}
