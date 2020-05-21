package com.project2.orchid.NotificationFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project2.orchid.Animation.SwipeToDeleteCallback;
import com.project2.orchid.MainActivity;
import com.project2.orchid.Object.Notification;
import com.project2.orchid.R;
import com.project2.orchid.RecyclerViewAdapter.RecyclerViewAdapterNotification;

import java.util.ArrayList;

import static android.view.View.GONE;

public class NotificationFragment extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference ref, delete;
    FirebaseAuth mAuth;
    ArrayList<Notification> lstNotifications;
    ProgressBar loadingView;
    LinearLayout linearLayout;
    Button tieptuc;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notification, container, false);

        recyclerView = root.findViewById(R.id.recyclerView_notification);
        loadingView = root.findViewById(R.id.loading_view2);
        linearLayout = root.findViewById(R.id.background_notifiaction);
        tieptuc = root.findViewById(R.id.notification_tieptuc);
        loadData();

        tieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    private void loadData() {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        ref = FirebaseDatabase.getInstance().getReference().child("Notifications").child(currentUser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loadingView.setVisibility(GONE);
                if (dataSnapshot.exists()) linearLayout.setVisibility(GONE);
                lstNotifications = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Notification p = dataSnapshot1.getValue(Notification.class);
                    lstNotifications.add(p);
                }

                final RecyclerViewAdapterNotification myAdapter = new RecyclerViewAdapterNotification(getContext(), lstNotifications);
                recyclerView.setAdapter(myAdapter);

                SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getActivity()) {
                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                        final int position = viewHolder.getAdapterPosition();
                        final Notification item = myAdapter.getData().get(position);

                        myAdapter.removeItem(position);

                        delete = FirebaseDatabase.getInstance().getReference().child("Notifications").child(currentUser.getUid()).child(item.getTitle());
                        delete.removeValue();
                    }
                };
                ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
                itemTouchhelper.attachToRecyclerView(recyclerView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
