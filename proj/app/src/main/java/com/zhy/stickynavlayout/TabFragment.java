package com.zhy.stickynavlayout;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.base.adapter.recyclerview.base.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabFragment extends Fragment
{
    public static final String TITLE = "title";
    private String mTitle = "Defaut Value";
    private RecyclerView mRecyclerView;
    private List<String> mDatas = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        mRecyclerView = (RecyclerView) view
                .findViewById(R.id.id_stickynavlayout_innerscrollview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        for (int i = 0; i < 20; i++){
            mDatas.add(mTitle + " -> " + i);
        }
        mRecyclerView.setAdapter(new CommonAdapter<String>(getActivity(), R.layout.item, mDatas)
        {
            @Override
            public void convert(ViewHolder holder, String o)
            {
                holder.setText(R.id.id_info, o);
            }

        });

        return view;

    }

    public static TabFragment newInstance(String title)
    {
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

}
