package com.example.salon.myapplication.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.salon.myapplication.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private int CAM_ANSER = 12;
    private int SELCT_PHOTO = 20;
    private String imageName = "profilePic.png";
    private ImageView pic;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkAndReqeustPremissions();
        setContentView(R.layout.activity_profile);
        pic = (ImageView) findViewById(R.id.pic);
        loadProfileImage(imageName);
    }

    String[] premissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.SEND_SMS,
            Manifest.permission.VIBRATE,
    };



    private void checkAndReqeustPremissions() {
        List<String> ListPremissionsNeeded = new ArrayList<>();
        for (String permission : premissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_DENIED)
                ListPremissionsNeeded.add(permission);
        }
        if (!ListPremissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, ListPremissionsNeeded.toArray(new String[ListPremissionsNeeded.size()]), 1);
        }

    }

    public void picImage(View view) {
        Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camIntent, CAM_ANSER);
    }

    public void onGallaryClick(View view) {
        Intent photoPickIntent = new Intent(Intent.ACTION_PICK);
        photoPickIntent.setType("image/*");
        startActivityForResult(photoPickIntent, SELCT_PHOTO);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAM_ANSER && resultCode == RESULT_OK) {
            imageBitmap = (Bitmap) data.getExtras().get("data");
            pic.setImageBitmap(imageBitmap);
            privateAddPic();
        }
        if (requestCode == SELCT_PHOTO && resultCode == RESULT_OK) {
            Uri Selected_Image_Uri = data.getData();
            pic.setImageURI(Selected_Image_Uri);

        }

    }

    private void privateAddPic() {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput(imageName, Context.MODE_PRIVATE);
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);

            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadProfileImage(String name) {
        FileInputStream fileInputStream;
        Bitmap bitmap = null;
        try {
            fileInputStream = openFileInput(name);
            bitmap = BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();
            pic.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
