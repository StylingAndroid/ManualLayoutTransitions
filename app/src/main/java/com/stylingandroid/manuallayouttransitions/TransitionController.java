package com.stylingandroid.manuallayouttransitions;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.ref.WeakReference;

public abstract class TransitionController implements View.OnFocusChangeListener {
    private final WeakReference<Activity> activityWeakReference;
    private final AnimatorBuilder animatorBuilder;

    protected TransitionController(WeakReference<Activity> activityWeakReference, @NonNull AnimatorBuilder animatorBuilder) {
        this.activityWeakReference = activityWeakReference;
        this.animatorBuilder = animatorBuilder;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Activity mainActivity = activityWeakReference.get();
        if (mainActivity != null) {
            if (hasFocus) {
                enterInputMode(mainActivity);
            } else {
                exitInputMode(mainActivity);
            }
        }
    }

    protected AnimatorBuilder getAnimatorBuilder() {
        return animatorBuilder;
    }

    protected abstract void enterInputMode(Activity mainActivity);

    protected abstract void exitInputMode(Activity mainActivity);

    protected void closeIme(View view) {
        Activity mainActivity = activityWeakReference.get();
        if (mainActivity != null) {
            InputMethodManager imm = (InputMethodManager) mainActivity.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected class ImeCloseListener extends AnimatorListenerAdapter {
        private final View view;

        public ImeCloseListener(View view) {
            this.view = view;
        }

        @Override
        public void onAnimationEnd(@NonNull Animator animation) {
            super.onAnimationEnd(animation);
            closeIme(view);
        }

    }
}
