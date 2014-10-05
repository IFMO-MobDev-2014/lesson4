package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class AdvancedButtonsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public static AdvancedButtonsFragment newInstance() {
        AdvancedButtonsFragment fragment = new AdvancedButtonsFragment();
        return fragment;
    }
    public AdvancedButtonsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_advanced_buttons, container, false);
    }

    public void onButtonPressed(int id) {
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
