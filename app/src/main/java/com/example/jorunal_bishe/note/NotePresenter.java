package com.example.jorunal_bishe.note;

import android.content.Context;
import android.util.Log;

import com.example.jorunal_bishe.been.Note;
import com.example.jorunal_bishe.dao.TbNote;
import com.example.jorunal_bishe.dao.TbNoteDao;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;

public class NotePresenter implements NoteContract.Presenter {

    private final NoteContract.View view;
    private Context context;
    private TbNoteDao tbNoteDao;
    private List<Note> noteList;

    public NotePresenter(NoteContract.View view, Context context) {
        this.view = view;
        this.context = context;
        noteList = new ArrayList<>();
    }

    @Override
    public void initDataBase() {
        tbNoteDao = TbNoteDao.getInstance();
    }

    @Override
    public void loadNotes() {
        noteList.clear();
        List<TbNote> list = tbNoteDao.getNote(1);
        if (list != null && list.size() > 0) {
            for (TbNote tbNote : list) {
                Note note = new Note();
                note.setDate(tbNote.getDate());
                note.setContent(tbNote.getContent());
                noteList.add(note);
            }
        }
        view.showNotes(noteList);
    }

    @Override
    public void start() {

    }
}
