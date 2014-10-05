package ru.ifmo.md.lesson4;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class BaseButtonsFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

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

        for (int id : CalculatorActivity.baseButtons) {
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
