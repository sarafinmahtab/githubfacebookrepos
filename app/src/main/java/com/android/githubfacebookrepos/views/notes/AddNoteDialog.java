package com.android.githubfacebookrepos.views.notes;

/*
 * Created by Arafin Mahtab on 6/21/20.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.android.githubfacebookrepos.R;
import com.android.githubfacebookrepos.databinding.DialogAddNoteBinding;

public class AddNoteDialog extends DialogFragment {

    private static final String EXTRA_CURRENT_NOTE = "CurrentNote";

    public static AddNoteDialog getInstance(AddNoteListener addNoteListener, @Nullable String currentNote) {
        AddNoteDialog dialog = new AddNoteDialog(addNoteListener);

        if (currentNote != null) {
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_CURRENT_NOTE, currentNote);
            dialog.setArguments(bundle);
        }

        return dialog;
    }

    public final String TAG = this.getClass().getName();

    private DialogAddNoteBinding binding;

    private AddNoteListener addNoteListener;

    private String currentNote;

    public AddNoteDialog(AddNoteListener addNoteListener) {
        this.addNoteListener = addNoteListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentNote = getArguments() != null ? getArguments().getString(EXTRA_CURRENT_NOTE) : "";

        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_note, container, false);
        binding.noteEditText.setText(currentNote);

        binding.addNoteImageView.setOnClickListener(v -> {
            String note = binding.noteEditText.getText() != null ? binding.noteEditText.getText().toString() : null;
            if (note == null || note.isEmpty()) {
                binding.noteEditText.setError(getString(R.string.empty_note_error));
            } else {
                addNoteListener.onAddNote(note);
            }
        });

        return binding.getRoot();
    }

    public interface AddNoteListener {
        void onAddNote(String note);
    }
}
