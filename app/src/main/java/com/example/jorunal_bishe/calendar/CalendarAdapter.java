package com.example.jorunal_bishe.calendar;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.base.AbsListAdapter;
import com.example.jorunal_bishe.been.JCalendar;

import java.util.List;

/**
 * @author : 徐无敌
 * date   : 2021/4/2010:54
 * desc   :
 */
public class CalendarAdapter  extends AbsListAdapter<JCalendar> {
    private int drawableId = -1;

    public CalendarAdapter(Context context, List<JCalendar> datas) {
        super(context, datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        JCalendar calendar = datas.get(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.calendar_item, parent, false);
            holder = new ViewHolder();
            holder.calendarText = (TextView) convertView.findViewById(R.id.tv_calendar);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String day = String.valueOf(calendar.getCommonCalendar().getDay());
        String lunarDay = calendar.getChineseCalendar().getFestival() == null
                ? calendar.getChineseCalendar().getDay() : calendar.getChineseCalendar().getFestival();
        SpannableString sp = new SpannableString(day + "\n" + lunarDay);
        sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                0, day.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new RelativeSizeSpan(1.2f), 0, day.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (!TextUtils.isEmpty(lunarDay)) {
            sp.setSpan(new RelativeSizeSpan(0.75f), day.length() + 1,
                    day.length() + lunarDay.length() + 1,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        holder.calendarText.setText(sp);
        if (calendar.isToday()) {
            // 设置当天的背景
            drawableId = R.mipmap.current_day_bgc;
            holder.calendarText.setTextColor(Color.WHITE);
        } else if (position < JCalendar.daysOfMonth + JCalendar.dayOfWeek && position >= JCalendar.dayOfWeek) {
            // 当前月信息显示
            drawableId = R.mipmap.item;
        } else {
            // 不是当前月份
            holder.calendarText.setTextColor(Color.parseColor("#FFBEB9B9"));
        }
        if (drawableId != -1)
            holder.calendarText.setBackgroundResource(drawableId);
        return convertView;
    }

    class ViewHolder {
        public TextView calendarText;
    }
}
