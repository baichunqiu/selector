package com.bcq.selector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.bcq.selector.imageselector.ClipImageActivity;
import com.bcq.selector.imageselector.ImageSelectorActivity;
import com.bcq.selector.imageselector.entry.RequestConfig;

import java.util.ArrayList;

/**
 * 选择器接口
 */
public interface Selector {
    /**
     * 图片选择的结果
     */
    String SELECT_RESULT = "select_result";

    /**
     * 是否是来自于相机拍照的图片，
     * 只有本次调用相机拍出来的照片，返回时才为true。
     * 当为true时，图片返回当结果有且只有一张图片。
     */
    String IS_CAMERA_IMAGE = "is_camera_image";

    String KEY_CONFIG = "key_config";

    //最大的图片选择数
    String MAX_SELECT_COUNT = "max_select_count";
    //是否单选
    String IS_SINGLE = "is_single";
    //初始位置
    String POSITION = "position";

    String IS_CONFIRM = "is_confirm";

    int RESULT_CODE = 0x00000012;

    /**
     * 预加载图片
     *
     * @param context
     */
    void preload(Context context);

    /**
     * 清空缓存
     */
    void clearCache(Context context);

    SelectorBuilder builder();

    interface SelectorBuilder {

        /**
         * 是否单选
         *
         * @param isSingle
         * @return
         */
        SelectorBuilder setSingle(boolean isSingle);

        /**
         * 是否可以点击预览，默认为true
         *
         * @param preview
         * @return
         */
        SelectorBuilder enablePreview(boolean preview);

        /**
         * 是否使用拍照功能。 默认为true
         *
         * @param camera
         * @return
         */
        public SelectorBuilder enableCamera(boolean camera);

        /**
         * 图片的最大选择数量，小于等于0时，不限数量，isSingle为false时才有用。
         *
         * @param maxSelectCount
         * @return
         */
        SelectorBuilder setMaxSelectCount(int maxSelectCount);

        /**
         * 接收从外面传进来的已选择的图片列表。当用户原来已经有选择过图片，现在重新打开
         * 选择器，允许用户把先前选过的图片传进来，并把这些图片默认为选中状态。
         *
         * @param selected
         * @return
         */
        SelectorBuilder selected(ArrayList<String> selected);

        /**
         * 打开相册
         *
         * @param activity
         * @param requestCode
         */
        void open(UIOrigin activity, int requestCode);

    }

    /**
     * ui起点 带有startActivityForResult方法的实体
     * Activity和Fragment的抽象接口
     */
    interface UIOrigin {
        void startActivityForResult(Intent intent, int requestCode);
    }

}
