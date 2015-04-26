package com.stylingandroid.manuallayouttransitions;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;

public class AnimatorBuilder {
    private static final String TRANSLATION_Y = "translationY";
    private static final String ALPHA = "alpha";

    private final int duration;

    public static AnimatorBuilder newInstance(Context context) {
        int duration = context.getResources().getInteger(android.R.integer.config_mediumAnimTime);
        return new AnimatorBuilder(duration);
    }

    AnimatorBuilder(int duration) {
        this.duration = duration;
    }

    public Animator buildTranslationYAnimator(View view, int startY, int endY) {
        Animator animator = ObjectAnimator.ofFloat(view, TRANSLATION_Y, startY, endY);
        animator.setDuration(duration);
        return animator;
    }

    public Animator buildShowAnimator(View view) {
        return buildAlphaAnimator(view, 0f, 1f);
    }

    public Animator buildHideAnimator(View view) {
        return buildAlphaAnimator(view, 1f, 0f);
    }

    public Animator buildAlphaAnimator(View view, float startAlpha, float endAlpha) {
        Animator animator = ObjectAnimator.ofFloat(view, ALPHA, startAlpha, endAlpha);
        animator.setDuration(duration);
        return animator;
    }
}
