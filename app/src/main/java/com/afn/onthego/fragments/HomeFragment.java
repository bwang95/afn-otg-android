package com.afn.onthego.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;

import com.afn.onthego.R;
import com.afn.onthego.adapters.HomeAdapter;
import com.afn.onthego.storage.KeyList;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends MainFragment {
    private OnFragmentInteractionListener mListener;
    private HomeAdapter navAdapter;

    private View.OnClickListener aboutListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mListener != null)
                mListener.onHomeFragmentSelection(KeyList.Navigation.ABOUT);
        }
    };

    private ListView.OnItemClickListener navigationListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String item = navAdapter.getItem(position).toString();

            String ret = "";

            switch (item) {
                case "Learn":
                    ret = KeyList.Navigation.LEARN;
                    break;
                case "Connect":
                    ret = KeyList.Navigation.CONNECT;
                    break;
                case "Volunteer":
                    ret = KeyList.Navigation.VOLUNTEER;
                    break;
                case "Donate":
                    ret = KeyList.Navigation.DONATE;
                    break;
            }

            if(mListener != null)
                mListener.onHomeFragmentSelection(ret);

        }
    };

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button about = (Button) view.findViewById(R.id.b_home_about);
        GridView fragments = (GridView) view.findViewById(R.id.gv_home_fragment_grid);

        navAdapter = new HomeAdapter(getActivity());
        fragments.setAdapter(navAdapter);

        fragments.setOnItemClickListener(navigationListener);
        about.setOnClickListener(aboutListener);

        return view;
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
        public void onHomeFragmentSelection(String key);
    }

}
