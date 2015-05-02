package com.afn.onthego.fragments;

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
import com.afn.onthego.storage.Storage;
import com.afn.onthego.util.Links;

public class VolunteerFragment extends Fragment {

    private View.OnClickListener signInListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().onBackPressed();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(Storage.getInstance(getActivity()).getLinks().getURLS().get(Links.VOLUNTEER_SIGN_IN)));
            startActivity(i);
//            Intent webIntent = new Intent(getActivity(), WebActivity.class);
//            webIntent.putExtra(KeyList.ActivityParams.KEY_URL, KeyList.URL.VOLUNTEER_SIGN_IN);
//            webIntent.putExtra(KeyList.ActivityParams.KEY_TITLE, "Sign In");
//            startActivity(webIntent);
        }
    };

    private View.OnClickListener signUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().onBackPressed();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(Storage.getInstance(getActivity()).getLinks().getURLS().get(Links.VOLUNTEER_SIGN_UP)));
            startActivity(i);
//            Intent webIntent = new Intent(getActivity(), WebActivity.class);
//            webIntent.putExtra(KeyList.ActivityParams.KEY_URL, KeyList.URL.VOLUNTEER_SIGN_UP);
//            webIntent.putExtra(KeyList.ActivityParams.KEY_TITLE, "Sign Up");
//            webIntent.putExtra(KeyList.ActivityParams.KEY_PURGE_COOKIES, true);
//            startActivity(webIntent);
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

}
