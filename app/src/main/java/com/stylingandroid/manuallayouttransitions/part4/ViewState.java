package com.stylingandroid.manuallayouttransitions.part4;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

public final class ViewState {
    private final int top;
    private final int absoluteTop;
    private final int absoluteLeft;
    private final int visibility;
    private final Bitmap viewBitmap;

    public static ViewState ofView(View view) {
        int top = 0;
        int absoluteTop = 0;
        int absoluteLeft = 0;
        int visibility = View.GONE;
        Bitmap viewBitmap = null;
        if (view != null) {
            top = view.getTop();
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            absoluteLeft = location[0];
            absoluteTop = location[1];
            visibility = view.getVisibility();
            if (visibility == View.VISIBLE) {
                viewBitmap = getBitmap(view);
            }
        }
        return new ViewState(top, absoluteLeft, absoluteTop, visibility, viewBitmap);
    }

    private static Bitmap getBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private ViewState(int top, int absoluteLeft, int absoluteTop, int visibility, Bitmap viewBitmap) {
        this.top = top;
        this.absoluteLeft = absoluteLeft;
        this.absoluteTop = absoluteTop;
        this.visibility = visibility;
        this.viewBitmap = viewBitmap;
    }

    public boolean hasMovedVertically(View view) {
        return view.getTop() != top;
    }

    public boolean hasAppeared(View view) {
        if (view == null) {
            return false;
        }
        int newVisibility = view.getVisibility();
        return viewBitmap == null || visibility != newVisibility && newVisibility == View.VISIBLE;
    }

    public boolean hasDisappeared(View view) {
        if (view == null) {
            return true;
        }
        int newVisibility = view.getVisibility();
        return visibility != newVisibility && newVisibility != View.VISIBLE;
    }

    public int getY() {
        return top;
    }

    public int getAbsoluteX() {
        return absoluteLeft;
    }

    public int getAbsoluteY() {
        return absoluteTop;
    }

    public Bitmap getViewBitmap() {
        return viewBitmap;
    }
}
