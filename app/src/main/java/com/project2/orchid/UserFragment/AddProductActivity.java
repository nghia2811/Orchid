package com.project2.orchid.UserFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project2.orchid.Activity.ProductActivity;
import com.project2.orchid.Animation.LoadingDialog;
import com.project2.orchid.Object.Product;
import com.project2.orchid.R;

import java.util.Calendar;

public class AddProductActivity extends AppCompatActivity {
    ImageView btnSave, btnRefresh, btnBack;
    Button btnChon;
    ImageView imageAdd;
    EditText edtTensp, edtGiatien, edtNhasanxuat, edtThuonghieu, edtXuatxu, edtMota;
    int REQUEST_CODE_IMAGE = 1;
    BottomSheetDialog bottomDialog;
    DatabaseReference mData, delete;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    String tensp, danhmuc;
    Product product;
    FirebaseAuth mAuth;
    String id;
    LoadingDialog loadingDialog;

    Uri uri = null;
    int image = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        btnBack = findViewById(R.id.btn_add_back);
        btnSave = findViewById(R.id.btn_save);
        btnRefresh = findViewById(R.id.btn_refresh);
        imageAdd = findViewById(R.id.image_add);
        edtTensp = findViewById(R.id.edt_tensp);
        edtGiatien = findViewById(R.id.edt_giatien);
        btnChon = findViewById(R.id.edt_danhmuc);
        edtNhasanxuat = findViewById(R.id.edt_nhasanxuat);
        edtThuonghieu = findViewById(R.id.edt_thuonghieu);
        edtXuatxu = findViewById(R.id.edt_xuatxu);
        edtMota = findViewById(R.id.edt_mota);
        imageAdd.setBackgroundResource(R.drawable.noimage);

        getCurrentUser();

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
                if (edtTensp.getText().toString().equals("") || edtGiatien.getText().toString().equals("") ||
                        btnChon.getText().toString().equals("") || edtNhasanxuat.getText().toString().equals("") ||
                        edtThuonghieu.getText().toString().equals("") || edtXuatxu.getText().toString().equals("") ||
                        edtMota.getText().toString().equals("") || image == 0) {
                    Toast.makeText(AddProductActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    loadingDialog = new LoadingDialog(AddProductActivity.this);
                    loadingDialog.startLoadingDialog();
                    addProduct();
                }
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

    public void reset() {
        imageAdd.setBackgroundResource(R.drawable.noimage);
        edtTensp.setText(null);
        edtGiatien.setText(null);
        btnChon.setText("Chọn");
        edtNhasanxuat.setText(null);
        edtThuonghieu.setText(null);
        edtXuatxu.setText(null);
        edtMota.setText(null);
    }

    private void ShowMenu() {
        PopupMenu popupMenu = new PopupMenu(this, btnChon);
        popupMenu.getMenuInflater().inflate(R.menu.menu_danh_muc, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_dienthoai:
                        btnChon.setText("Điện thoại - Máy tính");
                        break;
                    case R.id.menu_quanao:
                        btnChon.setText("Quần áo - Thời trang");
                        break;
                    case R.id.menu_lamdep:
                        btnChon.setText("Sức khỏe - Làm đẹp");
                        break;
                    case R.id.menu_nhacua:
                        btnChon.setText("Nhà cửa - Đồ gia dụng");
                        break;
                    case R.id.menu_sach:
                        btnChon.setText("Sách - Văn phòng phẩm");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void addProduct() {
        mData = FirebaseDatabase.getInstance().getReference();
        tensp = String.valueOf(edtTensp.getText());
        danhmuc = String.valueOf(btnChon.getText());

        final StorageReference storageRef = storage.getReferenceFromUrl("gs://orchid-29b28.appspot.com");
        Calendar calendar = Calendar.getInstance();
        final StorageReference mountainsRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");

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

                    // tạo node trên Database
                    product = new Product(edtTensp.getText().toString(), String.valueOf(url), edtGiatien.getText().toString() + " đ",
                            btnChon.getText().toString(), edtNhasanxuat.getText().toString(), edtThuonghieu.getText().toString(),
                            edtXuatxu.getText().toString(), edtMota.getText().toString(), id);
                    mData.child("Product").child(edtTensp.getText().toString()).setValue(product);

                    loadingDialog.dismissDialog();
                    Toast.makeText(AddProductActivity.this, "Upload thành công", Toast.LENGTH_SHORT).show();
                    createBottomDialog();
                    bottomDialog.show();
                } else {
                    loadingDialog.dismissDialog();
                    Toast.makeText(AddProductActivity.this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle unsuccessful uploads
//                Toast.makeText(AddProductActivity.this, "Upload thất bại", Toast.LENGTH_SHORT).show();
//                loadingDialog.dismissDialog();
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
//                while (!urlTask.isSuccessful()) ;
//                Uri downloadUrl = urlTask.getResult();
//                Toast.makeText(AddProductActivity.this, "Upload thành công", Toast.LENGTH_SHORT).show();
//
//                // tạo node trên Database
//                Product product = new Product(edtTensp.getText().toString(), String.valueOf(downloadUrl), edtGiatien.getText().toString(),
//                        btnChon.getText().toString(), edtNhasanxuat.getText().toString(), edtThuonghieu.getText().toString(),
//                        edtXuatxu.getText().toString(), edtMota.getText().toString(), id);
//                mData.child("Product").child(edtTensp.getText().toString()).setValue(product);
//                loadingDialog.dismissDialog();
//            }
//        });

    }

    private void createBottomDialog() {
        if (bottomDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.bottom_dialog_add, null);
            LinearLayout linearLayout;
            TextView name, nhasanxuat, gia;
            Button themmoi, xoa;
            ImageView addImage;

            linearLayout = view.findViewById(R.id.layout_xemchitiet);
            name = view.findViewById(R.id.add_ten);
            nhasanxuat = view.findViewById(R.id.add_nhasx);
            gia = view.findViewById(R.id.add_gia);
            themmoi = view.findViewById(R.id.add_themmoi);
            xoa = view.findViewById(R.id.add_xoa);
            addImage = view.findViewById(R.id.add_image);

            name.setText(edtTensp.getText());
            nhasanxuat.setText(edtNhasanxuat.getText());
            gia.setText(edtGiatien.getText() + " đ");
            addImage.setImageURI(uri);
            ;

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddProductActivity.this, ProductActivity.class);

                    intent.putExtra("Ten", tensp);
                    // start the activity
                    startActivity(intent);
                }
            });

            themmoi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomDialog.dismiss();
                    reset();
                    edtTensp.requestFocus();
                }
            });

            xoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StorageReference photoRef = storage.getReferenceFromUrl(product.getHinhAnh());
                    delete = FirebaseDatabase.getInstance().getReference().child("Product").child(tensp);
                    delete.removeValue();

                    photoRef.delete();
                    bottomDialog.dismiss();
                    reset();
                    edtTensp.requestFocus();
                }
            });
            bottomDialog = new BottomSheetDialog(this);
            bottomDialog.setContentView(view);
        }
    }

    private void getCurrentUser() {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        id = currentUser.getUid();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            uri = data.getData();
            imageAdd.setImageURI(uri);
            image = 1;
        }
    }
}
