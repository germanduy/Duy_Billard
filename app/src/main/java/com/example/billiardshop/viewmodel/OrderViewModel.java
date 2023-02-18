package com.example.billiardshop.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billiardshop.R;
import com.example.billiardshop.adapter.OrderAdapter;
import com.example.billiardshop.constant.GlobalFuntion;
import com.example.billiardshop.model.Order;
import com.example.billiardshop.untils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderViewModel {

    private Context mContext;
    public ObservableList<Order> listOrder = new ObservableArrayList<>();

    public OrderViewModel(Context mContext) {
        this.mContext = mContext;
        getListOrders();
    }

    private void getListOrders() {
        if (mContext == null) {
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("booking");

        myRef.child(Utils.getDeviceId(mContext))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (listOrder != null) {
                            listOrder.clear();
                        } else {
                            listOrder = new ObservableArrayList<>();
                        }

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Order order = dataSnapshot.getValue(Order.class);
                            listOrder.add(0, order);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        listOrder = null;
                    }
                });
    }

    @BindingAdapter({"list_data"})
    public static void loadListOrder(RecyclerView recyclerView, ObservableList<Order> list) {
        if (list == null) {
            GlobalFuntion.showToastMessage(recyclerView.getContext(),
                    recyclerView.getContext().getString(R.string.msg_get_date_error));
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        OrderAdapter orderAdapter = new OrderAdapter(list);
        recyclerView.setAdapter(orderAdapter);
    }

    public void release() {
        mContext = null;
    }
}
