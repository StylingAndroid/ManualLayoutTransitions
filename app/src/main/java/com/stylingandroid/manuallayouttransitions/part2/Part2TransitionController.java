package com.stylingandroid.manuallayouttransitions.part2;

import android.app.Activity;

import com.stylingandroid.manuallayouttransitions.AnimatorBuilder;
import com.stylingandroid.manuallayouttransitions.R;
import com.stylingandroid.manuallayouttransitions.TransitionController;

import java.lang.ref.WeakReference;

public class Part2TransitionController extends TransitionController {

    Part2TransitionController(WeakReference<Activity> activityWeakReference, AnimatorBuilder animatorBuilder) {
        super(activityWeakReference, animatorBuilder);
    }

    public static TransitionController newInstance(Activity activity) {
        WeakReference<Activity> activityWeakReference = new WeakReference<>(activity);
        AnimatorBuilder animatorBuilder = AnimatorBuilder.newInstance(activity);
        return new Part2TransitionController(activityWeakReference, animatorBuilder);
    }

    @Override
    protected void enterInputMode(Activity activity) {
        activity.setContentView(R.layout.activity_part2_input);
    }

    @Override
    protected void exitInputMode(Activity activity) {
        activity.setContentView(R.layout.activity_part2);
    }
}
