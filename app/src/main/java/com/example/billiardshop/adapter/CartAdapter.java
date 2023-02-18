package com.example.billiardshop.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billiardshop.R;
import com.example.billiardshop.constant.Constant;
import com.example.billiardshop.database.CueDatabase;
import com.example.billiardshop.databinding.ItemCartBinding;
import com.example.billiardshop.listener.ICalculatePriceListener;
import com.example.billiardshop.listener.IClickItemCartListener;
import com.example.billiardshop.model.Cue;

import java.util.List;

public class CartAdapter  extends RecyclerView.Adapter<CartAdapter.CartViewHolder> implements IClickItemCartListener {
    private final List<Cue> mListCue;
    private final ICalculatePriceListener iCalculatePriceListener;

    public CartAdapter(List<Cue> mListCue, ICalculatePriceListener listener) {
        this.mListCue = mListCue;
        this.iCalculatePriceListener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartBinding itemCartBinding = ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CartViewHolder(itemCartBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cue cue = mListCue.get(position);
        if (cue == null) {
            return;
        }
        cue.setAdapterPosition(holder.getAdapterPosition());
        cue.setClickItemCartListener(this);
        holder.mItemCartBinding.setCueModel(cue);
    }

    @Override
    public int getItemCount() {
        return null == mListCue ? 0 : mListCue.size();
    }


    private void showConfirmDialogDeleteCue(Context context, Cue cue, int position) {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.confirm_delete_cue))
                .setMessage(context.getString(R.string.message_delete_cue))
                .setPositiveButton(context.getString(R.string.delete), (dialog, which) -> {
                    CueDatabase.getInstance(context).cueDAO().deleteCue(cue);

                    mListCue.remove(position);
                    notifyItemRemoved(position);
                    calculateTotalPrice(context);
                })
                .setNegativeButton(context.getString(R.string.dialog_cancel), (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void calculateTotalPrice(Context context) {
        List<Cue> listCueCart = CueDatabase.getInstance(context).cueDAO().getListCueCart();
        if (listCueCart == null || listCueCart.isEmpty()) {
            String strZero = 0 + Constant.CURRENCY;
            iCalculatePriceListener.calculatePrice(strZero, 0);
            return;
        }

        int totalPrice = 0;
        for (Cue cue : listCueCart) {
            totalPrice = totalPrice + cue.getTotalPrice();
        }

        String totalString = totalPrice + Constant.CURRENCY;
        iCalculatePriceListener.calculatePrice(totalString, totalPrice);
    }

    @Override
    public void clickDeteteCue(Context context, Cue cue, int position) {
        showConfirmDialogDeleteCue(context, cue, position);
    }

    @Override
    public void updateItemCue(Context context, Cue cue, int position) {
        CueDatabase.getInstance(context).cueDAO().updateCue(cue);
        notifyItemChanged(position);
        calculateTotalPrice(context);
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        private final ItemCartBinding mItemCartBinding;

        public CartViewHolder(ItemCartBinding itemCartBinding) {
            super(itemCartBinding.getRoot());
            this.mItemCartBinding = itemCartBinding;
        }
    }
}
