package com.example.billiardshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.billiardshop.R;
import com.example.billiardshop.databinding.FragmentCartBinding;
import com.example.billiardshop.event.ReloadListCartEvent;
import com.example.billiardshop.view.BaseFragment;
import com.example.billiardshop.view.activity.MainActivity;
import com.example.billiardshop.viewmodel.CartViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CartFragment extends BaseFragment {
    private CartViewModel mCartViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentCartBinding fragmentCartBinding = FragmentCartBinding.inflate(inflater, container, false);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mCartViewModel = new CartViewModel(getActivity());
        fragmentCartBinding.setCartViewModel(mCartViewModel);

        return fragmentCartBinding.getRoot();
    }

    @Override
    protected void initToolbar() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null)
            mainActivity.setToolBar(true, getString(R.string.cart));
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ReloadListCartEvent event) {
        if (mCartViewModel != null) {
            mCartViewModel.getListCueInCart();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCartViewModel != null) {
            mCartViewModel.release();
        }
    }
}
