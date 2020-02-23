package com.innovation.piazza.Services;

import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.innovation.piazza.Domain.StoreModel;

import java.io.File;
import java.io.IOException;

public class FirebaseCommunication {

    private FirebaseDatabase database;
    private FirebaseStorage storage;

    public FirebaseCommunication() {
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public void getImage(final String url, final StoreModel storeModel) {
        final File localFile;
        try {
            StorageReference storageReference = storage.getReference();
            storageReference = storageReference.child(url);
            localFile = File.createTempFile("storeLogo", ".png");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    storeModel.setBitmap(BitmapFactory.decodeFile(localFile.getAbsolutePath()));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    System.out.println(url);
                    exception.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
