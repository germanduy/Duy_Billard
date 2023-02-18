package com.example.billiardshop.viewmodel;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;

import com.example.billiardshop.constant.Constant;
import com.example.billiardshop.database.CueDatabase;
import com.example.billiardshop.listener.IAddToCartSuccessListener;
import com.example.billiardshop.model.Cue;
import com.example.billiardshop.untils.GlideUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class DialogCartViewModel {

    private Activity mActivity;
    private BottomSheetDialog mBottomSheetDialog;
    public Cue mCue;
    public ObservableField<String> strTotalPrice = new ObservableField<>();
    private final IAddToCartSuccessListener iAddToCartSuccessListener;

    public DialogCartViewModel(Activity mActivity, BottomSheetDialog mBottomSheetDialog,
                               Cue mCue,
                               IAddToCartSuccessListener iAddToCartSuccessListener) {
        this.mActivity = mActivity;
        this.mBottomSheetDialog = mBottomSheetDialog;
        this.mCue = mCue;
        this.iAddToCartSuccessListener = iAddToCartSuccessListener;

        initData();
    }



    private void initData() {
        int totalPrice = mCue.getRealPrice();
        strTotalPrice.set(totalPrice + Constant.CURRENCY);

        mCue.setCount(1);
        mCue.setTotalPrice(totalPrice);
    }

    public void onClickSubtractCount(TextView tvCount) {
        int count = Integer.parseInt(tvCount.getText().toString());
        if (count <= 1) {
            return;
        }
        int newCount = Integer.parseInt(tvCount.getText().toString()) - 1;
        tvCount.setText(String.valueOf(newCount));

        int totalPrice1 = mCue.getRealPrice() * newCount;
        String strTotalPrice1 = totalPrice1 + Constant.CURRENCY;
        strTotalPrice.set(strTotalPrice1);

        mCue.setCount(newCount);
        mCue.setTotalPrice(totalPrice1);
    }

    public void onClickAddCount(TextView tvCount) {
        int newCount = Integer.parseInt(tvCount.getText().toString()) + 1;
        tvCount.setText(String.valueOf(newCount));

        int totalPrice2 = mCue.getRealPrice() * newCount;
        String strTotalPrice2 = totalPrice2 + Constant.CURRENCY;
        strTotalPrice.set(strTotalPrice2);

        mCue.setCount(newCount);
        mCue.setTotalPrice(totalPrice2);
    }

    public void onClickCancel() {
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog.dismiss();
        }
    }

    public void onClickAddToCart() {
        CueDatabase.getInstance(mActivity).cueDAO().insertCue(mCue);
        iAddToCartSuccessListener.addToCartSuccess();
    }

    @BindingAdapter({"url_image"})
    public static void loadImageFromUrl(ImageView imageView, String url) {
        GlideUtils.loadUrl(url, imageView);
    }

    public void release() {
        mActivity = null;
        mBottomSheetDialog = null;
    }
}
