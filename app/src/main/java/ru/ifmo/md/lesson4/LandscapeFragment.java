package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LandscapeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public static LandscapeFragment newInstance() {
        LandscapeFragment fragment = new LandscapeFragment();
        return fragment;
    }

    public LandscapeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_landscape, container, false);
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
