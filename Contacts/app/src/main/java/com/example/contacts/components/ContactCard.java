package com.example.contacts.components;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.contacts.R;
import com.example.contacts.models.Contact;
import com.google.android.material.card.MaterialCardView;

public class ContactCard extends MaterialCardView {
    LinearLayout body;
    LinearLayout nameField;
    AppCompatTextView nameText;
    LinearLayout phoneNumberField;
    AppCompatTextView phoneNumberText;
    AppCompatImageView callButton;
    AppCompatImageView textButton;
    LinearLayout emailField;
    AppCompatTextView emailText;
    AppCompatImageView emailButton;

    public ContactCard(Context context, Contact contact) {
        super(context);

        body = new LinearLayout(context);
        body.setOrientation(LinearLayout.VERTICAL);

        nameField = new LinearLayout(context);
        nameField.setPadding(0, 24, 0, 24);
        AppCompatImageView nameIcon = new AppCompatImageView(context);
        nameIcon.setImageResource(R.drawable.ic_baseline_person_24);
        nameField.addView(nameIcon);

        nameText = new AppCompatTextView(context);
        nameText.setText(contact.name);
        nameField.addView(nameText);

        body.addView(nameField);

        phoneNumberField = new LinearLayout(context);
        phoneNumberField.setPadding(0, 24, 0, 24);
        callButton = new AppCompatImageView(context);
        callButton.setImageResource(R.drawable.ic_baseline_call_24);
        callButton.setPadding(12, 0, 12, 0);
        phoneNumberField.addView(callButton);

        textButton = new AppCompatImageView(context);
        textButton.setImageResource(R.drawable.ic_baseline_textsms_24);
        textButton.setPadding(12, 0, 12, 0);
        phoneNumberField.addView(textButton);

        phoneNumberText = new AppCompatTextView(context);
        phoneNumberText.setText(contact.phoneNumber);
        phoneNumberField.addView(phoneNumberText);

        body.addView(phoneNumberField);

        if (!contact.email.equals("")) {
            createEmailField(context, contact);
        }

        addView(body);
    }

    public void setCallButtonListener(OnClickListener l) {
        callButton.setOnClickListener(l);
    }

    public void setTextButtonListener(OnClickListener l) {
        textButton.setOnClickListener(l);
    }

    public void setEmailButtonListener(OnClickListener l) {
        if (emailButton != null) {
            emailButton.setOnClickListener(l);
        }
    }

    private void createEmailField(Context context, Contact contact) {
        emailField = new LinearLayout(context);
        emailField.setPadding(0, 24, 0, 24);
        emailButton = new AppCompatImageView(context);
        emailButton.setImageResource(R.drawable.ic_baseline_email_24);
        emailButton.setPadding(12, 0, 12, 0);
        emailField.addView(emailButton);

        emailText = new AppCompatTextView(context);
        emailText.setText(contact.email);
        emailField.addView(emailText);

        body.addView(emailField);
    }

    public void setContactInfo(Contact contact) {
        nameText.setText(contact.name);
        phoneNumberText.setText(contact.phoneNumber);
        //if new email is blank and email field exists, delete email field
        if (contact.email.equals("") && emailField != null) {
            body.removeView(emailField);
            emailField = null;
            emailText = null;
            emailButton = null;
        }
        // if new email is not blank and no email field exists, create email field
        else if (!contact.email.equals("") && emailField == null) {
            createEmailField(getContext(), contact);
        }
        // if new email is not blank and email field exists, update email text
        else if (!contact.email.equals("")){
            emailText.setText(contact.email);
        }
    }
}
