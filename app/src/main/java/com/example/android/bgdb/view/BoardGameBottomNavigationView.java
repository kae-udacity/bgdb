package com.example.android.bgdb.view;

import android.content.Context;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;

import java.lang.reflect.Field;

/**
 * Displays bottom navigation icon in centre of layout when there is no title text.
 */
public class BoardGameBottomNavigationView extends BottomNavigationView {

    public BoardGameBottomNavigationView(Context context) {
        super(context);
    }

    public BoardGameBottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        centreMenuIcon();
    }

    public BoardGameBottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void centreMenuIcon() {
        BottomNavigationMenuView menuView = getBottomMenuView();

        if (menuView != null) {
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView menuItemView = (BottomNavigationItemView) menuView.getChildAt(i);

                AppCompatImageView icon = (AppCompatImageView) menuItemView.getChildAt(0);

                FrameLayout.LayoutParams params = (LayoutParams) icon.getLayoutParams();
                params.gravity = Gravity.CENTER;

                //noinspection RestrictedApi
                menuItemView.setShiftingMode(true);
            }
        }
    }

    private BottomNavigationMenuView getBottomMenuView() {
        Object menuView = null;
        try {
            Field field = BottomNavigationView.class.getDeclaredField("mMenuView");
            field.setAccessible(true);
            menuView = field.get(this);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return (BottomNavigationMenuView) menuView;
    }
}
