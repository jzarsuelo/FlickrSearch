package com.jzarsuelo.flickrsearch.app;

import android.app.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment {

    private OnSearchListener mSearchListener;
    private Context mContext;
    private EditText mSeachEditText;
    private ImageView mSearchImageView;

    public SearchFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof OnSearchListener){
            mSearchListener = (OnSearchListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        mContext = rootView.getContext();
        mSeachEditText = (EditText) rootView.findViewById(R.id.search_editText);
        mSearchImageView = (ImageView) rootView.findViewById(R.id.search_imageView);
        mSearchImageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String searchText = mSeachEditText.getText().toString();
                if (searchText.length() > 0) {
                    mSearchListener.onSearch(searchText);
                }

            }
        });

        return rootView;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * */
    public interface OnSearchListener{
        void onSearch(String text);
    }
}
