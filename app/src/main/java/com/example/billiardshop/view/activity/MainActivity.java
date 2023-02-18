package com.example.billiardshop.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.billiardshop.R;
import com.example.billiardshop.databinding.ActivityMainBinding;
import com.example.billiardshop.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        mainViewModel = new MainViewModel();
        activityMainBinding.setMainViewModel(mainViewModel);
        setContentView(activityMainBinding.getRoot());
    }

    public void setToolBar(boolean isShow, String title){
        mainViewModel.isShowToolbar.set(isShow);
        if ( isShow){
            mainViewModel.title.set(title);
        }
    }

    @Override
    public void onBackPressed() {
        showConfirmExitApp();
    }

    private void showConfirmExitApp(){
        new MaterialDialog.Builder(this)
                .title(getString(R.string.app_name))
                .content(getString(R.string.msg_exit_app))
                .positiveText(getString(R.string.action_ok))
                .onPositive((dialog, which) -> MainActivity.this.finish())
                .negativeText(getString(R.string.action_cancel))
                .cancelable(false)
                .show();
    }
}