package com.example.jorunal_bishe.note;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.been.Note;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NoteAdapter extends BaseQuickAdapter<Note, BaseViewHolder> {

    public NoteAdapter(@Nullable List<Note> data) {
        super(R.layout.item_note, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Note list) {

        baseViewHolder.setText(R.id.tv_time, list.getDate());
        baseViewHolder.setText(R.id.tv_content, list.getContent());
    }
}
