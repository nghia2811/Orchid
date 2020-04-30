package com.project2.orchid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class AddProductActivity extends AppCompatActivity {
    ImageButton btnSave, btnRefresh, btnBack;
    Button btnChon;
    ImageView imageAdd;
    EditText edtTensp, edtGiatien, edtNhasanxuat, edtThuonghieu, edtXuatxu, edtMota;
    int REQUEST_CODE_IMAGE = 1;
    DatabaseReference mData;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        btnBack = (ImageButton) findViewById(R.id.btn_add_back);
        btnSave = (ImageButton) findViewById(R.id.btn_save);
        btnRefresh = (ImageButton) findViewById(R.id.btn_refresh);
        imageAdd = (ImageView) findViewById(R.id.image_add);
        edtTensp = (EditText) findViewById(R.id.edt_tensp);
        edtGiatien = (EditText) findViewById(R.id.edt_giatien);
        btnChon = (Button) findViewById(R.id.edt_danhmuc);
        edtNhasanxuat = (EditText) findViewById(R.id.edt_nhasanxuat);
        edtThuonghieu = (EditText) findViewById(R.id.edt_thuonghieu);
        edtXuatxu = (EditText) findViewById(R.id.edt_xuatxu);
        edtMota = (EditText) findViewById(R.id.edt_mota);
        imageAdd.setBackgroundResource(R.drawable.noimage);

        mData = FirebaseDatabase.getInstance().getReference();
        final StorageReference storageRef = storage.getReferenceFromUrl("gs://orchid-29b28.appspot.com");

        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, REQUEST_CODE_IMAGE);//one can be replaced with any action code
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                StorageReference mountainsRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");

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
                        Toast.makeText(AddProductActivity.this, "Upload thất bại", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful()) ;
                        Uri downloadUrl = urlTask.getResult();
                        Toast.makeText(AddProductActivity.this, "Upload thành công", Toast.LENGTH_SHORT).show();

                        // tạo node trên Database
                        Product product = new Product(edtTensp.getText().toString(), String.valueOf(downloadUrl), edtGiatien.getText().toString(),
                                btnChon.getText().toString(), edtNhasanxuat.getText().toString(), edtThuonghieu.getText().toString(),
                                edtXuatxu.getText().toString(), edtMota.getText().toString());
                        switch (btnChon.getText().toString()) {
                            case "Điện thoại - Máy tính":
                                mData.child("Product").child("Điện thoại - Máy tính").child(edtTensp.getText().toString()).setValue(product, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                        if (databaseError == null) {
                                            Toast.makeText(AddProductActivity.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(AddProductActivity.this, "Lưu thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                break;
                            case "Quần áo - Thời trang":
                                mData.child("Product").child("Quần áo - Thời trang").child(edtTensp.getText().toString()).setValue(product, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                        if (databaseError == null) {
                                            Toast.makeText(AddProductActivity.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(AddProductActivity.this, "Lưu thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                break;
                            case "Sức khỏe - Làm đẹp":
                                mData.child("Product").child("Sức khỏe - Làm đẹp").child(edtTensp.getText().toString()).setValue(product, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                        if (databaseError == null) {
                                            Toast.makeText(AddProductActivity.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(AddProductActivity.this, "Lưu thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                break;
                            case "Nhà cửa - Đồ gia dụng":
                                mData.child("Product").child("Nhà cửa - Đồ gia dụng").child(edtTensp.getText().toString()).setValue(product, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                        if (databaseError == null) {
                                            Toast.makeText(AddProductActivity.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(AddProductActivity.this, "Lưu thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                break;
                            case "Sách - Văn phòng phẩm":
                                mData.child("Product").child("Sách - Văn phòng phẩm").child(edtTensp.getText().toString()).setValue(product, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                        if (databaseError == null) {
                                            Toast.makeText(AddProductActivity.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(AddProductActivity.this, "Lưu thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                break;
                        }
                    }
                });
            }
        });

        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenu();
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void reset(){
        imageAdd.setBackgroundResource(R.drawable.noimage);
        edtTensp.setText(null);
        edtGiatien.setText(null);
        btnChon.setText("Chọn");
        edtNhasanxuat.setText(null);
        edtThuonghieu.setText(null);
        edtXuatxu.setText(null);
        edtMota.setText(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(requestCode == REQUEST_CODE_IMAGE && resultCode ==RESULT_OK && data != null){
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            imageAdd.setImageBitmap(bitmap);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
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

    private void ShowMenu(){
        PopupMenu popupMenu = new PopupMenu(this, btnChon);
        popupMenu.getMenuInflater().inflate(R.menu.menu_danh_muc, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_dienthoai: btnChon.setText("Điện thoại - Máy tính");
                        break;
                    case R.id.menu_quanao: btnChon.setText("Quần áo - Thời trang");
                        break;
                    case R.id.menu_lamdep: btnChon.setText("Sức khỏe - Làm đẹp");
                        break;
                    case R.id.menu_nhacua: btnChon.setText("Nhà cửa - Đồ gia dụng");
                        break;
                    case R.id.menu_sach: btnChon.setText("Sách - Văn phòng phẩm");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }
}
