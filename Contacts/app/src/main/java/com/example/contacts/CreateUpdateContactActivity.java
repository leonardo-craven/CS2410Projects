package com.example.contacts;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.example.contacts.components.ImageSelector;
import com.example.contacts.components.MaterialInput;
import com.example.contacts.models.Contact;
import com.example.contacts.presenters.CreateUpdateContactPresenter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateUpdateContactActivity extends BaseActivity implements CreateUpdateContactPresenter.MVPView {
    CreateUpdateContactPresenter presenter;
    LinearLayout mainLayout;
    ImageSelector selector;
    MaterialInput nameInput;
    MaterialInput phoneNumberInput;
    MaterialInput emailInput;
    private final int PICK_IMAGE = 1;
    private final int TAKE_PICTURE = 2;

    String currentFilePath;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id = getIntent().getIntExtra("id", CreateUpdateContactPresenter.DEFAULT_POST_ID);
        presenter = new CreateUpdateContactPresenter(this, id);
        mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        selector = new ImageSelector(this);
        selector.setOnClickListener((view) -> {
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Choose Image")
                    .setItems(new CharSequence[]{"From Camera", "From Photos"}, (menuItem, i) -> {
                        if (i == 0) {
                            presenter.handleTakePicturePressed();
                        } else {
                            presenter.handleSelectPictureButtonPressed();
                        }
                    }).show();
        });
        mainLayout.addView(selector);
        nameInput = new MaterialInput(this, "Name");
        phoneNumberInput = new MaterialInput(this, "Phone");
        phoneNumberInput.setInputType(InputType.TYPE_CLASS_PHONE);
        emailInput = new MaterialInput(this, "Email");

        MaterialButton saveButton = new MaterialButton(this);
        saveButton.setText("Save");

        saveButton.setOnClickListener((view) -> {
            nameInput.setErrorEnabled(false);
            phoneNumberInput.setErrorEnabled(false);
            emailInput.setErrorEnabled(false);

            presenter.saveContact(nameInput.getText().toString(),
                    phoneNumberInput.getText().toString(),
                    emailInput.getText().toString(),
                    selector.getImageUri()
            );
        });

        MaterialButton cancelButton = new MaterialButton(this, null, R.attr.borderlessButtonStyle);
        cancelButton.setText("Cancel");
        cancelButton.setOnClickListener((view -> {
            presenter.handleCancelPressed();
        }));

        LinearLayout buttonsLayout = new LinearLayout(this);
        buttonsLayout.setGravity(Gravity.RIGHT);
        buttonsLayout.setPadding(48, 0, 48, 0);
        buttonsLayout.addView(cancelButton);
        buttonsLayout.addView(saveButton);

        mainLayout.addView(nameInput);
        mainLayout.addView(phoneNumberInput);
        mainLayout.addView(emailInput);
        mainLayout.addView(buttonsLayout);

        ScrollView scrollView = new ScrollView(this);
        scrollView.addView(mainLayout);

        setContentView(scrollView);

    }

    @Override
    public void goBack(Contact contact) {
        if (contact == null) {
            setResult(Activity.RESULT_CANCELED, null);
        } else {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("result", contact);
            setResult(RESULT_OK, resultIntent);
        }
        finish();
    }

    @Override
    public void goToPhotos() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    public void goToCamera() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "JPEG_" + timeStamp + ".jpg";

        File imageFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName);
        currentFilePath = imageFile.getAbsolutePath();

        Uri imageUri = FileProvider.getUriForFile(
                this,
                "com.example.contacts.provider",
                imageFile
        );

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    public void displayPicture(String pictureUri) {
        selector.setImageUri(pictureUri);
    }

    @Override
    public void displayNameError() {
        nameInput.setErrorEnabled(true);
        nameInput.setError("Name cannot be blank");
        Snackbar.make(mainLayout, "Name cannot be blank", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void displayPhoneError() {
        phoneNumberInput.setErrorEnabled(true);
        phoneNumberInput.setError("Phone number cannot be blank");
        Snackbar.make(mainLayout, "Phone number cannot be blank", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void displayEmailError() {
        emailInput.setErrorEnabled(true);
        emailInput.setError("Invalid email address");
        Snackbar.make(mainLayout, "Invalid email address", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void populateContactForm(Contact contact) {
        runOnUiThread(() -> {
            nameInput.setText(contact.name);
            phoneNumberInput.setText(contact.phoneNumber);
            emailInput.setText(contact.email);
            selector.setImageUri(contact.pictureUri);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri pictureUri = data.getData();
            presenter.handlePictureSelected(pictureUri.toString());
        }
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            presenter.handlePictureSelected(currentFilePath);
        }
    }
}
