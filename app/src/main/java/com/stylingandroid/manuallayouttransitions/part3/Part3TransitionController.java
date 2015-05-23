package com.stylingandroid.manuallayouttransitions.part3;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.stylingandroid.manuallayouttransitions.AnimatorBuilder;
import com.stylingandroid.manuallayouttransitions.R;
import com.stylingandroid.manuallayouttransitions.TransitionController;

import java.lang.ref.WeakReference;

public class Part3TransitionController extends TransitionController {

    Part3TransitionController(WeakReference<Activity> activityWeakReference, AnimatorBuilder animatorBuilder) {
        super(activityWeakReference, animatorBuilder);
    }

    public static TransitionController newInstance(Activity activity) {
        WeakReference<Activity> activityWeakReference = new WeakReference<>(activity);
        AnimatorBuilder animatorBuilder = AnimatorBuilder.newInstance(activity);
        return new Part3TransitionController(activityWeakReference, animatorBuilder);
    }

    @Override
    protected void enterInputMode(Activity activity) {
        createTransitionAnimator(activity);
        activity.setContentView(R.layout.activity_part2_input);
    }

    @Override
    protected void exitInputMode(Activity activity) {
        createTransitionAnimator(activity);
        activity.setContentView(R.layout.activity_part2);
    }

    private void createTransitionAnimator(Activity activity) {
        ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
        View inputView = parent.findViewById(R.id.input_view);
        View inputDone = parent.findViewById(R.id.input_done);
        View translation = parent.findViewById(R.id.translation);

        TransitionAnimator.begin(parent, inputView, inputDone, translation);
    }
}
