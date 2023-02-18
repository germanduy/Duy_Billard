package com.example.billiardshop.viewmodel;

import android.content.Context;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billiardshop.R;
import com.example.billiardshop.adapter.ContactAdapter;
import com.example.billiardshop.model.Contact;

public class ContactViewModel {

    private Context mContext;
    public ObservableList<Contact> listContacts = new ObservableArrayList<>();

    public ContactViewModel(Context mContext) {
        this.mContext = mContext;
        getListContacts();
    }

    private void getListContacts() {
        listContacts.add(new Contact(Contact.FACEBOOK, R.drawable.ic_facebook, mContext.getString(R.string.label_facebook)));
        listContacts.add(new Contact(Contact.HOTLINE, R.drawable.ic_hotline, mContext.getString(R.string.label_call)));
        listContacts.add(new Contact(Contact.GMAIL, R.drawable.ic_gmail, mContext.getString(R.string.label_gmail)));
        listContacts.add(new Contact(Contact.SKYPE, R.drawable.ic_skype, mContext.getString(R.string.label_skype)));
        listContacts.add(new Contact(Contact.YOUTUBE, R.drawable.ic_youtube, mContext.getString(R.string.label_youtube)));
        listContacts.add(new Contact(Contact.ZALO, R.drawable.ic_zalo, mContext.getString(R.string.label_zalo)));
    }
    @BindingAdapter({"list_data"})
    public static void loadListContacts(RecyclerView recyclerView,ObservableList<Contact> list){
        GridLayoutManager manager = new GridLayoutManager(recyclerView.getContext(),3);
        recyclerView.setLayoutManager(manager);

        ContactAdapter contactAdapter = new ContactAdapter(list);
        recyclerView.setAdapter(contactAdapter);
    }

    public void release() {
        mContext = null;
    }


}
