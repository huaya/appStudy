package com.example.appstduy;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class DetailsTransition extends Transition {
    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        // 记录初始状态
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        // 记录结束状态
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        // 创建动画
        AnimatorSet animatorSet = new AnimatorSet();
        ImageView imageView = sceneRoot.findViewById(R.id.image_view);
        ScaleAnimation scaleAnimation = new ScaleAnimation(sceneRoot.getContext(), null);
        // 添加动画效果，如改变大小、渐变等
        return animatorSet;
    }

}