package com.example.billiardshop.viewmodel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billiardshop.R;
import com.example.billiardshop.adapter.CartAdapter;
import com.example.billiardshop.constant.Constant;
import com.example.billiardshop.constant.GlobalFuntion;
import com.example.billiardshop.database.CueDatabase;
import com.example.billiardshop.databinding.LayoutBottomSheetOrderBinding;
import com.example.billiardshop.listener.ICalculatePriceListener;
import com.example.billiardshop.model.Cue;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class CartViewModel {

    private Context mContext;
    public ObservableList<Cue> listCueInCart = new ObservableArrayList<>();
    public static String strTotalPrice;
    private static int mAmount;
    private static CartAdapter mCartAdapter;
    private DialogOrderViewModel mDialogOrderViewModel;

    public CartViewModel(Context mContext) {
        this.mContext = mContext;
        getListCueInCart();
    }

    public void getListCueInCart() {
        if (listCueInCart != null) {
            listCueInCart.clear();
        } else {
            listCueInCart = new ObservableArrayList<>();
        }
        List<Cue> list = CueDatabase.getInstance(mContext).cueDAO().getListCueCart();
        listCueInCart.addAll(list);
    }
    @BindingAdapter({"list_cart", "calculate_price"})
    public static void loadListCueInCart(RecyclerView recyclerView, ObservableList<Cue> list, TextView textView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);


        mCartAdapter = new CartAdapter(list, new ICalculatePriceListener() {
            @Override
            public void calculatePrice(String totalPrice, int amount) {
                textView.setText(totalPrice);
                strTotalPrice = totalPrice;
                mAmount = amount;
            }
        });
        strTotalPrice = getValueTotalPrice(recyclerView.getContext());
        textView.setText(strTotalPrice);
        recyclerView.setAdapter(mCartAdapter);
    }

    private static String getValueTotalPrice(Context context) {
        List<Cue> listCueCart = CueDatabase.getInstance(context).cueDAO().getListCueCart();
        if (listCueCart == null || listCueCart.isEmpty()) {
            mAmount = 0;
            return 0 + Constant.CURRENCY;
        }

        int totalPrice = 0;
        for (Cue cue : listCueCart) {
            totalPrice = totalPrice + cue.getTotalPrice();
        }

        mAmount = totalPrice;
        return totalPrice + Constant.CURRENCY;
    }

    public void onClickOrderCart() {
        if (mContext == null || listCueInCart == null || listCueInCart.isEmpty()) {
            return;
        }

        LayoutBottomSheetOrderBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.layout_bottom_sheet_order, null, false);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
        bottomSheetDialog.setContentView(binding.getRoot());
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

        mDialogOrderViewModel = new DialogOrderViewModel(mContext, bottomSheetDialog, listCueInCart,
                strTotalPrice, mAmount, () -> {
            GlobalFuntion.showToastMessage(mContext, mContext.getString(R.string.msg_order_success));
            GlobalFuntion.hideSoftKeyboard((Activity) mContext);
            bottomSheetDialog.dismiss();

            CueDatabase.getInstance(mContext).cueDAO().deleteAllCue();
            clearCart();
        });
        binding.setDialogOrderViewModel(mDialogOrderViewModel);

        bottomSheetDialog.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void clearCart() {
        if (listCueInCart != null) {
            listCueInCart.clear();
        }
        mCartAdapter.notifyDataSetChanged();
    }

    public void release() {
        mContext = null;
        if (mDialogOrderViewModel != null) {
            mDialogOrderViewModel.release();
        }
    }

}
