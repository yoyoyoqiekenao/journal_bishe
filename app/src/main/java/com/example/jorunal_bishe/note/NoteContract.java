package com.example.jorunal_bishe.note;

import com.example.jorunal_bishe.base.BasePresenter;
import com.example.jorunal_bishe.base.BaseView;
import com.example.jorunal_bishe.been.Note;

import java.util.List;

public class NoteContract {

    interface Presenter extends BasePresenter {
        void initDataBase();

        void loadNotes();
    }

    interface View extends BaseView<Presenter> {
        void showNotes(List<Note> list);
    }
}
