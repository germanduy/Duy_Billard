package com.example.billiardshop.listener;

import android.content.Context;

import com.example.billiardshop.model.Cue;

public interface IClickItemCartListener {
    void clickDeteteCue(Context context, Cue cue, int position);
    void updateItemCue(Context context, Cue cue, int position);
}
