package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class BaseButtonsFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private static final int[] mButtonIds = {
            R.id.button_0, R.id.button_1, R.id.button_2,
            R.id.button_3, R.id.button_4, R.id.button_5,
            R.id.button_6, R.id.button_7, R.id.button_8,
            R.id.button_9, R.id.button_plus, R.id.button_minus,
            R.id.button_multiply, R.id.button_divide, R.id.button_equals
    };

    public static BaseButtonsFragment newInstance() {
        BaseButtonsFragment fragment = new BaseButtonsFragment();
        return fragment;
    }

    public BaseButtonsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_base_buttons, container, false);

        for (int id : mButtonIds) {
            Button b = (Button) v.findViewById(id);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onButtonClicked(view.getId());
                }
            });
        }

        return  v;
    }

    public void onButtonClicked(int id) {
        if (mListener != null) {
            mListener.onButtonClicked(id);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        public void onButtonClicked(int id);
    }

}
