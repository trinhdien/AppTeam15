package com.utc.asm_mob_java.screen.addnews;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.utc.asm_mob_java.R;
import com.utc.asm_mob_java.base.baseactivity.BaseBindingActivity;
import com.utc.asm_mob_java.databinding.AddNewsActivityBinding;
import com.utc.asm_mob_java.dialog.DialogUtils;
import com.utc.asm_mob_java.utils.ImageUtils;

public class AddNewActivity extends BaseBindingActivity<AddNewsActivityBinding, AddNewPresenter> implements AddNewView {
    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private Uri imageUri;
    private static final int REQUEST_PERMISSIONS = 123;


    @Override
    public void showMessage(String mess) {

    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    public void showErr(String err) {
        DialogUtils.showErrDialog(this, err);
    }

    @Override
    protected int getIdLayout() {
        return R.layout.add_news_activity;
    }

    @Override
    protected void initData() {
        mPresenter = new AddNewPresenter(this, this);
        mBinding.setPresenter(mPresenter);
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImage = result.getData().getData();
                        String base64Image = ImageUtils.convertImageToBase64(this, selectedImage);
                        mPresenter.notifySelectImage(base64Image);
                    }
                }
        );

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        if (imageUri != null) {
                            String base64Image = ImageUtils.convertImageToBase64(this, imageUri);
                            mPresenter.notifySelectImage(base64Image);
                        }
                    }
                }
        );


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSIONS && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            Toast.makeText(this, "Cần cấp quyền để chọn ảnh", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("IntentReset")
    private void openGallery() {
        @SuppressLint("IntentReset") Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        cameraLauncher.launch(intent);
    }

    @Override
    public void onChooseImage() {
        showImagePickerDialog();
    }

    private void requestPermissionAndOpenGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_PERMISSIONS);
            } else {
                openGallery();
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
            } else {
                openGallery();
            }
        }
    }

    private void requestPermissionAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSIONS);
        } else {
            openCamera();
        }
    }

    private void showImagePickerDialog() {
        String[] options = {"Chọn ảnh từ thư viện", "Chụp ảnh từ Camera"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn ảnh")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        requestPermissionAndOpenGallery();
                    } else {
                        requestPermissionAndOpenCamera();
                    }
                }).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.compositeDisposable.dispose();
        }
    }
}
