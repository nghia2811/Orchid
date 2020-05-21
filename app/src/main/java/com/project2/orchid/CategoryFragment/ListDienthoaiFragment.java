package com.project2.orchid.CategoryFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.project2.orchid.Activity.CategoryActivity;
import com.project2.orchid.R;

public class ListDienthoaiFragment extends Fragment {
    Button category;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_dienthoai, container, false);
        category = root.findViewById(R.id.btn_list_dienthoai);

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("DanhMuc", category.getText());
                startActivity(intent);
            }
        });

        return root;
    }
}