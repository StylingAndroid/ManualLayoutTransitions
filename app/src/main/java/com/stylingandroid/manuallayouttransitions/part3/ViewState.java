package com.stylingandroid.manuallayouttransitions.part3;

import android.view.View;

public class ViewState {
    private final int top;
    private final int visibility;

    public ViewState(int top, int visibility) {
        this.top = top;
        this.visibility = visibility;
    }

    public static ViewState ofView(View view) {
        int top = view.getTop();
        int visibility = view.getVisibility();
        return new ViewState(top, visibility);
    }

    public boolean hasMovedVertically(View view) {
        return view.getTop() != top;
    }

    public boolean hasAppeared(View view) {
        int newVisibility = view.getVisibility();
        return visibility != newVisibility && newVisibility == View.VISIBLE;
    }

    public boolean hasDisappeared(View view) {
        int newVisibility = view.getVisibility();
        return visibility != newVisibility && newVisibility != View.VISIBLE;
    }

    public int getY() {
        return top;
    }
}
