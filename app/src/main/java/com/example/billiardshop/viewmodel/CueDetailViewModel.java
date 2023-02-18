package com.example.billiardshop.viewmodel;

import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billiardshop.R;
import com.example.billiardshop.adapter.MoreImageAdapter;
import com.example.billiardshop.constant.Constant;
import com.example.billiardshop.database.CueDatabase;
import com.example.billiardshop.databinding.LayoutBottomSheetCartBinding;
import com.example.billiardshop.event.ReloadListCartEvent;
import com.example.billiardshop.listener.IAddToCartSuccessListener;
import com.example.billiardshop.model.Cue;
import com.example.billiardshop.model.Image;
import com.example.billiardshop.untils.GlideUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class CueDetailViewModel {
    private Activity mActivity;
    private DialogCartViewModel mDialogCartViewModel;
    public Cue mCue;
    public ObservableBoolean isSale = new ObservableBoolean();
    public ObservableBoolean isMoreImages = new ObservableBoolean();
    public ObservableBoolean isCueInCart = new ObservableBoolean();
    public ObservableList<Image> listMoreImages = new ObservableArrayList<>();
    public String strSale, strPriceOld, strRealPrice;
    public ObservableField<String> strStatusCart = new ObservableField<>();

    public CueDetailViewModel(Activity mActivity, Cue mCue) {
        this.mActivity = mActivity;
        this.mCue = mCue;
        
        initData();
    }

    private void initData() {
        if (mCue == null) {
            return;
        }

        if (mCue.getSale() <= 0) {
            isSale.set(false);
            strRealPrice = mCue.getPrice() + Constant.CURRENCY;
        } else {
            isSale.set(true);
            strSale = mActivity.getString(R.string.label_discount) + " " + mCue.getSale() + "%";
            strPriceOld = mCue.getPrice() + Constant.CURRENCY;
            strRealPrice = mCue.getRealPrice() + Constant.CURRENCY;
        }

        if (mCue.getImages() == null || mCue.getImages().isEmpty()) {
            isMoreImages.set(false);
        } else {
            isMoreImages.set(true);
            listMoreImages.addAll(mCue.getImages());
        }

        if (isCueInCart(mCue.getId())) {
            isCueInCart.set(true);
            strStatusCart.set(mActivity.getString(R.string.added_to_cart));
        } else {
            isCueInCart.set(false);
            strStatusCart.set(mActivity.getString(R.string.add_to_cart));
        }
    }
    public ObservableField<String> getStrStatusCart(TextView textView) {
        if (isCueInCart.get()) {
            textView.setBackgroundResource(R.drawable.bg_gray_shape_corner_6);
            textView.setTextColor(ContextCompat.getColor(mActivity, R.color.black_50));
        } else {
            textView.setBackgroundResource(R.drawable.bg_green_shape_corner_6);
            textView.setTextColor(ContextCompat.getColor(mActivity, R.color.white));
        }
        return strStatusCart;
    }

    public String getStrPriceOld(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        return strPriceOld;
    }

    public void onClickButtonBack() {
        mActivity.onBackPressed();
    }

    @BindingAdapter({"url_image"})
    public static void loadImageFromUrl(ImageView imageView, String url) {
        GlideUtils.loadUrlBanner(url, imageView);
    }

    @BindingAdapter({"list_more_image"})
    public static void loadListMoreImages(RecyclerView recyclerView, ObservableList<Image> list) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        MoreImageAdapter moreImageAdapter = new MoreImageAdapter(list);
        recyclerView.setAdapter(moreImageAdapter);
    }

    public boolean isCueInCart(int cueId) {
        List<Cue> list = CueDatabase.getInstance(mActivity).cueDAO().checkCueInCart(cueId);
        return list != null && !list.isEmpty();
    }

    public void onClickAddToCart() {
        if (isCueInCart.get()) {
            return;
        }

        LayoutBottomSheetCartBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mActivity),
                R.layout. layout_bottom_sheet_cart, null, false);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mActivity);
        bottomSheetDialog.setContentView(binding.getRoot());


        mDialogCartViewModel = new DialogCartViewModel(mActivity, bottomSheetDialog, mCue, new IAddToCartSuccessListener() {
            @Override
            public void addToCartSuccess() {
                bottomSheetDialog.dismiss();
                isCueInCart.set(true);
                strStatusCart.set(mActivity.getString(R.string.added_to_cart));
                EventBus.getDefault().post(new ReloadListCartEvent());

            }
        });

        binding.setDialogCartViewModel(mDialogCartViewModel);

        bottomSheetDialog.show();
    }

    public void release() {
        mActivity = null;
        if (mDialogCartViewModel != null) {
            mDialogCartViewModel.release();
        }
    }


}
