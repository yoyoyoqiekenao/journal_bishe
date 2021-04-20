package com.example.jorunal_bishe.mine;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.base.AbsListAdapter;

import java.util.List;

/**
 * @author : 徐无敌
 * date   : 2021/4/2016:55
 * desc   :
 */
public class ClassifyAdapter  extends AbsListAdapter<Classify> {

    public ClassifyAdapter(Context context, List<Classify> datas) {
        super(context, datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_menu, parent, false);
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Classify classify = datas.get(position);
        holder.ivIcon.setImageBitmap(classify.getIcon());
        holder.tvName.setText(classify.getName());
        return convertView;
    }

    class ViewHolder {
        ImageView ivIcon;
        TextView tvName;
    }
}
