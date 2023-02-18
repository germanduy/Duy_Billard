package com.example.billiardshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.billiardshop.R;
import com.example.billiardshop.databinding.FragmentContactBinding;
import com.example.billiardshop.view.BaseFragment;
import com.example.billiardshop.view.activity.MainActivity;
import com.example.billiardshop.viewmodel.ContactViewModel;

public class ContactFragment extends BaseFragment {

    private ContactViewModel contactViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentContactBinding fragmentContactBinding = FragmentContactBinding.inflate(inflater,container,false);

        contactViewModel = new ContactViewModel(getActivity());
        fragmentContactBinding.setContactViewModel(contactViewModel);

        return fragmentContactBinding.getRoot();
    }

    @Override
    protected void initToolbar() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null)
            mainActivity.setToolBar(true, getString(R.string.contact));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (contactViewModel != null) {
            contactViewModel.release();
        }
    }
}
