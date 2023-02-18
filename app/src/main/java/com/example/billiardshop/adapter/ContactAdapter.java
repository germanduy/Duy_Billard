package com.example.billiardshop.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billiardshop.databinding.ItemContactBinding;
import com.example.billiardshop.model.Contact;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private final List<Contact> listContact;

    public ContactAdapter(List<Contact> listContact) {
        this.listContact = listContact;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContactBinding itemContactBinding = ItemContactBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ContactViewHolder(itemContactBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = listContact.get(position);
        if (contact != null) {
            holder.itemContactBinding.setContactModel(contact);
        }

    }

    @Override
    public int getItemCount() {
        return null == listContact ? 0 : listContact.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        private final ItemContactBinding itemContactBinding;

        public ContactViewHolder(@NonNull ItemContactBinding itemContactBinding) {
            super(itemContactBinding.getRoot());
            this.itemContactBinding = itemContactBinding;
        }
    }
}
