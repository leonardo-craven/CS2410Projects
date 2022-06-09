package com.example.contacts;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;

import com.example.contacts.components.ContactView;
import com.example.contacts.models.Contact;
import com.example.contacts.presenters.ContactPresenter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

public class ContactActivity extends BaseActivity implements ContactPresenter.MVPView {
    ContactPresenter presenter;
    LinearLayout mainLayout;
    ContactView contactView;
    private final int UPDATE_CONTACT = 0;
    private final int CALL_PERMISSION_REQUESTED = 1;
    private final int TEXT_PERMISSION_REQUESTED = 2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainLayout = new LinearLayout(this);
        LinearLayout.LayoutParams mainParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mainLayout.setLayoutParams(mainParams);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        presenter = new ContactPresenter(this, getIntent().getIntExtra("id", -1));
        setContentView(mainLayout);
    }

    public void renderContactInfo(Contact contact) {
        runOnUiThread(() -> {
            contactView = new ContactView(this, contact);
            contactView.setCallButtonListener(view -> {
                presenter.handleCallButtonPressed();
            });
            contactView.setTextButtonListener(view -> {
                presenter.handleTextButtonPressed();
            });
            contactView.setEmailButtonListener(view -> {
                presenter.handleEmailButtonPressed();
            });
            contactView.setFabOnClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(this, view);
                popupMenu.getMenu().add("Edit");
                popupMenu.getMenu().add("Delete");
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener((menuItem) -> {
                    if (menuItem.getTitle().toString().equals("Edit")) {
                        // handle edit
                        presenter.handleEditPressed();
                    } else {
                        // handle delete
                        presenter.handleDeletePressed();
                    }
                    return true;
                });
            });
            mainLayout.addView(contactView);
        });
    }

    @Override
    public void updateContactUI(Contact contact) {
        contactView.setContactInfo(contact);
    }

    @Override
    public void goBack(Contact contact, boolean isDeleted, boolean isUpdated) {
        if (isDeleted) {
            Intent intent = new Intent();
            intent.putExtra("contact", contact);
            setResult(ContactsActivity.CONTACT_DELETED, intent);
        }
        else if (isUpdated) {
            Intent intent = new Intent();
            intent.putExtra("contact", contact);
            setResult(ContactsActivity.CONTACT_UPDATED, intent);
        }
        finish();
    }

    @Override
    public void displayDeleteConfirmation() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Are you sure you want to delete this contact?")
                .setPositiveButton("Delete", (view, i) -> {
                    presenter.deleteContact();
                })
                .setNeutralButton("Cancel", (view, i) -> {
                    view.dismiss();
                }).show();
    }

    @Override
    public void goToUpdatePage(Contact contact) {
        Intent intent = new Intent(this, CreateUpdateContactActivity.class);
        intent.putExtra("id", contact.id);
        startActivityForResult(intent, UPDATE_CONTACT);
    }

    @Override
    public void makePhoneCall(String phoneNumber) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent();
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(callIntent);
        } else {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUESTED);
        }
    }

    @Override
    public void sendText(String phoneNumber) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            Intent textIntent = new Intent(Intent.ACTION_VIEW);
            textIntent.setData(Uri.parse("sms:" + phoneNumber));
            startActivity(textIntent);
        } else {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, TEXT_PERMISSION_REQUESTED);
        }
    }

    @Override
    public void sendEmail(String address) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + address));
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_CONTACT && resultCode == Activity.RESULT_OK) {
            Contact contact = (Contact)data.getSerializableExtra("result");
            presenter.handleContactUpdated(contact);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PERMISSION_REQUESTED) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // try to make the phone call again
                presenter.handleCallButtonPressed();
            } else {
                // display message saying that you will need to allow permissions to continue using this function.
                Snackbar.make(mainLayout, "Permission required to make phone call", Snackbar.LENGTH_SHORT).show();
            }
        }

        if (requestCode == TEXT_PERMISSION_REQUESTED) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // try to make the phone call again
                presenter.handleTextButtonPressed();
            } else {
                // display message saying that you will need to allow permissions to continue using this function.
                Snackbar.make(mainLayout, "Permission required to send text", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        presenter.handleBackPressed();
    }
}
