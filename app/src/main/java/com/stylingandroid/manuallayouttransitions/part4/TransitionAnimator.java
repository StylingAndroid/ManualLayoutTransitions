package com.stylingandroid.manuallayouttransitions.part4;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.stylingandroid.manuallayouttransitions.AnimatorBuilder;

import java.util.ArrayList;
import java.util.List;

public class TransitionAnimator implements ViewTreeObserver.OnPreDrawListener {
    private final ViewGroup parent;
    private final SparseArray<ViewState> startStates;
    private final AnimatorBuilder animatorBuilder;

    private ViewGroup viewOverlay;

    public static void begin(ViewGroup parent, @IdRes int... viewIds) {
        SparseArray<ViewState> startStates = buildViewStates(parent, viewIds);
        AnimatorBuilder animatorBuilder = AnimatorBuilder.newInstance(parent.getContext());
        TransitionAnimator transitionAnimator = new TransitionAnimator(animatorBuilder, parent, startStates);
        ViewTreeObserver viewTreeObserver = parent.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(transitionAnimator);
    }

    TransitionAnimator(AnimatorBuilder animatorBuilder, ViewGroup parent, SparseArray<ViewState> startStates) {
        this.animatorBuilder = animatorBuilder;
        this.parent = parent;
        this.startStates = startStates;
    }

    private static SparseArray<ViewState> buildViewStates(ViewGroup parent, @IdRes int... viewIds) {
        SparseArray<ViewState> viewStates = new SparseArray<>();
        for (int viewId : viewIds) {
            View view = parent.findViewById(viewId);
            viewStates.put(viewId, ViewState.ofView(view));
        }
        return viewStates;
    }

    @Override
    public boolean onPreDraw() {
        ViewTreeObserver viewTreeObserver = parent.getViewTreeObserver();
        viewTreeObserver.removeOnPreDrawListener(this);
        Context context = parent.getContext();
        viewOverlay = new FrameLayout(context);
        parent.addView(viewOverlay);
        ViewGroup.LayoutParams layoutParams = viewOverlay.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        viewOverlay.setLayoutParams(layoutParams);
        SparseArray<View> views = new SparseArray<>();
        for (int i = 0; i < startStates.size(); i++) {
            int resId = startStates.keyAt(i);
            View view = parent.findViewById(resId);
            if (view == null) {
                ViewState startState = startStates.get(resId);
                view = addOverlayView(startState);
            }
            views.put(resId, view);
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
        animatorSet.addListener(new AnimationCompletedListener());
        return animatorSet;
    }

    private Animator buildViewAnimator(final View view, ViewState startState) {
        Animator animator = null;
        if (startState == null || startState.hasAppeared(view)) {
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

    private View addOverlayView(ViewState viewState) {
        Context context = viewOverlay.getContext();
        ImageView imageView = new ImageView(context);
        int[] overlayLocation = new int[2];
        viewOverlay.getLocationOnScreen(overlayLocation);
        imageView.setX(viewState.getAbsoluteX() - overlayLocation[0]);
        imageView.setY(viewState.getAbsoluteY() - overlayLocation[1]);
        Bitmap viewBitmap = viewState.getViewBitmap();
        imageView.setAdjustViewBounds(true);
        imageView.setImageBitmap(viewBitmap);
        imageView.setVisibility(View.INVISIBLE);
        viewOverlay.addView(imageView);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        imageView.setLayoutParams(layoutParams);
        return imageView;
    }

    private class AnimationCompletedListener extends AnimatorListenerAdapter {
        @Override
        public void onAnimationEnd(@NonNull Animator animation) {
            viewOverlay.removeAllViews();
        }
    }
}
