package com.daking.sports.util;

import android.content.Context;
import android.content.Intent;

import com.daking.sports.R;
import com.daking.sports.application.ActivityManager;

/**
 * Created by Steven on 2017/3/29. 动画效果
 */

public class AnimationUtil {
    public Context mContext;
    public enum ActivityAnimation {
        /**
         * FADE_HOLD 淡入淡出
         * SCALE_ALPHA 缩放
         * SCALE_ROTATE_ALPHA 旋转缩放
         * SCALE_TRANSLATE_ROTATE_ALPHA 缩放移动旋转
         * SCALE_TRANSLATE_ALPHA 缩放移动
         * HYPERSPACE
         * PUSH_LEFT 左推
         * PUSH_RIGHT 右推
         * PUSH_UP 上推
         * SLIDE_LEFT 左滑
         * WAVE_SCALE_ALPHA
         * ZOOM_ENTER 放大进入
         * SLIDE_UP 上滑
         */
        FADE_HOLD, SCALE_ALPHA, SCALE_ROTATE_ALPHA, SCALE_TRANSLATE_ROTATE_ALPHA, SCALE_TRANSLATE_ALPHA, HYPERSPACE, PUSH_LEFT, PUSH_RIGHT, PUSH_UP, SLIDE_LEFT, WAVE_SCALE_ALPHA, ZOOM_ENTER, SLIDE_UP
    }

    /**
     * 跳转加动画
     *
     * @param cls
     * @param animationId
     */
    protected void startActivity(Class<?> cls, ActivityAnimation animationId) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        mContext.startActivity(intent);
        animation(animationId);
    }

    /**
     * 跳转加动画
     *
     * @param intent
     * @param animationId
     */
    protected void startActivity(Intent intent, ActivityAnimation animationId) {
        mContext.startActivity(intent);
        animation(animationId);
    }

    /**
     * 消亡动画
     *
     * @param animationId
     */
    public void finishActivity(ActivityAnimation animationId) {
        ActivityManager.getInstance().getCurrentActivity().finish();
        animation(animationId);
    }

    /**
     * 动画类型
     *
     * @param animationId
     */
    private void animation(ActivityAnimation animationId) {
        switch (animationId) {
            case FADE_HOLD:
                ActivityManager.getInstance().getCurrentActivity().overridePendingTransition(R.anim.fade, R.anim.hold);
                break;
            case SCALE_ALPHA:
                ActivityManager.getInstance().getCurrentActivity().overridePendingTransition(R.anim.my_scale_action, R.anim.my_alpha_action);
                break;
            case SCALE_ROTATE_ALPHA:
                ActivityManager.getInstance().getCurrentActivity().overridePendingTransition(R.anim.scale_rotate, R.anim.my_alpha_action);
                break;
            case SCALE_TRANSLATE_ROTATE_ALPHA:
                ActivityManager.getInstance().getCurrentActivity().overridePendingTransition(R.anim.scale_translate_rotate, R.anim.my_alpha_action);
                break;
            case SCALE_TRANSLATE_ALPHA:
                ActivityManager.getInstance().getCurrentActivity().overridePendingTransition(R.anim.scale_translate, R.anim.my_alpha_action);
                break;
            case HYPERSPACE:
                ActivityManager.getInstance().getCurrentActivity().overridePendingTransition(R.anim.hyperspace_in, R.anim.hyperspace_out);
                break;
            case PUSH_LEFT:
                ActivityManager.getInstance().getCurrentActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case PUSH_RIGHT:
                ActivityManager.getInstance().getCurrentActivity().overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);
                break;
            case PUSH_UP:
                ActivityManager.getInstance().getCurrentActivity(). overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                break;
            case SLIDE_LEFT:
                ActivityManager.getInstance().getCurrentActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                break;
            case WAVE_SCALE_ALPHA:
                ActivityManager.getInstance().getCurrentActivity().overridePendingTransition(R.anim.wave_scale, R.anim.my_alpha_action);
                break;
            case ZOOM_ENTER:
                ActivityManager.getInstance().getCurrentActivity().overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
                break;
            case SLIDE_UP:
                ActivityManager.getInstance().getCurrentActivity().overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
                break;
        }
    }

}
