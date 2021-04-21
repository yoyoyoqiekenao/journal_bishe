package com.example.jorunal_bishe.note;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.base.FragmentBase;
import com.example.jorunal_bishe.been.Note;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@ContentView(R.layout.fragment_note)
public class NoteFragment extends FragmentBase implements NoteContract.View, View.OnClickListener {

    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.rc_note)
    RecyclerView rcNote;

    private NoteContract.Presenter presenter;
    private NoteAdapter mAdapter;
    private List<Note> mList = new ArrayList<>();

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
}
