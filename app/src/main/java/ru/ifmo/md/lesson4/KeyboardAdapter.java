package ru.ifmo.md.lesson4;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class KeyboardAdapter extends BaseAdapter {
    private Context mContext;
    private final int unitWidth, unitHeight;

    public KeyboardAdapter(Context c, int w, int h) {
        mContext = c;
        unitHeight = h;
        unitWidth = w;
    }

    public int getCount() {
        return keys.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view;
        if (convertView == null) {
            view = new TextView(mContext);
            view.setLayoutParams(new GridView.LayoutParams(unitWidth, unitHeight));
            view.setPadding(8, 8, 8, 8);
        } else {
            view = (TextView) convertView;
        }

        view.setText(keys[position]);
        view.setTextColor(mContext.getResources().getColor(R.color.holo_blue_dark));
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
        view.setGravity(Gravity.CENTER);
        return view;
    }

    public static String[] keys = {
        "7", "8", "9", "*", "←",
        "4", "5", "6", "/", "C",
        "1", "2", "3", "+", ".",
        "(", "0", ")", "-", "( )"
    };
}