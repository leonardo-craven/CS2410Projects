package com.example.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.Nullable;

import com.example.contacts.components.ContactListItem;
import com.example.contacts.models.Contact;
import com.example.contacts.presenters.ContactsPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContactsActivity extends BaseActivity implements ContactsPresenter.MVPView {
    FrameLayout mainLayout;
    LinearLayout contactsLayout;
    ContactsPresenter presenter;
    private final int CREATE_NEW_CONTACT = 1;
    private final int MODIFY_CONTACT = 0;
    public static final int CONTACT_DELETED = 2;
    public static final int CONTACT_UPDATED = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ContactsPresenter(this);

        mainLayout = new FrameLayout(this);
        contactsLayout = new LinearLayout(this);
        contactsLayout.setOrientation(LinearLayout.VERTICAL);
        ScrollView scrollView = new ScrollView(this);
        scrollView.addView(contactsLayout);

        FloatingActionButton fab = new FloatingActionButton(this);
        FrameLayout.LayoutParams fabParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        fabParams.gravity = (Gravity.RIGHT | Gravity.BOTTOM);
        fabParams.setMargins(0, 0, 24, 24);
        fab.setLayoutParams(fabParams);
        fab.setImageResource(R.drawable.ic_baseline_add_24);
        fab.setOnClickListener(view -> presenter.handleCreateNewContactPress());


        mainLayout.addView(scrollView);
        mainLayout.addView(fab);

        setContentView(mainLayout);
    }

    @Override
    public void renderContact(Contact contact) {
        runOnUiThread(() -> {
            ContactListItem contactListItem = new ContactListItem(this, contact);
            contactListItem.setOnClickListener(view -> presenter.handleContactClicked(contact));
            contactsLayout.addView(contactListItem);
        });
    }

    @Override
    public void goToNewContactPage() {
        Intent intent = new Intent(this, CreateUpdateContactActivity.class);
        startActivityForResult(intent, CREATE_NEW_CONTACT);
    }

    @Override
    public void goToContactPage(Contact contact) {
        Intent intent = new Intent(this, ContactActivity.class);
        intent.putExtra("id", contact.id);
        startActivityForResult(intent, MODIFY_CONTACT);
    }

    @Override
    public void removeContact(Contact contact) {
        View view = contactsLayout.findViewWithTag(contact.id);
        contactsLayout.removeView(view);
    }

    @Override
    public void updateContactUI(Contact contact) {
        ContactListItem item = contactsLayout.findViewWithTag(contact.id);
        item.updateContactInfo(contact);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_NEW_CONTACT && resultCode == Activity.RESULT_OK) {
            assert data != null;
            Contact newContact = (Contact)data.getSerializableExtra("result");
            presenter.handleNewContactCreated(newContact);
        }

        if (requestCode == MODIFY_CONTACT && resultCode == CONTACT_DELETED) {
            Contact deletedContact = (Contact)data.getSerializableExtra("contact");
            presenter.onContactDeleted(deletedContact);
        }

        if (requestCode == MODIFY_CONTACT && resultCode == CONTACT_UPDATED) {
            Contact updatedContact = (Contact)data.getSerializableExtra("contact");
            presenter.onContactUpdated(updatedContact);
        }
    }
}