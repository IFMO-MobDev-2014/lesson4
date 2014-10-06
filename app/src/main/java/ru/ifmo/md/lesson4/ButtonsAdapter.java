package ru.ifmo.md.lesson4;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created y dimatomp on 05.10.14.
 */
public class ButtonsAdapter extends FragmentPagerAdapter {
    final ButtonsFragment[] fragments;

    public ButtonsAdapter(FragmentActivity context, int... buttonArrays) {
        super(context.getSupportFragmentManager());
        this.fragments = new ButtonsFragment[buttonArrays.length];
        for (int i = 0; i < fragments.length; i++) {
            this.fragments[i] = new ButtonsFragment();
            this.fragments[i].setButtonNames(buttonArrays[i]);
        }
    }

    @Override
    public Fragment getItem(int i) {
        return fragments[i];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
