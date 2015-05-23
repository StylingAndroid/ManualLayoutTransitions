package com.stylingandroid.manuallayouttransitions.part3;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.stylingandroid.manuallayouttransitions.AnimatorBuilder;

import java.util.ArrayList;
import java.util.List;

public final class TransitionAnimator implements ViewTreeObserver.OnPreDrawListener {
    private final ViewGroup parent;
    private final SparseArray<ViewState> startStates;
    private final AnimatorBuilder animatorBuilder;

    public static void begin(ViewGroup parent, View... views) {
        SparseArray<ViewState> startStates = buildViewStates(views);
        AnimatorBuilder animatorBuilder = AnimatorBuilder.newInstance(parent.getContext());
        TransitionAnimator transitionAnimator = new TransitionAnimator(animatorBuilder, parent, startStates);
        ViewTreeObserver viewTreeObserver = parent.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(transitionAnimator);
    }

    private TransitionAnimator(AnimatorBuilder animatorBuilder, ViewGroup parent, SparseArray<ViewState> startStates) {
        this.animatorBuilder = animatorBuilder;
        this.parent = parent;
        this.startStates = startStates;
    }

    private static SparseArray<ViewState> buildViewStates(View... views) {
        SparseArray<ViewState> viewStates = new SparseArray<>();
        for (View view : views) {
            viewStates.put(view.getId(), ViewState.ofView(view));
        }
        return viewStates;
    }

    @Override
    public boolean onPreDraw() {
        ViewTreeObserver viewTreeObserver = parent.getViewTreeObserver();
        viewTreeObserver.removeOnPreDrawListener(this);
        SparseArray<View> views = new SparseArray<>();
        for (int i = 0; i < startStates.size(); i++) {
            int resId = startStates.keyAt(i);
            View view = parent.findViewById(resId);
            views.put(view.getId(), view);
        }
        Animator animator = buildAnimator(views);
        animator.start();
        return false;
    }

    private Animator buildAnimator(SparseArray<View> views) {
        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> animators = new ArrayList<>();
        for (int i = 0; i < views.size(); i++) {
            int resId = views.keyAt(i);
            ViewState startState = startStates.get(resId);
            View view = views.get(resId);
            animators.add(buildViewAnimator(view, startState));
        }
        animatorSet.playTogether(animators);
        return animatorSet;
    }

    private Animator buildViewAnimator(final View view, ViewState startState) {
        Animator animator = null;
        if (startState.hasAppeared(view)) {
            animator = animatorBuilder.buildShowAnimator(view);
        } else if (startState.hasDisappeared(view)) {
            final int visibility = view.getVisibility();
            view.setVisibility(View.VISIBLE);
            animator = animatorBuilder.buildHideAnimator(view);
            animator.addListener(
                    new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(@NonNull Animator animation) {
                            super.onAnimationEnd(animation);
                            view.setVisibility(visibility);
                        }
                    });
        } else if (startState.hasMovedVertically(view)) {
            int startY = startState.getY();
            int endY = view.getTop();
            animator = animatorBuilder.buildTranslationYAnimator(view, startY - endY, 0);
        }

        return animator;
    }
}
