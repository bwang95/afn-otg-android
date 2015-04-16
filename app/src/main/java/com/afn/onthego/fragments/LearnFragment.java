package com.afn.onthego.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.afn.onthego.R;
import com.afn.onthego.async.LearnRequest;
import com.afn.onthego.async.PDFRequest;
import com.afn.onthego.storage.KeyList;
import com.afn.onthego.storage.Storage;
import com.afn.onthego.util.LearningModule;
import com.joanzapata.pdfview.PDFView;

import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LearnFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LearnFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LearnFragment extends MainFragment
        implements PDFRequest.PDFRequestListener,
        SwipeRefreshLayout.OnRefreshListener,
        LearnRequest.LearnRequestListener {

    private OnFragmentInteractionListener mListener;

    public ListView listView;
    public ArrayAdapter<String> listAdapter;

    public ArrayList<LearningModule> learningModules;
    public ArrayList<String> modulesNameArray;

    private SwipeRefreshLayout swipeRefreshLayout;
    private PDFView pdfView;
    ProgressDialog progressDialog;

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

            switch (learningModule.getType()) {
                case KeyList.LearningModulesKeys.TYPE_PDF:
                    handlePDF(learningModule);
                    break;
                case KeyList.LearningModulesKeys.TYPE_WEBSITE:
                    handleWebsite(learningModule);
                    break;
                case KeyList.LearningModulesKeys.TYPE_YOUTUBE:
                    handleYouTube(learningModule);
                    break;
                default:
                    Toast.makeText(getActivity(), learningModule.getType() + " not recognized", Toast.LENGTH_LONG).show();
            }
        }
    };

    public void handlePDF(LearningModule learningModule) {
        progressDialog.show();
        PDFRequest request = new PDFRequest(learningModule.getData(), getActivity(), LearnFragment.this);
        request.execute();
    }

    public void handleWebsite(LearningModule learningModule) {
        String website_url = learningModule.getData();
        if (URLUtil.isValidUrl(website_url)) {
            Intent website_intent = new Intent(Intent.ACTION_VIEW);
            website_intent.setData(Uri.parse(website_url));
            startActivity(website_intent);
        } else {
            Toast.makeText(getActivity(), website_url + " is an invalid URL", Toast.LENGTH_LONG).show();
        }
    }

    public void handleYouTube(LearningModule learningModule) {
        String youtube_url = learningModule.getData();
        if (URLUtil.isValidUrl(youtube_url)) {
            Intent youtube_intent = new Intent(Intent.ACTION_VIEW);
            youtube_intent.setData(Uri.parse(youtube_url));
            startActivity(youtube_intent);
        } else {
            Toast.makeText(getActivity(), youtube_url + " is an invalid URL", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_learn, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.srl_learn_swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.holo_blue_bright,
                R.color.holo_green_light,
                R.color.holo_orange_light,
                R.color.holo_red_light);
        listView = (ListView) v.findViewById(R.id.lv_learn_module_list);
        pdfView = (PDFView) v.findViewById(R.id.pdfv_learn_pdf);
        Storage storage = Storage.getInstance(getActivity());
        learningModules = storage.getLearningModules().getLearningModulesArray();
        ArrayList<String> modulesNameArray = storage.getLearningModules().getModulesNamesArray();
        listAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, modulesNameArray);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading PDF, please wait...");
        progressDialog.setCancelable(false);

        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(learnModuleListener);

        return v;
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

    @Override
    public void onRefresh() {
        new LearnRequest(getActivity(), this).execute();
    }

    public void onPDFRequestSuccess(String filename) {
        progressDialog.hide();
        pdfView.fromFile(new File(filename)).load();
        pdfView.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void onLearnRequestSuccess(String json) {
        Storage storage = Storage.getInstance(getActivity());
        storage.getLearningModules().updateModules(json);
        modulesNameArray = storage.getLearningModules().getModulesNamesArray();
        //TODO I don't think you have to clear the adapter here.
        listAdapter.clear();
        for (String s : modulesNameArray) {
            listAdapter.add(s);
        }
        listAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void onPDFRequestFailure() {
        progressDialog.hide();
        Toast.makeText(getActivity(), "Something went wrong loading the PDF file.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLearnRequestFailure() {
        Toast.makeText(getActivity(), "Could not load from web", Toast.LENGTH_LONG).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    public boolean onBackPressed() {
        if (pdfView.getVisibility() == View.VISIBLE) {
            swipeRefreshLayout.setEnabled(true);
            Animation slideOut = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_down);
            slideOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}
                @Override
                public void onAnimationEnd(Animation animation) {
                    pdfView.setVisibility(View.GONE);
                    pdfView.recycle();
                }
                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            pdfView.startAnimation(slideOut);
            return false;
        }
        return super.onBackPressed();
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
