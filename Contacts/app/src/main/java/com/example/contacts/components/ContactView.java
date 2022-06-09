package com.example.contacts.components;

import android.content.Context;
import android.net.Uri;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.contacts.R;
import com.example.contacts.models.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContactView extends FrameLayout {
    LinearLayout body;
    AppCompatImageView picture;
    ContactCard cardView;
    FloatingActionButton fab;

    public ContactView(@NonNull Context context, Contact contact) {
        super(context);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);

        body = new LinearLayout(context);
        body.setOrientation(LinearLayout.VERTICAL);

        picture = new AppCompatImageView(context);
        FrameLayout.LayoutParams pictureParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 560);
        picture.setLayoutParams(pictureParams);
        updatePicture(contact);
        body.addView(picture);

        cardView = new ContactCard(context, contact);
        body.addView(cardView);

        addView(body);

        fab = new FloatingActionButton(context);
        FrameLayout.LayoutParams fabParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        fabParams.gravity = (Gravity.RIGHT | Gravity.BOTTOM);
        fabParams.setMargins(0, 0, 24, 24);
        fab.setLayoutParams(fabParams);
        fab.setImageResource(R.drawable.ic_baseline_edit_24);
        addView(fab);
    }

    public void setCallButtonListener(OnClickListener l) {
        cardView.setCallButtonListener(l);
    }

    public void setTextButtonListener(OnClickListener l) {
        cardView.setTextButtonListener(l);
    }

    public void setEmailButtonListener(OnClickListener l) {
        cardView.setEmailButtonListener(l);
    }

    public void setFabOnClickListener(OnClickListener l) {
        fab.setOnClickListener(l);
    }

    private void updatePicture(Contact contact) {
        if (contact.pictureUri.equals("")) {
            picture.setBackgroundColor(getResources().getColor(R.color.colorDarkBackground, null));
            picture.setImageResource(R.drawable.ic_baseline_image_240);
            picture.setScaleType(ImageView.ScaleType.CENTER);
        } else {
            picture.setImageURI(Uri.parse(contact.pictureUri));
            picture.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    public void setContactInfo(Contact contact) {
        updatePicture(contact);
        cardView.setContactInfo(contact);
    }
}
