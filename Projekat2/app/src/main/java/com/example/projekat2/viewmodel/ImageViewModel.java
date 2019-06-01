package com.example.projekat2.viewmodel;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.projekat2.model.UploadResult;
import com.example.projekat2.repository.ImageRepository;

import java.io.File;

public class ImageViewModel extends ViewModel {

    private ImageRepository repo;

    public ImageViewModel() {
        repo = new ImageRepository();
    }

    public void addImage(File file) {
        repo.addImage(file);
    }

    public LiveData<UploadResult<Uri>> getUploadPhotoLiveData() {
        return repo.getUploadLiveData();
    }
}
