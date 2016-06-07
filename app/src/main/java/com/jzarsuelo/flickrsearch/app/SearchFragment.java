package com.jzarsuelo.flickrsearch.app;

import android.app.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
        mSeachEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;

                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    mSearchListener.onSearch(v.getText().toString());
                    hideSoftKeyboard((Activity) mSearchListener);
                    handled = true;
                }

                return handled;
            }
        });
        mSearchImageView = (ImageView) rootView.findViewById(R.id.search_imageView);
        mSearchImageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String searchText = mSeachEditText.getText().toString();
                if (searchText.length() > 0) {
                    hideSoftKeyboard((Activity) mSearchListener);
                    mSearchListener.onSearch(searchText);
                }

            }
        });

        return rootView;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_HIDDEN);
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
