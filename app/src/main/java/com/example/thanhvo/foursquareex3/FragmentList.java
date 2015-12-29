package com.example.thanhvo.foursquareex3;

import android.content.Intent;
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

import java.util.ArrayList;

/**
 * Created by ThanhVo on 12/28/2015.
 */
public class FragmentList extends Fragment {
    String[] itemname ={ };
    String[] itemaddress ={ };
    String[] itemcategories = { };
    String[] itemdistance ={ };
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

        return view;
    }

    public void updateList(final ArrayList<FoursquareVenue>venueList)
    {
        ArrayList<String> tempname = new ArrayList<String>();
        ArrayList<String> tempaddress = new ArrayList<String>();
        ArrayList<String> tempcategory = new ArrayList<String>();
        ArrayList<String> tempdistance = new ArrayList<String>();
        for(int i=0; i<venueList.size(); ++i)
        {
            FoursquareVenue item = venueList.get(i);
            tempname.add(item.getName());
            tempaddress.add(item.getCrossStreet());
            tempdistance.add(item.getDistance());
            tempcategory.add(item.getCategory());
        }
        itemname = tempname.toArray(new String[tempname.size()]);
        itemaddress = tempaddress.toArray(new String[tempaddress.size()]);
        itemcategories = tempcategory.toArray(new String[tempcategory.size()]);
        itemdistance = tempdistance.toArray(new String[tempdistance.size()]);
        CustomListAdapter customListAdapter = new CustomListAdapter(getActivity().getApplicationContext(),itemname,itemaddress,itemcategories,itemdistance);
        CustomListAdapter.OnItemClickListener onItemClickListener = new CustomListAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View v,int position)
            {
                FoursquareVenue venue = venueList.get(position);
                Toast.makeText(getActivity().getApplicationContext(), "Clicked " + venue.getName(), Toast.LENGTH_SHORT).show();
                //This code activates the DetailVenueActivity, showing the detail of one venue. However, due to the lack of time, I skip it
               /* Intent intent = new Intent(getActivity().getBaseContext(),DetailVenueActivity.class);
                intent.putExtra("id",venue.getId());
                intent.putExtra("name",venue.getName());
                intent.putExtra("address",venue.getCrossStreet());
                intent.putExtra("category",venue.getCategory());
                intent.putExtra("distance",venue.getDistance());
                startActivity(intent);*/

            }
        };
        customListAdapter.setOnItemClickListener(onItemClickListener);
        recyclerView.swapAdapter(customListAdapter,false);
    }


}
