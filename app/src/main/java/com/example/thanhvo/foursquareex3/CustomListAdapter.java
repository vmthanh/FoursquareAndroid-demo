package com.example.thanhvo.foursquareex3;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by ThanhVo on 12/28/2015.
 */

public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.ViewHolder>{
    Context context;
    private final String[] itemname;
    private final String[] itemaddress;
    private final String[] itemcategories;
    private final String[] itemdistance;
    OnItemClickListener itemClickListener;
    public CustomListAdapter(Context context,String[] itemname,String[] itemaddress,String[] itemcategories,String[] itemdistance){
        this.context = context;
        this.itemname = itemname;
        this.itemaddress = itemaddress;
        this.itemcategories = itemcategories;
        this.itemdistance = itemdistance;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private LinearLayout mainholder;
        public TextView venue_name;
        public TextView venue_crossstreet;
        public TextView venue_category;
        public TextView venue_distance;
        public ViewHolder(View itemView){
            super(itemView);
            mainholder = (LinearLayout)itemView.findViewById(R.id.mainholder);
            venue_name = (TextView)mainholder.findViewById(R.id.venue_name);
            venue_crossstreet = (TextView)mainholder.findViewById(R.id.venue_crossstreet);
            venue_category = (TextView)mainholder.findViewById(R.id.venue_category);
            venue_distance = (TextView)mainholder.findViewById(R.id.venue_distance);
            mainholder.setOnClickListener(this);
        }
        @Override
        public void onClick(View v){
            if(itemClickListener!=null)
            {
                itemClickListener.onItemClick(itemView,getPosition());
            }
        }
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        if (this.itemname==null)
        {
            return 0;
        }else{
            return this.itemname.length;
        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.venue_name.setText(this.itemname[position]);
        holder.venue_crossstreet.setText(this.itemaddress[position]);
        holder.venue_category.setText(this.itemcategories[position]);
        holder.venue_distance.setText(this.itemdistance[position]);
    }
}
