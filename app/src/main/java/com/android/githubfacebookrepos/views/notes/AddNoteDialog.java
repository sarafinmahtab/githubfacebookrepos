package com.android.githubfacebookrepos.views.notes;

/*
 * Created by Arafin Mahtab on 6/21/20.
 */

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.android.githubfacebookrepos.R;
import com.android.githubfacebookrepos.databinding.DialogAddNoteBinding;
import com.android.githubfacebookrepos.helpers.CommonUtil;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.UUID;


public class AddNoteDialog extends BottomSheetDialogFragment {

    private static final String EXTRA_CURRENT_NOTE_ID = "CurrentNoteId";
    private static final String EXTRA_CURRENT_NOTE = "CurrentNote";

    public static AddNoteDialog getInstance(
            AddNoteListener addNoteListener,
            @Nullable String currentNoteId,
            @Nullable String currentNote) {
        AddNoteDialog dialog = new AddNoteDialog(addNoteListener);

        if (currentNoteId != null) {
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_CURRENT_NOTE_ID, currentNoteId);
            bundle.putString(EXTRA_CURRENT_NOTE, currentNote);
            dialog.setArguments(bundle);
        }

        return dialog;
    }

    public final String TAG = this.getClass().getName();

    private String noteId;
    private String currentNote;

    private DialogAddNoteBinding binding;

    private AddNoteListener addNoteListener;

    public AddNoteDialog(AddNoteListener addNoteListener) {
        this.addNoteListener = addNoteListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getArguments() != null) {
            noteId = getArguments().getString(EXTRA_CURRENT_NOTE_ID);
            currentNote = getArguments().getString(EXTRA_CURRENT_NOTE);
        }

        noteId = (noteId == null || noteId.isEmpty()) ? UUID.randomUUID().toString() : noteId;
        currentNote = (currentNote == null || currentNote.isEmpty()) ? "" : currentNote;

        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_note, container, false);
        binding.noteEditText.setText(currentNote);

        if (getActivity() != null) {
            CommonUtil.showSoftKeyboardForced(getActivity(), binding.noteEditText);
        }


        binding.noteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.addNoteImageView.setVisibility(
                        s.toString().trim().isEmpty() || s.toString().equals(currentNote) ?
                                View.GONE : View.VISIBLE
                );
            }
        });


        binding.addNoteImageView.setOnClickListener(v -> {
            String note = binding.noteEditText.getText() != null ? binding.noteEditText.getText().toString().trim() : currentNote;
            if (note == null || note.isEmpty()) {
                binding.noteEditText.setError(getString(R.string.empty_note_error));
            } else {
                addNoteListener.onAddNote(noteId, note);
                dismiss();
            }
        });


        return binding.getRoot();
    }

    public interface AddNoteListener {
        void onAddNote(String note, String noteId);
    }
}
