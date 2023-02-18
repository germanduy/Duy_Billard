package com.example.billiardshop.view.activity;

import android.os.Bundle;

import com.example.billiardshop.constant.Constant;
import com.example.billiardshop.databinding.ActivityCueDetailBinding;
import com.example.billiardshop.model.Cue;
import com.example.billiardshop.view.BaseActivity;
import com.example.billiardshop.viewmodel.CueDetailViewModel;

public class CueDetailActivity extends BaseActivity {

    private CueDetailViewModel mCueDetailViewModel;
    private Cue mCue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCueDetailBinding activityCueDetailBinding = ActivityCueDetailBinding.inflate(getLayoutInflater());
        setContentView(activityCueDetailBinding.getRoot());

        getDataIntent();
        mCueDetailViewModel = new CueDetailViewModel(this, mCue);
        activityCueDetailBinding.setCueDetailViewModel(mCueDetailViewModel);
    }

    private void getDataIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mCue = (Cue) bundle.get(Constant.KEY_INTENT_CUE_OBJECT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCueDetailViewModel != null) {
            mCueDetailViewModel.release();
        }
    }
}
