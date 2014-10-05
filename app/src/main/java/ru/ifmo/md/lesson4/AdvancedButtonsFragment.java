package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class AdvancedButtonsFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    public static AdvancedButtonsFragment newInstance() {
        return new AdvancedButtonsFragment();
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

        View v = inflater.inflate(R.layout.fragment_advanced_buttons, container, false);

        for (int id : CalculatorActivity.advancedButtons) {
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
