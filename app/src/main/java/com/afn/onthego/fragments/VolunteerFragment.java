package com.afn.onthego.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.afn.onthego.R;
import com.afn.onthego.activities.WebActivity;
import com.afn.onthego.storage.KeyList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VolunteerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VolunteerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VolunteerFragment extends MainFragment {

    private OnFragmentInteractionListener mListener;

    private View.OnClickListener signInListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().onBackPressed();
            Intent webIntent = new Intent(getActivity(), WebActivity.class);
            webIntent.putExtra(KeyList.ActivityParams.KEY_URL, KeyList.URL.VOLUNTEER_SIGN_IN);
            webIntent.putExtra(KeyList.ActivityParams.KEY_TITLE, "Sign In");
            startActivity(webIntent);
        }
    };

    private View.OnClickListener signUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().onBackPressed();
            Intent webIntent = new Intent(getActivity(), WebActivity.class);
            webIntent.putExtra(KeyList.ActivityParams.KEY_URL, KeyList.URL.VOLUNTEER_SIGN_UP);
            webIntent.putExtra(KeyList.ActivityParams.KEY_TITLE, "Sign Up");
            webIntent.putExtra(KeyList.ActivityParams.KEY_PURGE_COOKIES, true);
            startActivity(webIntent);
        }
    };

    private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().onBackPressed();
        }
    };

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment VolunteerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VolunteerFragment newInstance() {
        VolunteerFragment fragment = new VolunteerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public VolunteerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_volunteer, container, false);

        Button signIn = (Button) view.findViewById(R.id.b_volunteer_sign_in);
        Button signUp = (Button) view.findViewById(R.id.b_volunteer_sign_up);
        Button cancel = (Button) view.findViewById(R.id.b_volunteer_cancel);
        FrameLayout baseLayout = (FrameLayout) view.findViewById(R.id.fl_volunteer_main);

        signIn.setOnClickListener(signInListener);
        signUp.setOnClickListener(signUpListener);
        cancel.setOnClickListener(cancelListener);
        baseLayout.setOnClickListener(cancelListener);

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
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
