package com.maakservicess.beingparents.app_monitor.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.controllers.MainsListData;

import java.util.List;

/**
 * Created by SAI PC on 3/27/2016.
 */
public class MainsListAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    List<MainsListData> guidanceList;

    public MainsListAdapter(Context context, List<MainsListData> guidanceList) {
        this.context = context;
        this.guidanceList = guidanceList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return guidanceList.size();
    }

    @Override
    public Object getItem(int position) {
        return guidanceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.guidance_list_item, parent, false);

            viewHolder.guidanceTitleTextView = (TextView) convertView.findViewById(R.id.guidanceListItemTextView);
            viewHolder.guidanceImgView = (ImageView) convertView.findViewById(R.id.guidanceListItemImgView);

            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }

        MainsListData mainsListDatas = guidanceList.get(position);

        viewHolder.guidanceTitleTextView.setText(mainsListDatas.getTitle());
        viewHolder.guidanceImgView.setImageResource(mainsListDatas.getImgId());

        return convertView;
    }

    public class ViewHolder {
        private TextView guidanceTitleTextView;
        private ImageView guidanceImgView;

    }
}
