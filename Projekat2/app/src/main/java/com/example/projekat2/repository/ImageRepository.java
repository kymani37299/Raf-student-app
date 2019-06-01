package com.example.projekat2.repository;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.projekat2.model.UploadResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;

public class ImageRepository {

    private MutableLiveData<UploadResult<Uri>> uploadLiveData;

    private StorageReference imagesReference;

    public ImageRepository() {
        uploadLiveData = new MutableLiveData<>();
        imagesReference = FirebaseStorage.getInstance().getReference().child("images");
    }

    public void addImage(File file) {
        Uri uri = Uri.fromFile(file);
        String name = file.getName();

        StorageReference uploadReference = imagesReference.child(name);
        uploadReference.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> {
                    UploadResult<Uri> uploadResult = new UploadResult<>(UploadResult.STATUS_SUCCESS);
                    uploadLiveData.setValue(uploadResult);
                })
                .addOnFailureListener(e -> {
                    UploadResult<Uri> uploadResult = new UploadResult<>(UploadResult.STATUS_FAILED);
                    uploadLiveData.setValue(uploadResult);
                })
                .addOnProgressListener(taskSnapshot -> {
                    long progress = ((100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
                    UploadResult<Uri> uploadResult = new UploadResult<>(UploadResult.STATUS_UPLOADING, progress);
                    uploadLiveData.setValue(uploadResult);
                }).continueWithTask(task -> uploadReference.getDownloadUrl()).addOnSuccessListener(uri1 -> {
                    UploadResult<Uri> uploadResult = new UploadResult<>(UploadResult.STATUS_GOT_URL, uri1);
                    uploadLiveData.setValue(uploadResult);
                });
    }

    public LiveData<UploadResult<Uri>> getUploadLiveData() {
        return uploadLiveData;
    }

}
