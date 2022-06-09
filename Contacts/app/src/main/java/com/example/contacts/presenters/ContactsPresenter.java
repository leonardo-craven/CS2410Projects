package com.example.contacts.presenters;

import com.example.contacts.database.AppDatabase;
import com.example.contacts.models.Contact;

import java.util.ArrayList;

public class ContactsPresenter {
    private MVPView view;
    private ArrayList<Contact> contacts = new ArrayList<>();
    private AppDatabase database;
    public interface MVPView extends BaseMVPView {
        void renderContact(Contact contact);
        void goToNewContactPage();
        void goToContactPage(Contact contact);
        void removeContact(Contact contact);
        void updateContactUI(Contact contact);
    }

    public ContactsPresenter(MVPView view) {
        this.view = view;
        this.database = view.getContextDatabase();
        loadContacts();
    }

    public void loadContacts() {
        new Thread(() -> {
            contacts = (ArrayList<Contact>)database.getContactDao().getAll();
            contacts.forEach(contact -> view.renderContact(contact));
        }).start();
    }

    public void handleCreateNewContactPress() {
        new Thread(() -> view.goToNewContactPage()).start();
    }

    public void handleNewContactCreated(Contact contact) {
        contacts.add(contact);
        view.renderContact(contact);
    }

    public void handleContactClicked(Contact contact) {
        new Thread(() -> view.goToContactPage(contact)).start();
    }

    public void onContactDeleted(Contact contact) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).id == contact.id) {
                contacts.remove(i);
                break;
            }
        }
        view.removeContact(contact);
    }

    public void onContactUpdated(Contact contact) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).id == contact.id) {
                contacts.set(i, contact);
                break;
            }
        }
        view.updateContactUI(contact);
    }
}
