package com.example.projekat2.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.projekat2.R;
import com.example.projekat2.model.Message;
import com.example.projekat2.model.MessageType;
import com.example.projekat2.model.UploadResult;
import com.example.projekat2.model.User;
import com.example.projekat2.util.ImageUtil;
import com.example.projekat2.view.adapter.MessageAdapter;
import com.example.projekat2.viewmodel.ImageViewModel;
import com.example.projekat2.viewmodel.MessageViewModel;
import com.example.projekat2.viewmodel.UserViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class WallFragment extends Fragment {

    private static final int REQUEST_CAMERA_PERMISSION = 222;
    private static final int REQUEST_CAMERA_PHOTO = 333;

    private RecyclerView rvMessages;
    private EditText etText;
    private ImageView ivSend;
    private ImageView ivAddPicture;
    private ProgressBar pbLoading;
    private CardView llLoading;

    private UserViewModel userViewModel;
    private MessageViewModel messageViewModel;
    private ImageViewModel imageViewModel;

    private MessageAdapter adapter;
    private LinearLayoutManager layoutManager;

    private String thisUser;

    private File imageFile;

    public static WallFragment newInstance() {
        return new WallFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_chat,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getReferences();
        setupUI();
        setupListeners();
    }

    private void getReferences() {
        View v = getView();
        rvMessages = v.findViewById(R.id.rv_chat_messages);
        etText = v.findViewById(R.id.et_chat_text);
        ivSend = v.findViewById(R.id.iv_chat_send);
        ivAddPicture = v.findViewById(R.id.iv_chat_add_picture);
        pbLoading = v.findViewById(R.id.pb_chat_loading);
        llLoading = v.findViewById(R.id.ll_chat_2);

        messageViewModel = ViewModelProviders.of(getActivity()).get(MessageViewModel.class);
        imageViewModel = ViewModelProviders.of(getActivity()).get(ImageViewModel.class);
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        adapter = new MessageAdapter();
        layoutManager = new LinearLayoutManager(getActivity());

        Intent intent = getActivity().getIntent();
        thisUser = intent.getStringExtra("index");
    }
    private void setupUI() {
        rvMessages.setLayoutManager(layoutManager);
        rvMessages.setAdapter(adapter);
        ivAddPicture.setVisibility(View.VISIBLE);
    }

    private void setupListeners() {
        ivSend.setOnClickListener((v) -> {
            Message message = new Message(thisUser, MessageType.TEXT,etText.getText().toString());
            etText.setText("");
            messageViewModel.addWallMessage(message);
            hideKeyboard();
        });

        ivAddPicture.setOnClickListener((v) -> takePhoto());

        messageViewModel.getWallMessagesLiveData().observe(this,(messages) -> {
            adapter.setData(messages);
            layoutManager.scrollToPosition(adapter.getItemCount()-1);
        });

        userViewModel.getAllUsers().observe(this,(users -> {
            HashMap<String,String> indexNameMap = new HashMap<>();
            for(User u : users) {
                indexNameMap.put(u.getIndex(),u.getName());
            }
            adapter.setUsers(indexNameMap);
        }));

        imageViewModel.getUploadPhotoLiveData().observe(getActivity(),(uploadResult) -> {
            switch(uploadResult.getUploadStatus()) {
                case UploadResult.STATUS_SUCCESS:
                    setLoadingState(false);
                    Toast.makeText(this.getActivity(),"Image uploaded!",Toast.LENGTH_SHORT).show();
                    break;
                case UploadResult.STATUS_UPLOADING:
                    setLoadingState(true);
                    pbLoading.setProgress((int) uploadResult.getProgress());
                    break;
                case UploadResult.STATUS_FAILED:
                    setLoadingState(false);
                    Toast.makeText(this.getActivity(),"Image upload failed!",Toast.LENGTH_SHORT).show();
                    break;
                case UploadResult.STATUS_GOT_URL:
                    String content = uploadResult.getData().toString();
                    Message message = new Message(thisUser,MessageType.IMAGE,content);
                    messageViewModel.addWallMessage(message);
                    layoutManager.scrollToPosition(adapter.getItemCount()-1);
                    break;
            }
        });

    }

    public void setLoadingState(boolean loading) {
        etText.setEnabled(!loading);
        ivSend.setEnabled(!loading);
        ivAddPicture.setEnabled(!loading);
        llLoading.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @SuppressLint("MissingPermission")
    private void takePhoto() {

        if (!hasAnyFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(getActivity(),"No camera!",Toast.LENGTH_SHORT).show();
            return;
        }

        if (hasPermissions(Manifest.permission.CAMERA)) {

            try {
                imageFile = ImageUtil.createImageFile(getActivity());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photoURI = FileProvider.getUriForFile(getActivity(), getString(R.string.app_file_provider), imageFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(intent, REQUEST_CAMERA_PHOTO);
        } else {
            requestPermissions(REQUEST_CAMERA_PERMISSION, Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    Toast.makeText(getActivity(),"Permission not granted",Toast.LENGTH_SHORT).show();
                }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != REQUEST_CAMERA_PHOTO ) {
            return;
        }

        if (resultCode != getActivity().RESULT_OK) {
            Toast.makeText(getActivity(), "You didn't capture photo!", Toast.LENGTH_SHORT).show();
            return;
        }

        imageViewModel.addImage(imageFile);

    }

    protected boolean hasAnyFeature(String... features){
        for (String feature : features) {
            if (getActivity().getPackageManager().hasSystemFeature(feature)){
                return true;
            }
        }
        return false;
    }

    protected boolean hasPermissions(String... permissions){
        for (String permission : permissions) {
            boolean hasPermission = ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_GRANTED;
            if(!hasPermission) {
                return false;
            }
        }
        return true;
    }

    protected void requestPermissions(int requestCode, String... permissions){
        ActivityCompat.requestPermissions(getActivity(), permissions, requestCode);
    }

    // Copy-paste from stack overflow
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getActivity().getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(getActivity());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
