package com.afn.onthego.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.afn.onthego.R;
import com.afn.onthego.activities.ConnectActivity;
import com.afn.onthego.activities.WebActivity;
import com.afn.onthego.storage.KeyList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DonateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DonateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DonateFragment extends MainFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private LinearLayout donateLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DonateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DonateFragment newInstance() {
        DonateFragment fragment = new DonateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public DonateFragment() {
        // Required empty public constructor
    }

    private View.OnClickListener donateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW);
            webIntent.setData(Uri.parse(KeyList.URL.DONATE_URL));
            startActivity(webIntent);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donate, container, false);
        donateLayout = (LinearLayout) view.findViewById(R.id.ll_donate_button);
        donateLayout.setOnClickListener(donateListener);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
