package com.example.jorunal_bishe.consume;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.dao.TbJournal;
import com.example.jorunal_bishe.util.JDataKit;
import com.example.jorunal_bishe.util.JDateKit;
import com.example.jorunal_bishe.util.JLogUtils;
import com.example.jorunal_bishe.widgets.TimeLineMarkerView;

import net.tsz.afinal.FinalBitmap;

import java.util.Date;
import java.util.List;

/**
 * @author : 徐无敌
 * date   : 2021/4/2014:56
 * desc   :
 */
public class BeforeAdapter  extends BaseQuickAdapter<TbJournal, BaseViewHolder> {

    private Context context;
    private FinalBitmap finalBitmap;
    private OnImgClickListener listener;
    private OnItemClickListener itemClickListener;

    interface OnImgClickListener {
        void onClick(String path);
    }

    public interface OnItemClickListener {
        void onItemClick(TbJournal item);
    }

    public void setOnImgClickListener(OnImgClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public BeforeAdapter(Context context, List<TbJournal> list) {
        super(R.layout.item_before, list);
        this.context = context;
        finalBitmap = FinalBitmap.create(context);
    }

    @Override
    protected void convert(BaseViewHolder helper, final TbJournal item) {
        if (itemClickListener != null) {
            helper.itemView.setBackgroundResource(R.drawable.recycler_bg);
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(item);
                }
            });
        }
        TimeLineMarkerView timeLineView = helper.getView(R.id.tlv_time);
        ImageView img = helper.getView(R.id.iv_img);
        String date = JDateKit.dateToStr("yyyy-MM-dd", new Date());
        if (date.equals(item.date)) {
            helper.setTextColor(R.id.tv_time,
                    ContextCompat.getColor(context, R.color.red));
            helper.setTextColor(R.id.tv_content,
                    ContextCompat.getColor(context, R.color.red));
            helper.setTextColor(R.id.tv_money,
                    ContextCompat.getColor(context, R.color.red));
            timeLineView.setMarkerDrawable(ContextCompat.getDrawable(context,
                    R.drawable.timeline_bg_red));
        } else {
            helper.setTextColor(R.id.tv_time,
                    ContextCompat.getColor(context, R.color.blue_sky));
            helper.setTextColor(R.id.tv_content,
                    ContextCompat.getColor(context, R.color.blue_sky));
            helper.setTextColor(R.id.tv_money,
                    ContextCompat.getColor(context, R.color.blue_sky));
            timeLineView.setMarkerDrawable(ContextCompat.getDrawable(context,
                    R.drawable.timeline_bg_blue));
        }
        String money = "";
        if (item.journalType == TbJournal.INCOME) {
            money = "+ " + JDataKit.doubleFormat(item.money);
        } else if (item.journalType == TbJournal.PAYOUT) {
            money = "- " + JDataKit.doubleFormat(item.money);
        }
        helper.setText(R.id.tv_content, item.subType);
        helper.setText(R.id.tv_money, money);
        helper.setText(R.id.tv_time, item.date + "\n" + item.time);
        if (!TextUtils.isEmpty(item.imgPath)) {
            finalBitmap.display(img, item.imgPath);
            img.setVisibility(View.VISIBLE);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onClick(item.imgPath);
                    }
                }
            });
        } else {
            img.setVisibility(View.GONE);
        }
        JLogUtils.getInstance().i("item " + item.toString());
    }
}
