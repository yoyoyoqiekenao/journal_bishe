package com.example.jorunal_bishe.note;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.base.FragmentBase;
import com.example.jorunal_bishe.been.Note;

import org.xutils.view.annotation.ContentView;

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

    @Override
    protected void initWidgets() {
        ButterKnife.bind(this, view);

        presenter = new NotePresenter(this, context);
        presenter.initDataBase();
        tvAdd.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadNotes();
    }

    @Override
    public void showNotes(List<Note> list) {
        mAdapter = new NoteAdapter(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rcNote.setLayoutManager(manager);
        rcNote.setAdapter(mAdapter);

        mAdapter.setNewData(list);
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
