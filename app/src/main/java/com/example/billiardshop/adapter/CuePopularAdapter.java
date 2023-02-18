package com.example.billiardshop.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billiardshop.databinding.ItemCuePopularBinding;
import com.example.billiardshop.model.Cue;

import java.util.List;

public class CuePopularAdapter extends RecyclerView.Adapter<CuePopularAdapter.CuePopularViewHolder> {

    private final List<Cue> mListCue;

    public CuePopularAdapter(List<Cue> mListCue) {
        this.mListCue = mListCue;
    }

    @NonNull
    @Override
    public CuePopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCuePopularBinding itemCuePopularBinding = ItemCuePopularBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CuePopularViewHolder(itemCuePopularBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CuePopularViewHolder holder, int position) {
        Cue cue = mListCue.get(position);
        if (cue == null) {
            return;
        }
        holder.mItemCuePopularBinding.setCueModel(cue);
    }

    @Override
    public int getItemCount() {
        return null == mListCue ? 0 : mListCue.size();
    }

    public static class CuePopularViewHolder extends RecyclerView.ViewHolder {
        private final ItemCuePopularBinding mItemCuePopularBinding;

        public CuePopularViewHolder(ItemCuePopularBinding itemCuePopularBinding) {
            super(itemCuePopularBinding.getRoot());
            this.mItemCuePopularBinding = itemCuePopularBinding;
        }
    }
}
