package com.bcq.selector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.bcq.selector.entry.RequestConfig;
import com.bcq.selector.utils.ImageUtil;
import com.bcq.selector.utils.StringUtils;
import com.bcq.selector.utils.VersionUtils;
import com.bcq.selector.view.ClipImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * 裁剪ui
 */
public class ClipImageActivity extends Activity {

    private FrameLayout btnConfirm;
    private FrameLayout btnBack;
    private ClipImageView imageView;
    private int mRequestCode;
    private boolean isCameraImage;
    private float cropRatio;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_clip_image);

        Intent intent = getIntent();
        RequestConfig config = intent.getParcelableExtra(Selector.KEY_CONFIG);
        mRequestCode = config.requestCode;
        config.isSingle = true;
        config.maxSelectCount = 0;
        cropRatio = config.cropRatio;
        setStatusBarColor();
        ImageSelectorActivity.openActivity(this, mRequestCode, config);
        initView();
    }

    /**
     * 修改状态栏颜色
     */
    private void setStatusBarColor() {
        if (VersionUtils.isAndroidL()) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.title_bg));
        }
    }

    private void initView() {
        imageView = findViewById(R.id.process_img);
        btnConfirm = findViewById(R.id.btn_confirm);
        btnBack = findViewById(R.id.btn_back);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView.getDrawable() != null) {
                    btnConfirm.setEnabled(false);
                    confirm(imageView.clipImage());
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageView.setRatio(cropRatio);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && requestCode == mRequestCode) {
            ArrayList<String> images = data.getStringArrayListExtra(Selector.SELECT_RESULT);
            isCameraImage = data.getBooleanExtra(Selector.IS_CAMERA_IMAGE, false);
            Bitmap bitmap = ImageUtil.decodeSampledBitmapFromFile(this, images.get(0), 720, 1080);
            if (bitmap != null) {
                imageView.setBitmapData(bitmap);
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    private void confirm(Bitmap bitmap) {
        String imagePath = null;
        if (bitmap != null) {
            String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.getDefault())).toString();
            String path = ImageUtil.getImageCacheDir(this);
            imagePath = ImageUtil.saveImage(bitmap, path, name);
            bitmap.recycle();
            bitmap = null;
        }

        if (StringUtils.isNotEmptyString(imagePath)) {
            ArrayList<String> selectImages = new ArrayList<>();
            selectImages.add(imagePath);
            Intent intent = new Intent();
            intent.putStringArrayListExtra(Selector.SELECT_RESULT, selectImages);
            intent.putExtra(Selector.IS_CAMERA_IMAGE, isCameraImage);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    /**
     * 启动图片选择器
     *
     * @param activity
     * @param requestCode
     * @param config
     */
    public static void openActivity(Activity activity, int requestCode, RequestConfig config) {
        Intent intent = new Intent(activity, ClipImageActivity.class);
        intent.putExtra(Selector.KEY_CONFIG, config);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 启动图片选择器
     *
     * @param fragment
     * @param requestCode
     * @param config
     */
    public static void openActivity(Fragment fragment, int requestCode, RequestConfig config) {
        Intent intent = new Intent(fragment.getActivity(), ClipImageActivity.class);
        intent.putExtra(Selector.KEY_CONFIG, config);
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 启动图片选择器
     *
     * @param fragment
     * @param requestCode
     * @param config
     */
    public static void openActivity(android.app.Fragment fragment, int requestCode, RequestConfig config) {
        Intent intent = new Intent(fragment.getActivity(), ClipImageActivity.class);
        intent.putExtra(Selector.KEY_CONFIG, config);
        fragment.startActivityForResult(intent, requestCode);
    }
}
