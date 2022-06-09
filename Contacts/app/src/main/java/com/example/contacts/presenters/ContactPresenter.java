package com.example.contacts.presenters;

import com.example.contacts.database.AppDatabase;
import com.example.contacts.models.Contact;

public class ContactPresenter {
    Contact contact;
    boolean didUpdate = false;

    private MVPView view;
    private AppDatabase database;

    public interface MVPView extends BaseMVPView {
        void renderContactInfo(Contact contact);
        void updateContactUI(Contact contact);
        void goBack(Contact contact, boolean isDeleted, boolean isUpdated);
        void displayDeleteConfirmation();
        void goToUpdatePage(Contact contact);
        void makePhoneCall(String phoneNumber);
        void sendText(String phoneNumber);
        void sendEmail(String address);
    }

    public ContactPresenter(MVPView view, int id) {
        this.view = view;
        this.database = view.getContextDatabase();
        new Thread(() -> {
            contact = database.getContactDao().findById(id).get(0);
            view.renderContactInfo(contact);
        }).start();
    }

    public void handleCallButtonPressed() {
        view.makePhoneCall(contact.phoneNumber);
    }

    public void handleTextButtonPressed() {
        view.sendText(contact.phoneNumber);
    }

    public void handleEmailButtonPressed() {
        view.sendEmail(contact.email);
    }

    public void handleEditPressed() {
        view.goToUpdatePage(contact);
    }

    public void handleDeletePressed() {
        view.displayDeleteConfirmation();
    }

    public void handleContactUpdated(Contact contact) {
        this.contact = contact;
        didUpdate = true;
        view.updateContactUI(contact);
    }

    public void deleteContact() {
        new Thread(() -> {
            database.getContactDao().delete(contact);
            view.goBack(contact, true, false);
        }).start();
    }

    public void handleBackPressed() {
        view.goBack(contact, false, didUpdate);
    }
}
