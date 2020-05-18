package com.project2.orchid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project2.orchid.object.User;

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
    LoadingDialog loadingDialog;

    Uri uri;

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
        loadingDialog = new LoadingDialog(RegisterActivity.this);

        mAuth = FirebaseAuth.getInstance();

        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, REQUEST_CODE_IMAGE);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getText().toString().equals("") || password.getText().toString().equals("") || ten.getText().toString().equals("") || diachi.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng thêm đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    DangKi();
                }
            }
        });
    }

    private void DangKi() {
        loadingDialog.startLoadingDialog();
        String email = user.getText().toString();
        String pass = password.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            final FirebaseUser user = mAuth.getCurrentUser();

                            mData = FirebaseDatabase.getInstance().getReference();
                            final StorageReference storageRef = storage.getReferenceFromUrl("gs://orchid-29b28.appspot.com");

                            final Date date = Calendar.getInstance().getTime();
                            final StorageReference mountainsRef = storageRef.child("image" + date.getTime() + ".png");

                            UploadTask uploadTask = mountainsRef.putFile(uri);

                            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {

                                    }
                                    return mountainsRef.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        String url = task.getResult().toString();
                                        User user1 = new User(user.getUid(), ten.getText().toString(), String.valueOf(url),
                                                user.getEmail(), diachi.getText().toString(), date);
                                        mData.child("User").child(user.getUid()).setValue(user1);
                                        loadingDialog.dismissDialog();
                                        Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        loadingDialog.dismissDialog();
                                        Toast.makeText(RegisterActivity.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            uri = data.getData();
            imageAdd.setImageURI(uri);
        }
    }
}
