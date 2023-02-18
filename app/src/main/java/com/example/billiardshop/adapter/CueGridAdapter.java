package com.example.billiardshop.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billiardshop.databinding.ItemCueGridBinding;
import com.example.billiardshop.model.Cue;

import java.util.List;


public class CueGridAdapter extends RecyclerView.Adapter<CueGridAdapter.CueGridViewHolder> {

    private final List<Cue> mListCue;

    public CueGridAdapter(List<Cue> mListCue) {
        this.mListCue = mListCue;
    }

    @NonNull
    @Override
    public CueGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCueGridBinding itemCueGridBinding =ItemCueGridBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CueGridViewHolder(itemCueGridBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CueGridViewHolder holder, int position) {
        Cue cue = mListCue.get(position);
        if( cue== null){
            return;
        }
        holder.mItemCueGridBinding.setCueModel(cue);

    }

    @Override
    public int getItemCount() {
        return null == mListCue ? 0 : mListCue.size();
    }

    public static class CueGridViewHolder extends RecyclerView.ViewHolder {
        private final ItemCueGridBinding mItemCueGridBinding;

        public CueGridViewHolder(ItemCueGridBinding itemCueGridBinding) {
            super(itemCueGridBinding.getRoot());
            this.mItemCueGridBinding = itemCueGridBinding;
        }
    }
}
