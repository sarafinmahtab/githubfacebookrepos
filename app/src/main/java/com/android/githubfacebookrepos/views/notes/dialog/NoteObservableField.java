package com.android.githubfacebookrepos.views.notes.dialog;

/*
 * Created by Arafin Mahtab on 6/23/20.
 */

import androidx.databinding.ObservableField;

public class NoteObservableField {

    private ObservableField<String> note = new ObservableField<>();

    public ObservableField<String> getNote() {
        return note;
    }

    public void setNote(ObservableField<String> note) {
        this.note = note;
    }

    public void setNote(String note) {
        setNote(new ObservableField<>(note));
    }
}
