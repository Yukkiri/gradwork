package ru.puchkova.gradwork;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private ArrayList<Notes> notes;
    private LayoutInflater inflater;
    private App app;
    NotesList list;

    private static final String NOT_TODAY = "-1";
    private static final String EMPTY = "";
    private static final int RED = 1;
    private static final int YELLOW = 0;

    private CompoundButton.OnCheckedChangeListener myCheckChangeList
            = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            notes.get((Integer) buttonView.getTag()).setCheck(isChecked);
        }
    };

    NotesAdapter(Context context, ArrayList<Notes> notes) {
        this.notes = notes;
        this.inflater = LayoutInflater.from(context);;
    }


    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.notes_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NotesAdapter.ViewHolder holder, int position) {
        Deadlines deadlines = new Deadlines();
        final Notes note = notes.get(position);
        if(note.getTitle().equals(EMPTY)){
            holder.title.setVisibility(View.GONE);
        }
        holder.title.setText(note.getTitle());
        if(note.getDescription().equals(EMPTY)){
            holder.description.setVisibility(View.GONE);
        }
        holder.description.setText(note.getDescription());
        if (note.getDeadline().equals(NOT_TODAY)){
            holder.deadline.setVisibility(View.INVISIBLE);
        } else if (deadlines.deadlineImportance(note.getDeadline()) == RED) {
            holder.deadline.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorAccent));
            holder.deadline.setVisibility(View.VISIBLE);
        } else if (deadlines.deadlineImportance(note.getDeadline()) == YELLOW) {
            holder.deadline.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.deadlineColor));
            holder.deadline.setVisibility(View.VISIBLE);
        } else{
            holder.deadline.setVisibility(View.VISIBLE);
            holder.deadline.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimary));
        }
        holder.deadline.setText(deadlines.deadlineToDate(note.getDeadline()));
        holder.checkBox.setOnCheckedChangeListener(myCheckChangeList);
        holder.checkBox.setTag(position);
        holder.checkBox.setChecked(note.isCheck());
        final int noteId = note.getId();
        final String noteDeadline = note.getDeadline();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openNote = new Intent(holder.itemView.getContext(), NoteActivity.class);
                openNote.putExtra("noteId", noteId);
                openNote.putExtra("title", holder.title.getText().toString());
                openNote.putExtra("description", holder.description.getText().toString());
                openNote.putExtra("deadline", noteDeadline);
                openNote.putExtra("isChecked", note.isCheck());
                holder.itemView.getContext().startActivity(openNote);
            }
        });

        //дописать позже
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                app = (App)holder.itemView.getContext().getApplicationContext();
                if(note.isCheck())
                    note.setCheck(false);
                else
                    note.setCheck(true);

                app.changeNote(note);
            }

        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView title;
        TextView description;
        TextView deadline;

        ViewHolder(View view){
            super(view);
            checkBox = view.findViewById(R.id.checkBox);
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            deadline = view.findViewById(R.id.deadline);
        }
    }
}

