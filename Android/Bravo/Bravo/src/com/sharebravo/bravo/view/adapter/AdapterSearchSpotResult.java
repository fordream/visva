package com.sharebravo.bravo.view.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharebravo.bravo.R;
import com.sharebravo.bravo.model.response.ObGetSpot.Spot;
import com.sharebravo.bravo.sdk.util.network.ImageLoader;

public class AdapterSearchSpotResult extends BaseAdapter {
    private FragmentActivity mContext     = null;
    private ImageLoader      mImageLoader = null;
    private ArrayList<Spot>  mSpots       = new ArrayList<Spot>();

    public AdapterSearchSpotResult(FragmentActivity fragmentActivity) {
        mContext = fragmentActivity;
        mImageLoader = new ImageLoader(mContext);
    }

    @Override
    public int getCount() {
        return mSpots.size() + 1;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parentView) {
        if (position == getCount() - 1) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_search_result_footer, parentView);
        } else {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_search_spot_result, parentView);
            ViewHolder holder = new ViewHolder();
            holder.spotAvatar = (ImageView) convertView.findViewById(R.id.img_avatar_spot);
            holder.spotName = (TextView) convertView.findViewById(R.id.txt_spot_name);
            holder.numberBravos = (TextView) convertView.findViewById(R.id.text_number_bravo);
            holder.spotName.setText(mSpots.get(position).Spot_Name);
            holder.numberBravos.setText(mSpots.get(position).Total_Bravos + " Bravos");
        }

        return convertView;
    }

    public void updateData(ArrayList<Spot> mSpots) {
        this.mSpots = mSpots;
        notifyDataSetChanged();
    }

    class ViewHolder {
        ImageView spotAvatar;
        TextView  spotName;
        TextView  numberBravos;
    }
}
