package com.example.jorunal_bishe.note;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.base.FragmentBase;
import com.example.jorunal_bishe.been.Note;
import com.example.jorunal_bishe.util.ToastUtil;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@ContentView(R.layout.fragment_note)
public class NoteFragment extends FragmentBase implements NoteContract.View, View.OnClickListener, PopupWindow.OnDismissListener {

    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.rc_note)
    RecyclerView rcNote;
    @BindView(R.id.ll)
    LinearLayout ll;

    private NoteContract.Presenter presenter;
    private NoteAdapter mAdapter;
    private List<Note> mList = new ArrayList<>();
    private PopupWindow mPop;

    @Override
    protected void initWidgets() {
        ButterKnife.bind(this, view);


        tvAdd.setOnClickListener(this);

        mAdapter = new NoteAdapter(mList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rcNote.setLayoutManager(manager);
        rcNote.setAdapter(mAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter = new NotePresenter(this, context);
        presenter.initDataBase();
        presenter.loadNotes();
    }

    @Override
    public void showNotes(List<Note> list) {

        mList.clear();
        mList.addAll(list);
        mAdapter.setNewData(list);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(getContext(), EditNoteActivity.class);
                intent.putExtra("time", list.get(position).getDate());
                intent.putExtra("context", list.get(position).getContent());
                intent.putExtra("isUpdate", true);
                startActivity(intent);
            }
        });

        mAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                showPop(position);
                return false;
            }
        });
    }

    @Override
    public void deleteNotes() {
        ToastUtil.getInstance().showMessage("删除成功");
    }

    @Override
    public void setPresenter(NoteContract.Presenter presenter) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add:
                Intent intent = new Intent(getContext(), EditNoteActivity.class);
                startActivity(intent);
                break;
            default:
        }
    }

    private void showPop(int position) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pop_delete, null);
        mPop = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView tvSure = view.findViewById(R.id.tv_sure);
        TextView tvNo = view.findViewById(R.id.tv_no);

        backgroundAlpha(0.5f);
        mPop.setFocusable(true);
        mPop.setBackgroundDrawable(new BitmapDrawable());
        mPop.setOutsideTouchable(true);
        mPop.setAnimationStyle(R.style.anim_bottonbar);

        mPop.setOnDismissListener(this);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteNotes(mList.get(position).getDate());
                mPop.dismiss();
                onResume();
            }
        });
        mPop.showAtLocation(ll, Gravity.CENTER, 0, 0);

    }

    /**
     * 设置添加屏幕的背景透明度 * * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public void onDismiss() {
        backgroundAlpha(1.0f);
    }
}
