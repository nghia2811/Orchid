package com.project2.orchid.NotificationFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project2.orchid.Activity.GioHangActivity;
import com.project2.orchid.Object.Category2;
import com.project2.orchid.Object.Notification;
import com.project2.orchid.R;
import com.project2.orchid.RecyclerViewAdapter.RecyclerViewAdapterThongbao;

import java.util.ArrayList;
import java.util.List;

public class ThongBaoFragment extends Fragment {
    List<Category2> lstBtn;
    public ArrayList<Notification> lstNotifications;
    ImageView gioHang;
    Query ref;
    FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_thongbao, container, false);

        gioHang = root.findViewById(R.id.btn_thongbao_giohang);
        gioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GioHangActivity.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        ref = FirebaseDatabase.getInstance().getReference().child("Notifications").child(currentUser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstNotifications = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Notification p = dataSnapshot1.getValue(Notification.class);
                    lstNotifications.add(p);
                }
                if (lstNotifications != null) {
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.thongbao_host_fragment, new NotificationFragment()).commit();
                } else {
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.thongbao_host_fragment, new Notification1Fragment()).commit();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        lstBtn = new ArrayList<>();
        lstBtn.add(new Category2(null, R.drawable.ic_home_gray_24dp));
        lstBtn.add(new Category2(null,R.drawable.ic_insert_emoticon_gray_24dp));
        lstBtn.add(new Category2(null,R.drawable.ic_whatshot_gray_24dp));
        lstBtn.add(new Category2(null,R.drawable.ic_sms_gray_24dp));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView_thongbao);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapterThongbao myAdapter = new RecyclerViewAdapterThongbao(this,lstBtn);
        recyclerView.setAdapter(myAdapter);

        return root;
    }
}