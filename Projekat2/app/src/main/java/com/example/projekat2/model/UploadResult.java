package com.example.projekat2.model;

public class UploadResult<T> {

    public static final int STATUS_UPLOADING = 1;
    public static final int STATUS_FAILED = 2;
    public static final int STATUS_SUCCESS = 3;
    public static final int STATUS_GOT_URL = 4;

    private T mData;

    private int mUploadStatus;

    private long mProgress;

    public UploadResult(int uploadStatus, long progress) {
        mUploadStatus = uploadStatus;
        mProgress = progress;
    }

    public UploadResult(int uploadStatus, T data) {
        mData = data;
        mUploadStatus = uploadStatus;
    }

    public UploadResult(int uploadStatus) {
        mUploadStatus = uploadStatus;
    }

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        mData = data;
    }

    public int getUploadStatus() {
        return mUploadStatus;
    }

    public void setUploadStatus(int uploadStatus) {
        mUploadStatus = uploadStatus;
    }

    public long getProgress() {
        return mProgress;
    }

    public void setProgress(long progress) {
        mProgress = progress;
    }
}
