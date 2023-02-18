package com.example.billiardshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.billiardshop.R;
import com.example.billiardshop.databinding.FragmentOrderBinding;
import com.example.billiardshop.view.BaseFragment;
import com.example.billiardshop.view.activity.MainActivity;
import com.example.billiardshop.viewmodel.OrderViewModel;

public class OrderFragment extends BaseFragment {
    private OrderViewModel mOrderViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentOrderBinding fragmentOrderBinding = FragmentOrderBinding.inflate(inflater, container, false);
        mOrderViewModel = new OrderViewModel(getActivity());
        fragmentOrderBinding.setOrderViewModel(mOrderViewModel);

        return fragmentOrderBinding.getRoot();
    }

    @Override
    protected void initToolbar() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null)
            mainActivity.setToolBar(true, getString(R.string.order));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mOrderViewModel != null) {
            mOrderViewModel.release();
        }
    }
}
