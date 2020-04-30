package com.project2.orchid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

public class ProductActivity extends AppCompatActivity {
    private TextView tensp, gia, danhmuc, nhasx, thuonghieu, xuatxu, mota;
    private ImageView img;
    ImageButton back, like, gioHang;
    Button chonmua;
    DatabaseReference ref, mData, giohang;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        tensp = (TextView) findViewById(R.id.txt_tensp);
        gia = (TextView) findViewById(R.id.txt_giasp);
        danhmuc = (TextView) findViewById(R.id.txt_danhmuc);
        nhasx = (TextView) findViewById(R.id.txt_nhasx);
        thuonghieu = (TextView) findViewById(R.id.txt_thuonghieu);
        xuatxu = (TextView) findViewById(R.id.txt_xuatxu);
        mota = (TextView) findViewById(R.id.txt_mota);

        chonmua = (Button) findViewById(R.id.btn_chonmua);
        img = (ImageView) findViewById(R.id.category_thumbnail);
        back = (ImageButton) findViewById(R.id.btn_product_back);
        like = (ImageButton) findViewById(R.id.btn_product_like);
        gioHang  = (ImageButton) findViewById(R.id.btn_giohang);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Recieve data
        Intent intent = getIntent();
        String name = intent.getExtras().getString("Ten");
        String danhMuc = intent.getExtras().getString("DanhMuc");

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        ref = FirebaseDatabase.getInstance().getReference().child("Product").child(danhMuc).child(name);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Setting values
                tensp.setText(dataSnapshot.child("ten").getValue().toString());
                gia.setText(dataSnapshot.child("giaTien").getValue().toString());
                danhmuc.setText(dataSnapshot.child("danhMuc").getValue().toString());
                nhasx.setText(dataSnapshot.child("nhaSanXuat").getValue().toString());
                thuonghieu.setText(dataSnapshot.child("thuơngHieu").getValue().toString());
                xuatxu.setText(dataSnapshot.child("xuatXu").getValue().toString());
                mota.setText(dataSnapshot.child("mota").getValue().toString());

                LoadImage loadImage = new LoadImage(img);
                loadImage.execute(dataSnapshot.child("hinhAnh").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProductActivity.this, "load thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData = FirebaseDatabase.getInstance().getReference();
                final StorageReference storageRef = storage.getReferenceFromUrl("gs://orchid-29b28.appspot.com");
                Calendar calendar = Calendar.getInstance();
                StorageReference mountainsRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");

                // Get the data from an ImageView as bytes
                img.setDrawingCacheEnabled(true);
                img.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
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
                        Product product = new Product(tensp.getText().toString(), String.valueOf(downloadUrl), gia.getText().toString(),
                                danhmuc.getText().toString(), nhasx.getText().toString(), thuonghieu.getText().toString(),
                                xuatxu.getText().toString(), mota.getText().toString());
                        mData.child("User").child(currentUser.getUid()).child("YeuThich").child(tensp.getText().toString()).setValue(product, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                if (databaseError == null) {
                                    Toast.makeText(ProductActivity.this, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ProductActivity.this, "upload thất bại", Toast.LENGTH_SHORT).show();
                                }
                                ;
                            }
                        });
                    }
                });
            }
        });

        gioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, GioHangActivity.class);
                startActivity(intent);
            }
        });

        chonmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                giohang = FirebaseDatabase.getInstance().getReference();
                final StorageReference storageRef = storage.getReferenceFromUrl("gs://orchid-29b28.appspot.com");
                Calendar calendar = Calendar.getInstance();
                StorageReference mountainsRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");

                // Get the data from an ImageView as bytes
                img.setDrawingCacheEnabled(true);
                img.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
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
                        Product product = new Product(tensp.getText().toString(), String.valueOf(downloadUrl), gia.getText().toString(),
                                danhmuc.getText().toString(), nhasx.getText().toString(), thuonghieu.getText().toString(),
                                xuatxu.getText().toString(), mota.getText().toString());
                        giohang.child("User").child(currentUser.getUid()).child("GioHang").child(tensp.getText().toString()).setValue(product, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                if (databaseError == null) {
                                    Toast.makeText(ProductActivity.this, "Đã thêm vào danh sách giỏ hàng", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ProductActivity.this, "upload thất bại", Toast.LENGTH_SHORT).show();
                                }
                                ;
                            }
                        });
                    }
                });
            }
        });
    }


    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView img;
        public LoadImage(ImageView ivResult) {
            this.img = ivResult;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlLink = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new java.net.URL(urlLink).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            img.setImageBitmap(bitmap);
        }
    }

}
