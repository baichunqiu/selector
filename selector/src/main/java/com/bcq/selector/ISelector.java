package com.bcq.selector;

/**
 * 选择器接口
 * 定义常量字段
 */
public interface ISelector {
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

}
