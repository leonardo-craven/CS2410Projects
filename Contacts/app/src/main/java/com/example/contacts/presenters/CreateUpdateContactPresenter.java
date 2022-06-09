package com.example.contacts.presenters;

import com.example.contacts.database.AppDatabase;
import com.example.contacts.models.Contact;

public class CreateUpdateContactPresenter {
    MVPView view;
    AppDatabase database;
    Contact contact;
    public interface MVPView extends BaseMVPView {
        void goBack(Contact contact);
        void goToPhotos();
        void goToCamera();
        void displayPicture(String pictureUri);
        void displayNameError();
        void displayPhoneError();
        void displayEmailError();
        void populateContactForm(Contact contact);
    }
    public static final int DEFAULT_POST_ID = -1;

    public CreateUpdateContactPresenter(MVPView view, int id) {
        this.view = view;
        this.database = view.getContextDatabase();

        if (id != DEFAULT_POST_ID) {
            new Thread(() -> {
                contact = database.getContactDao().findById(id).get(0);
                view.populateContactForm(contact);
            }).start();
        }
    }

    public void handleTakePicturePressed() {
        view.goToCamera();
    }

    public void handleSelectPictureButtonPressed() {
        view.goToPhotos();
    }

    public void handlePictureSelected(String pictureUri) {
        view.displayPicture(pictureUri);
    }

    public void handleCancelPressed() {
        view.goBack(null);
    }

    public void saveContact(String name, String phoneNumber, String email, String pictureUri) {
        boolean hasError = false;
        if (name.length() == 0) {
            view.displayNameError();
            hasError = true;
        }
        if (phoneNumber.length() == 0) {
            view.displayPhoneError();
            hasError = true;
        }
        if (email.length() != 0 && !email.contains("@")) {
            view.displayEmailError();
            hasError = true;
        }

        if (hasError) return;

        new Thread(() -> {
            if (contact == null) {
                Contact newContact = new Contact();
                newContact.name = name;
                newContact.phoneNumber = phoneNumber;
                newContact.email = email;
                newContact.pictureUri = pictureUri;
                newContact.id = (int) database.getContactDao().create(newContact);
                view.goBack(newContact);
            } else {
                contact.name = name;
                contact.phoneNumber = phoneNumber;
                contact.email = email;
                contact.pictureUri = pictureUri;
                database.getContactDao().update(contact);
                view.goBack(contact);
            }
        }).start();
    }
}
