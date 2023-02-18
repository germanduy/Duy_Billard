package com.example.billiardshop.model;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.billiardshop.BR;
import com.example.billiardshop.R;
import com.example.billiardshop.constant.GlobalFuntion;
import com.example.billiardshop.untils.StringUtil;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class Feedback extends BaseObservable {

    private String name;
    private String phone;
    private String email;
    private String comment;

    public Feedback() {
    }

    public Feedback(String name, String phone, String email, String comment) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.comment = comment;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(com.example.billiardshop.BR.name);

    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
        notifyPropertyChanged(BR.comment);
    }

    public void clickSendFeedback(View view) {
        Context context = view.getContext();
        if (StringUtil.isEmpty(name)) {
            GlobalFuntion.showToastMessage(context, context.getString(R.string.name_require));
        } else if (StringUtil.isEmpty(comment)) {
            GlobalFuntion.showToastMessage(context, context.getString(R.string.comment_require));
        } else {
            Feedback feedback = new Feedback(name, phone, email, comment);
            long id = System.currentTimeMillis();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("feedback");

            myRef.child(String.valueOf(id))
                    .setValue(feedback, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            GlobalFuntion.hideSoftKeyboard((Activity) context);
                            GlobalFuntion.showToastMessage(context, context.getString(R.string.send_feedback_success));
                            Feedback.this.setName("");
                            Feedback.this.setPhone("");
                            Feedback.this.setEmail("");
                            Feedback.this.setComment("");
                        }
                    });
        }

    }
}
