package com.project2.orchid;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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
import com.project2.orchid.object.Comment;
import com.project2.orchid.object.Product;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ProductActivity extends AppCompatActivity {
    private TextView tensp, gia, danhmuc, nhasx, thuonghieu, xuatxu, mota, baohanh;
    private ImageView img;
    ImageView back, like, gioHang;
    Button chonmua, vietnhanxet;
    DatabaseReference ref, mData;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseAuth mAuth;
    BottomSheetDialog bottomDialog;
    String nhacc, noisanxuat, name, nguoiBan, id, tenkhachhang;
    ArrayList<Comment> lstComment;
    RecyclerView recyclerView;

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
        baohanh = findViewById(R.id.text_xemthembaohanh);
        recyclerView = findViewById(R.id.recyclerView_comment);

        chonmua = findViewById(R.id.btn_chonmua);
        img = findViewById(R.id.category_thumbnail);
        back = findViewById(R.id.btn_product_back);
        like = findViewById(R.id.btn_product_like);
        gioHang = findViewById(R.id.btn_giohang);
        vietnhanxet = findViewById(R.id.vietnhanxet);

        // Recieve data
        Intent intent = getIntent();
        name = intent.getExtras().getString("Ten");
        loadData();
        loadComment();
        getCurrentUser();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        baohanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createBottomDialog();
                bottomDialog.show();
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nguoiBan.equals(id))
                    Toast.makeText(ProductActivity.this, "Đây là sản phẩm bạn đăng bán", Toast.LENGTH_SHORT).show();
                else addLike();
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
                if (nguoiBan.equals(id))
                    Toast.makeText(ProductActivity.this, "Đây là sản phẩm bạn đăng bán", Toast.LENGTH_SHORT).show();
                else addToCart();
            }
        });

        vietnhanxet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createBottomDialogComment();
                bottomDialog.show();
            }
        });
    }

    private void createBottomDialog() {
        if (bottomDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.bottom_dialog, null);
            TextView nhacungcap, noibaohanh;
            nhacungcap = view.findViewById(R.id.baohanh_ncc);
            noibaohanh = view.findViewById(R.id.baohanh_noibaohanh);

            nhacungcap.setText(nhacc);
            noibaohanh.setText(noisanxuat);

            bottomDialog = new BottomSheetDialog(this);
            bottomDialog.setContentView(view);
        }
    }

    private void createBottomDialogComment() {
        if (bottomDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.bottom_comment, null);
            final EditText edtComment;
            Button post;

            edtComment = view.findViewById(R.id.edt_comment);
            post = view.findViewById(R.id.btn_post);

            post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edtComment.getText().equals("")) {
                        Toast.makeText(ProductActivity.this, "Vui lòng nhập nhận xét", Toast.LENGTH_SHORT).show();
                    } else {
                        mData = FirebaseDatabase.getInstance().getReference();
                        Date date = Calendar.getInstance().getTime();
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                        Comment comment = new Comment(edtComment.getText().toString(), id, tenkhachhang);
                        mData.child("Product").child(name).child("Comment").child(dateFormat.format(date)).setValue(comment);
                        edtComment.setText(null);
                        if (!nguoiBan.equals(id)) {
                            mData.child("User").child(nguoiBan).child("Notifications").child(dateFormat.format(date)).setValue(comment);
                        }
                        Toast.makeText(ProductActivity.this, "Đã gửi nhận xét", Toast.LENGTH_SHORT).show();
                        bottomDialog.dismiss();
                    }
                }
            });
            bottomDialog = new BottomSheetDialog(this);
            bottomDialog.setContentView(view);
        }
    }

    private void loadData() {
        ref = FirebaseDatabase.getInstance().getReference().child("Product").child(name);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                tensp.setText(dataSnapshot.child("ten").getValue().toString());
                gia.setText(dataSnapshot.child("giaTien").getValue().toString());
                danhmuc.setText(dataSnapshot.child("danhMuc").getValue().toString());
                nhasx.setText(dataSnapshot.child("nhaSanXuat").getValue().toString());
                thuonghieu.setText(dataSnapshot.child("thuơngHieu").getValue().toString());
                xuatxu.setText(dataSnapshot.child("xuatXu").getValue().toString());
                mota.setText(dataSnapshot.child("mota").getValue().toString());
                Glide.with(ProductActivity.this).load(dataSnapshot.child("hinhAnh").getValue().toString())
                        .placeholder(R.drawable.noimage).into(img);

                nguoiBan = dataSnapshot.child("nguoiBan").getValue().toString();
                nhacc = dataSnapshot.child("thuơngHieu").getValue().toString();
                noisanxuat = dataSnapshot.child("nhaSanXuat").getValue().toString();

                final String tempUrl = dataSnapshot.child("hinhAnh").getValue().toString();
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(ProductActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                        PhotoView photoView = new PhotoView(ProductActivity.this);
                        Glide.with(ProductActivity.this).load(tempUrl).into(photoView);
                        photoView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        dialog.addContentView(photoView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProductActivity.this, "load thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadComment() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        ref = FirebaseDatabase.getInstance().getReference().child("Product").child(name).child("Comment");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstComment = new ArrayList<Comment>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Comment p = dataSnapshot1.getValue(Comment.class);
                    lstComment.add(p);
                }
                final RecyclerViewAdapterComment myAdapter = new RecyclerViewAdapterComment(ProductActivity.this, lstComment);
                recyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProductActivity.this, "Không thể load comment", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addLike() {
        final LoadingDialog loadingDialog = new LoadingDialog(ProductActivity.this);
        loadingDialog.startLoadingDialog();
        mData = FirebaseDatabase.getInstance().getReference();
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://orchid-29b28.appspot.com");
        Calendar calendar = Calendar.getInstance();
        final StorageReference mountainsRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");

        // Get the data from an ImageView as bytes
        img.setDrawingCacheEnabled(true);
        img.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);

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
                    Product product = new Product(tensp.getText().toString(), String.valueOf(url), gia.getText().toString(),
                            danhmuc.getText().toString(), nhasx.getText().toString(), thuonghieu.getText().toString(),
                            xuatxu.getText().toString(), mota.getText().toString(), nguoiBan);
                    mData.child("Favourite").child(id).child(tensp.getText().toString()).setValue(product);
                    loadingDialog.dismissDialog();
                    Toast.makeText(ProductActivity.this, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                } else {
                    loadingDialog.dismissDialog();
                    Toast.makeText(ProductActivity.this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addToCart() {
        final LoadingDialog loadingDialog = new LoadingDialog(ProductActivity.this);
        loadingDialog.startLoadingDialog();

        mData = FirebaseDatabase.getInstance().getReference();

        final StorageReference storageRef = storage.getReferenceFromUrl("gs://orchid-29b28.appspot.com");
        Calendar calendar = Calendar.getInstance();
        final StorageReference mountainsRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");

        // Get the data from an ImageView as bytes
        img.setDrawingCacheEnabled(true);
        img.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);

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
                    Product product = new Product(tensp.getText().toString(), String.valueOf(url), gia.getText().toString(),
                            danhmuc.getText().toString(), nhasx.getText().toString(), thuonghieu.getText().toString(),
                            xuatxu.getText().toString(), mota.getText().toString(), nguoiBan);
                    mData.child("Cart").child(id).child(tensp.getText().toString()).setValue(product);
                    loadingDialog.dismissDialog();
                    Toast.makeText(ProductActivity.this, "Đã thêm vào Giỏ hàng", Toast.LENGTH_SHORT).show();
                } else {
                    loadingDialog.dismissDialog();
                    Toast.makeText(ProductActivity.this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getCurrentUser() {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        id = currentUser.getUid();

        ref = FirebaseDatabase.getInstance().getReference().child("User").child(id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tenkhachhang = dataSnapshot.child("ten").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProductActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}