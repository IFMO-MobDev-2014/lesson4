package ru.ifmo.md.lesson4;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import ru.ifmo.mb.lesson4.R;

/**
 * Created by Svet on 04.10.2014.
 */
public class ButtonAdapter extends BaseAdapter {
    private Context context;
    private int count = 20;
    private int height = 0;
    private String [] values = { "(", ")", "C", "Back",
                                "1", "2", "3", "+",
                                "3", "4", "5", "-",
                                "7", "8", "9", "*",
                                "0", ".", "=", "/"};

    public ButtonAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() { return count; }

    @Override
    public Object getItem(int a) { return null; }

    @Override
    public long getItemId(int a) { return 0; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.button, null);
        TextView b = (TextView) view.findViewById(R.id.key);
        b.setText(values[position]);
        return view;
    }
}
