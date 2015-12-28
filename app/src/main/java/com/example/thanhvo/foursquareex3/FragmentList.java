package com.example.thanhvo.foursquareex3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by ThanhVo on 12/28/2015.
 */
public class FragmentList extends Fragment {
    String[] itemname ={
            "Coffee",
            "Restaraunt",
            "Hotel",
            "Pub"
    };
    String[] itemaddress ={
            "Le loi street",
            "Nguyen Van Cu street",
            "Le hong phong street",
            "Ly thuong kiet street"
    };
    String[] itemcategories = {
            "shop",
            "shop",
            "shop",
            "shop"
    };
    String[] itemdistance ={
            "123",
            "45",
            "234",
            "60"
    };
    private ListView list;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    public FragmentList(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.list);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        CustomListAdapter customListAdapter = new CustomListAdapter(getActivity().getApplicationContext(),itemname,itemaddress,itemcategories,itemdistance);
        CustomListAdapter.OnItemClickListener onItemClickListener = new CustomListAdapter.OnItemClickListener(){
          @Override
          public void onItemClick(View v,int position)
          {
              Toast.makeText(getActivity().getApplicationContext(), "Clicked " + position, Toast.LENGTH_SHORT).show();
          }
        };
        customListAdapter.setOnItemClickListener(onItemClickListener);
        recyclerView.setAdapter(customListAdapter);

        return view;
    }
}
