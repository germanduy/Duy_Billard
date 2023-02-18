package com.example.billiardshop.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.billiardshop.R;
import com.example.billiardshop.adapter.CueGridAdapter;
import com.example.billiardshop.adapter.CuePopularAdapter;
import com.example.billiardshop.constant.GlobalFuntion;
import com.example.billiardshop.model.Cue;
import com.example.billiardshop.untils.StringUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


import me.relex.circleindicator.CircleIndicator3;

public class HomeViewModel extends BaseObservable {

    private Context mContext;
    public ObservableList<Cue> listCue = new ObservableArrayList<>();
    public ObservableList<Cue> listCuePopular = new ObservableArrayList<>();
    public ObservableBoolean isSuccess = new ObservableBoolean();
    public ObservableField<String> stringHint = new ObservableField<>();

    public HomeViewModel(Context mContext) {
        this.mContext = mContext;
        getListCueFromFirebase("");
    }

    public void getListCueFromFirebase(String key) {
        if (mContext == null) {
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("cue");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (listCue != null) {
                    listCue.clear();
                } else {
                    listCue = new ObservableArrayList<>();
                }

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Cue cue = snapshot1.getValue(Cue.class);
                    if (cue == null) {
                        return;
                    }

                    if (StringUtil.isEmpty(key)) {
                        listCue.add(0, cue);
                    } else {
                        if (GlobalFuntion.getTextSearch(cue.getName()).toLowerCase().trim()
                                .contains(GlobalFuntion.getTextSearch(key).toLowerCase().trim())) {
                            listCue.add(0, cue);
                        }
                    }
                }
                getListCuePopular(listCue);
                isSuccess.set(true);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listCue = null;
            }
        });
    }

    private void getListCuePopular(List<Cue> listCue) {
        if (listCuePopular != null) {
            listCuePopular.clear();
        } else {
            listCuePopular = new ObservableArrayList<>();
        }
        if (listCue == null || listCue.isEmpty()) {
            return;
        }
        for (Cue cue : listCue) {
            if (cue.isPopular()) {
                listCuePopular.add(cue);
            }
        }
    }

    public ObservableField<String> getStringHint(EditText editText) {
        stringHint.set(mContext.getString(R.string.hint_search_name));

        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String keyword = editText.getText().toString();
                searchCue(keyword);
                return true;
            }
            return false;
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String strKey = s.toString().trim();
                if (StringUtil.isEmpty(strKey)) {
                    searchCue("");
                }
            }
        });
        return stringHint;
    }

    private void searchCue(String key) {
        if (listCue != null) {
            listCue.clear();
        }
        getListCueFromFirebase(key);
    }

    @BindingAdapter({"list_data"})
    public static void loadListCue(RecyclerView recyclerView, ObservableList<Cue> list) {
        GlobalFuntion.hideSoftKeyboard((Activity) recyclerView.getContext());

        if (list == null) {
            GlobalFuntion.showToastMessage(recyclerView.getContext(),
                    recyclerView.getContext().getString(R.string.msg_get_date_error));
            return;
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        CueGridAdapter cueGridAdapter = new CueGridAdapter(list);
        recyclerView.setAdapter(cueGridAdapter);
    }

    @BindingAdapter(value = {"list_data_popular", "indicator_viewpager"})
    public static void loadListCuePopular(ViewPager2 viewPager2, ObservableList<Cue> list, CircleIndicator3 indicator3) {
        CuePopularAdapter cuePopularAdapter = new CuePopularAdapter(list);
        viewPager2.setAdapter(cuePopularAdapter);

        Handler handlerBanner = new Handler(Looper.getMainLooper());
        Runnable runnableBanner = () -> {
            if (list == null || list.isEmpty()) {
                return;
            }
            if (viewPager2.getCurrentItem() == list.size() - 1) {
                viewPager2.setCurrentItem(0);
                return;
            }
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        };
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handlerBanner.removeCallbacks(runnableBanner);
                handlerBanner.postDelayed(runnableBanner, 3000);
            }
        });

        indicator3.setViewPager(viewPager2);
    }
    public void onClickButtonSearch(EditText editText) {
        String keyword = editText.getText().toString();
        searchCue(keyword);
    }



    public void release() {
        mContext = null;
    }
}
