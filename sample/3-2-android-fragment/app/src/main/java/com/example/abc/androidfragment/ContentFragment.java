package com.example.abc.androidfragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContentFragment extends Fragment {
    static final String POSITION_KEY = "POSITION_KEY";
    static final String contents[] = {"This is content 1",
                                        "This is content 2"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if (args != null)
        {
            updateContent(args.getInt(POSITION_KEY));
        }
        else
        {
            updateContent(0);
        }
    }

    public void updateContent(int position)
    {
        TextView txtContent = (TextView) getActivity().findViewById(R.id.txtContent);
        txtContent.setText(contents[position]);
    }
}
