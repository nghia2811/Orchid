package com.project2.orchid.RecyclerViewAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.project2.orchid.CategoryFragment.ListDienthoaiFragment;
import com.project2.orchid.CategoryFragment.ListFragment;
import com.project2.orchid.CategoryFragment.ListGiadungFragment;
import com.project2.orchid.CategoryFragment.ListHomeFragment;
import com.project2.orchid.CategoryFragment.ListLamdepFragment;
import com.project2.orchid.CategoryFragment.ListSachFragment;
import com.project2.orchid.CategoryFragment.ListThoitrangFragment;
import com.project2.orchid.Object.Category2;
import com.project2.orchid.R;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapterList extends RecyclerView.Adapter<RecyclerViewAdapterList.MyViewHolder> {

    private ListFragment mContext ;
    private List<Category2> mData ;
    List<LinearLayout> cardViewList = new ArrayList<>();

    public RecyclerViewAdapterList(ListFragment mContext, List<Category2> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        view = LayoutInflater.from(mContext.getActivity()).inflate(R.layout.button_list,parent,false);
        final MyViewHolder vHolder = new MyViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.list_title.setText(mData.get(position).getTitle());
        holder.img_list.setImageResource(mData.get(position).getThumbnail());
        cardViewList.add(holder.cardView);
        cardViewList.get(0).setBackgroundResource(R.color.colorWhite);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                for(LinearLayout cardView : cardViewList){
                    cardView.setBackgroundResource(R.color.colorOrange);
                }
                holder.cardView.setBackgroundResource(R.color.colorWhite);
                if (position == 0) {
                    Fragment newFragment = new ListHomeFragment();
                    FragmentTransaction transaction = mContext.getFragmentManager().beginTransaction();

                    transaction.replace(R.id.list_host_fragment, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else if (position == 1) {
                        Fragment newFragment = new ListThoitrangFragment();
                        FragmentTransaction transaction = mContext.getFragmentManager().beginTransaction();

                        transaction.replace(R.id.list_host_fragment, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                }
                else if (position == 2){
                        Fragment newFragment = new ListDienthoaiFragment();
                        FragmentTransaction transaction = mContext.getFragmentManager().beginTransaction();

                        transaction.replace(R.id.list_host_fragment, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                else if (position == 3){
                        Fragment newFragment = new ListGiadungFragment();
                        FragmentTransaction transaction = mContext.getFragmentManager().beginTransaction();

                        transaction.replace(R.id.list_host_fragment, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                else if (position == 4){
                        Fragment newFragment = new ListLamdepFragment();
                        FragmentTransaction transaction = mContext.getFragmentManager().beginTransaction();

                        transaction.replace(R.id.list_host_fragment, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                else if (position == 5){
                        Fragment newFragment = new ListSachFragment();
                        FragmentTransaction transaction = mContext.getFragmentManager().beginTransaction();

                        transaction.replace(R.id.list_host_fragment, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView list_title;
        ImageView img_list;
        LinearLayout cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);
            list_title = (TextView) itemView.findViewById(R.id.list_title_id) ;
            img_list = (ImageView) itemView.findViewById(R.id.list_img_id);
            cardView = (LinearLayout) itemView.findViewById(R.id.cardview_list);
        }
    }


}
