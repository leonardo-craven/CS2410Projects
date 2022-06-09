package com.example.contacts.components;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import com.example.contacts.models.Contact;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ContactListItem extends LinearLayout {
    View picture;
    Contact contact;
    AppCompatTextView nameText;

    public ContactListItem(Context context, Contact contact) {
        super(context);
        this.contact = contact;
        setTag(contact.id);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 12 ,0, 12);
        setLayoutParams(params);

        updatePicture(contact);

        this.nameText = new AppCompatTextView(context);
        this.nameText.setText(contact.name);
        this.nameText.setTextSize(24);
        nameText.setPadding(12, 0, 0, 0);

        addView(nameText);
    }

    private void updatePicture(Contact contact) {
        if (picture != null) {
            removeView(picture);
            picture = null;
        }
        if (contact.pictureUri.equals("")) {
            picture = new CircleDisplay(getContext(), Character.toString(contact.name.charAt(0)));
        }
        else {
            CardView cardView = new CardView(getContext());
            float density = getResources().getDisplayMetrics().density;
            cardView.setRadius(20*density);
            CardView.LayoutParams cardParams = new CardView.LayoutParams((int)(40*density), (int)(40*density));
            cardView.setLayoutParams(cardParams);
            AppCompatImageView imageView = new AppCompatImageView(getContext());
            imageView.setImageURI(Uri.parse(contact.pictureUri));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            cardView.addView(imageView, 0);
            picture = cardView;
        }
        addView(picture, 0);
    }

    public void updateContactInfo(Contact contact) {
        updatePicture(contact);
        nameText.setText(contact.name);
    }

}
