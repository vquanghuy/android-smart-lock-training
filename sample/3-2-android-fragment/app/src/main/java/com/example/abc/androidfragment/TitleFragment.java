package com.example.abc.androidfragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TitleFragment extends ListFragment {
    OnTitleSelectedListener mCallback;

    public final static String[] Headlines = {
            "Article One",
            "Article Two"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int layout = android.R.layout.simple_list_item_activated_1;
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, Headlines));
    }

    // Container Activity must implement this interface
    public interface OnTitleSelectedListener {
        public void onTitleSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnTitleSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnTitleSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mCallback.onTitleSelected(position);
    }
}
