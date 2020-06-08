package com.bcq.selector;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.bcq.selector.entry.RequestConfig;
import com.bcq.selector.model.ImageModel;

import java.util.ArrayList;

/**
 * 选择器
 */
public class Selector implements ISelector{

    /**
     * 预加载图片
     * @param context
     */
    public static void preload(Context context) {
        ImageModel.preloadAndRegisterContentObserver(context);
    }

    /**
     * 清空缓存
     */
    public static void clearCache(Context context) {
        ImageModel.clearCache(context);
    }

    public static ImageSelectorBuilder builder() {
        return new ImageSelectorBuilder();
    }

    public static class ImageSelectorBuilder {

        private RequestConfig config;

        private ImageSelectorBuilder() {
            config = new RequestConfig();
        }

        /**
         * 是否使用图片剪切功能。默认false。如果使用了图片剪切功能，相册只能单选。
         *
         * @param isCrop
         * @return
         */
        public ImageSelectorBuilder setCrop(boolean isCrop) {
            config.isCrop = isCrop;
            return this;
        }

        /**
         * 图片剪切的宽高比，宽固定为手机屏幕的宽。
         *
         * @param ratio
         * @return
         */
        public ImageSelectorBuilder setCropRatio(float ratio) {
            config.cropRatio = ratio;
            return this;
        }

        /**
         * 是否单选
         *
         * @param isSingle
         * @return
         */
        public ImageSelectorBuilder setSingle(boolean isSingle) {
            config.isSingle = isSingle;
            return this;
        }

        /**
         * 是否可以点击放大图片查看，默认为true
         *
         * @param isViewImage
         * @return
         * @deprecated 请使用canPreview(boolean canPreview);
         */
        @Deprecated
        public ImageSelectorBuilder setViewImage(boolean isViewImage) {
            config.canPreview = isViewImage;
            return this;
        }

        /**
         * 是否可以点击预览，默认为true
         *
         * @param canPreview
         * @return
         */
        public ImageSelectorBuilder canPreview(boolean canPreview) {
            config.canPreview = canPreview;
            return this;
        }

        /**
         * 是否使用拍照功能。
         *
         * @param useCamera 默认为true
         * @return
         */
        public ImageSelectorBuilder useCamera(boolean useCamera) {
            config.useCamera = useCamera;
            return this;
        }

        public ImageSelectorBuilder onlyTakePhoto(boolean onlyTakePhoto) {
            config.onlyTakePhoto = onlyTakePhoto;
            return this;
        }

        /**
         * 图片的最大选择数量，小于等于0时，不限数量，isSingle为false时才有用。
         *
         * @param maxSelectCount
         * @return
         */
        public ImageSelectorBuilder setMaxSelectCount(int maxSelectCount) {
            config.maxSelectCount = maxSelectCount;
            return this;
        }

        /**
         * 接收从外面传进来的已选择的图片列表。当用户原来已经有选择过图片，现在重新打开
         * 选择器，允许用户把先前选过的图片传进来，并把这些图片默认为选中状态。
         *
         * @param selected
         * @return
         */
        public ImageSelectorBuilder setSelected(ArrayList<String> selected) {
            config.selected = selected;
            return this;
        }

        /**
         * 打开相册
         *
         * @param activity
         * @param requestCode
         */
        public void start(Activity activity, int requestCode) {
            config.requestCode = requestCode;
            // 仅拍照，useCamera必须为true
            if (config.onlyTakePhoto) {
                config.useCamera = true;
            }
            if (config.isCrop) {
                ClipImageActivity.openActivity(activity, requestCode, config);
            } else {
                ImageSelectorActivity.openActivity(activity, requestCode, config);
            }
        }

        /**
         * 打开相册
         *
         * @param fragment
         * @param requestCode
         */
        public void start(Fragment fragment, int requestCode) {
            config.requestCode = requestCode;
            // 仅拍照，useCamera必须为true
            if (config.onlyTakePhoto) {
                config.useCamera = true;
            }
            if (config.isCrop) {
                ClipImageActivity.openActivity(fragment, requestCode, config);
            } else {
                ImageSelectorActivity.openActivity(fragment, requestCode, config);
            }
        }

        /**
         * 打开相册
         *
         * @param fragment
         * @param requestCode
         */
        public void start(android.app.Fragment fragment, int requestCode) {
            config.requestCode = requestCode;
            // 仅拍照，useCamera必须为true
            if (config.onlyTakePhoto) {
                config.useCamera = true;
            }
            if (config.isCrop) {
                ClipImageActivity.openActivity(fragment, requestCode, config);
            } else {
                ImageSelectorActivity.openActivity(fragment, requestCode, config);
            }
        }
    }

}
