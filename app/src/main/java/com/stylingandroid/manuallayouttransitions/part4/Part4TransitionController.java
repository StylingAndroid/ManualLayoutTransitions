package com.stylingandroid.manuallayouttransitions.part4;

import android.app.Activity;
import android.view.ViewGroup;

import com.stylingandroid.manuallayouttransitions.AnimatorBuilder;
import com.stylingandroid.manuallayouttransitions.R;
import com.stylingandroid.manuallayouttransitions.TransitionController;

import java.lang.ref.WeakReference;

public class Part4TransitionController extends TransitionController {
    private static final int[] VIEW_IDS = {
            R.id.input_view, R.id.input_done, R.id.translation
    };

    Part4TransitionController(WeakReference<Activity> activityWeakReference, AnimatorBuilder animatorBuilder) {
        super(activityWeakReference, animatorBuilder);
    }

    public static TransitionController newInstance(Activity activity) {
        WeakReference<Activity> activityWeakReference = new WeakReference<>(activity);
        AnimatorBuilder animatorBuilder = AnimatorBuilder.newInstance(activity);
        return new Part4TransitionController(activityWeakReference, animatorBuilder);
    }

    @Override
    protected void enterInputMode(Activity activity) {
        createTransitionAnimator(activity);
        activity.setContentView(R.layout.activity_part4_input);
    }

    @Override
    protected void exitInputMode(Activity activity) {
        createTransitionAnimator(activity);
        activity.setContentView(R.layout.activity_part4);
    }

    private void createTransitionAnimator(Activity activity) {
        ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);

        TransitionAnimator.begin(parent, VIEW_IDS);
    }
}
