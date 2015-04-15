package com.afn.onthego.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.afn.onthego.R;
import com.afn.onthego.storage.KeyList;
import com.afn.onthego.storage.Storage;
import com.afn.onthego.util.LearningModule;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LearnFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LearnFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LearnFragment extends Fragment {
    public ArrayList<LearningModule> learningModules;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LearnFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LearnFragment newInstance(String param1, String param2) {
        LearnFragment fragment = new LearnFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public LearnFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ListView.OnItemClickListener learnModuleListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            LearningModule learningModule = learningModules.get(position);

            switch (learningModule.getType())
            {
                case KeyList.LearningModulesKeys.TYPE_PDF:
                    Toast.makeText(getActivity(), "pdf not implemented", Toast.LENGTH_LONG).show();
                    // what to do here?
                    break;
                case KeyList.LearningModulesKeys.TYPE_WEBSITE:
                    String website_url = learningModule.getData();
                    if(URLUtil.isValidUrl(website_url)) {
                        Intent website_intent = new Intent(Intent.ACTION_VIEW);
                        website_intent.setData(Uri.parse(website_url));
                        startActivity(website_intent);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), website_url + " is an invalid URL", Toast.LENGTH_LONG).show();
                    }
                    break;
                case KeyList.LearningModulesKeys.TYPE_YOUTUBE:
                    String youtube_url = learningModule.getData();
                    if(URLUtil.isValidUrl(youtube_url)) {
                        Intent youtube_intent = new Intent(Intent.ACTION_VIEW);
                        youtube_intent.setData(Uri.parse(youtube_url));
                        startActivity(youtube_intent);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), youtube_url + " is an invalid URL", Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    Toast.makeText(getActivity(), learningModule.getType() + " not recognized", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_learn, container, false);
        ListView listView = (ListView) v.findViewById(R.id.listView);
        Storage storage = Storage.getInstance(getActivity());
        learningModules = storage.getLearningModules().getLearningModulesArray();
        ArrayList<String> modulesNameArray = storage.getLearningModules().getModulesNamesArray();
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, modulesNameArray);

        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(learnModuleListener);

        return v;
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

        public void onFragmentInteraction(Uri uri);
    }

}
