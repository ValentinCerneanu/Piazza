package com.innovation.piazza.Services;

import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.innovation.piazza.Domain.Category;
import com.innovation.piazza.Domain.Item;
import com.innovation.piazza.Domain.Store;

import java.io.File;
import java.io.IOException;

public class FirebaseCommunication {

    private FirebaseDatabase database;
    private FirebaseStorage storage;

    public FirebaseCommunication() {
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public void getImageStore(final String url, final Store store) {
        final File localFile;
        try {
            StorageReference storageReference = storage.getReference();
            storageReference = storageReference.child(url);
            localFile = File.createTempFile("storeLogo", ".png");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    store.setBitmap(BitmapFactory.decodeFile(localFile.getAbsolutePath()));
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

    public void getImageCategory(final String url, final Category category) {
        final File localFile;
        try {
            StorageReference storageReference = storage.getReference();
            storageReference = storageReference.child(url);
            localFile = File.createTempFile("storeLogo", ".png");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    category.setBitmap(BitmapFactory.decodeFile(localFile.getAbsolutePath()));
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

    public void getImageItem(final String url, final Item item) {
        final File localFile;
        try {
            StorageReference storageReference = storage.getReference();
            storageReference = storageReference.child(url);
            localFile = File.createTempFile("storeLogo", ".png");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    item.setBitmap(BitmapFactory.decodeFile(localFile.getAbsolutePath()));
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
