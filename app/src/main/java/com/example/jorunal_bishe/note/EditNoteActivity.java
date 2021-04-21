package com.example.jorunal_bishe.note;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.base.ActivityBase;
import com.example.jorunal_bishe.dao.TbNote;
import com.example.jorunal_bishe.dao.TbNoteDao;
import com.example.jorunal_bishe.util.JDateKit;
import com.example.jorunal_bishe.util.ToastUtil;
import com.example.jorunal_bishe.widgets.ClearEditText;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditNoteActivity extends ActivityBase implements View.OnClickListener {
    @BindView(R.id.cet_description)
    ClearEditText cet_description;
    @BindView(R.id.btn_save)
    Button btn_save;
    @BindView(R.id.tv_time)
    TextView tvTime;

    private int type = 1;
    private TbNoteDao tbNoteDao;

    private String mTime;
    private String mContext;
    private Boolean isUpdate;

    private String mStartTime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_editnote;
    }

    @Override
    protected void initParams(Bundle savedInstanceState) {
        ButterKnife.bind(this);

        mTime = getIntent().getStringExtra("time");
        mContext = getIntent().getStringExtra("context");
        isUpdate = getIntent().getBooleanExtra("isUpdate", false);

        mStartTime = JDateKit.dateToStr("yyyy-MM-dd HH:mm:ss ", new Date());
        if (TextUtils.isEmpty(mTime)) {
            mTime = mStartTime;
        }
        tvTime.setText(mTime + "");
        cet_description.setText(mContext);

        tbNoteDao = TbNoteDao.getInstance();
        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                String text = cet_description.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    ToastUtil.getInstance().showMessage("内容不能为空");
                    return;
                }
                TbNote tbNote = new TbNote();
                tbNote.setType(1);
                tbNote.setContent(text);
                tbNote.setDate(mTime);
                if (isUpdate) {
                    tbNoteDao.updateNote(tbNote);
                } else {
                    tbNoteDao.saveNote(tbNote);

                }
                finish();
                break;
            default:
        }
    }
}
