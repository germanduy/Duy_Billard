package com.example.billiardshop.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.example.billiardshop.R;
import com.example.billiardshop.constant.Constant;
import com.example.billiardshop.constant.GlobalFuntion;
import com.example.billiardshop.listener.ISendOrderSuccessListener;
import com.example.billiardshop.model.Cue;
import com.example.billiardshop.model.Order;
import com.example.billiardshop.untils.StringUtil;
import com.example.billiardshop.untils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogOrderViewModel {
    private Context mContext;
    private BottomSheetDialog mBottomSheetDialog;
    private final ObservableList<Cue> listCueInCart;
    public String strTotalPrice;
    public ObservableField<String> strName = new ObservableField<>();
    public ObservableField<String> strAddress = new ObservableField<>();
    public ObservableField<String> strPhone = new ObservableField<>();
    private final int mAmount;
    private final ISendOrderSuccessListener iSendOrderSuccessListener;

    public DialogOrderViewModel(Context context, BottomSheetDialog mBottomSheetDialog,
                                ObservableList<Cue> list, String total,
                                int amount, ISendOrderSuccessListener listener) {
        this.mContext = context;
        this.mBottomSheetDialog = mBottomSheetDialog;
        this.listCueInCart = list;
        this.strTotalPrice = total;
        this.mAmount = amount;
        this.iSendOrderSuccessListener = listener;
    }

    public void release() {
        mContext = null;
        mBottomSheetDialog = null;
    }
    
    public String getStringListCueOrder() {
        if (listCueInCart == null || listCueInCart.isEmpty()) {
            return "";
        }
        String result = "";
        for (Cue cue : listCueInCart) {
            if (StringUtil.isEmpty(result)) {
                result = "- " + cue.getName() + " (" + cue.getRealPrice() + Constant.CURRENCY + ") "
                        + "- " + mContext.getString(R.string.quantity) + " " + cue.getCount();
            } else {
                result = result + "\n" + "- " + cue.getName() + " (" + cue.getRealPrice()
                        + Constant.CURRENCY + ") "
                        + "- " + mContext.getString(R.string.quantity) + " " + cue.getCount();
            }
        }
        return result;
    }

    public void onClickCancel() {
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog.dismiss();
        }
    }

    public void onClickSendOrder() {
        String name = strName.get();
        String phone = strPhone.get();
        String address = strAddress.get();

        if (StringUtil.isEmpty(name) || StringUtil.isEmpty(phone) || StringUtil.isEmpty(address)) {
            GlobalFuntion.showToastMessage(mContext, mContext.getString(R.string.message_enter_infor_order));
        } else {
            long id = System.currentTimeMillis();
            Order order = new Order(id, name, phone, address,
                    mAmount, getStringListCueOrder(), Constant.TYPE_PAYMENT_CASH);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("booking");

            myRef.child(Utils.getDeviceId(mContext))
                    .child(String.valueOf(id))
                    .setValue(order, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error1, @NonNull DatabaseReference ref1) {
                            iSendOrderSuccessListener.sendOrderSuccess();
                        }
                    });

        }
    }

}
