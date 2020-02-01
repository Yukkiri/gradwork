package ru.puchkova.gradwork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private ArrayList<Notes> notes;
    private LayoutInflater inflater;

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
    public void onBindViewHolder(NotesAdapter.ViewHolder holder, int position) {
        Notes note = notes.get(position);
        holder.title.setText(note.getTitle());
        holder.description.setText(note.getDescription());
        holder.deadline.setText(note.getDeadline());
        holder.checkBox.setOnCheckedChangeListener(myCheckChangeList);
        holder.checkBox.setTag(position);
        holder.checkBox.setChecked(note.isCheck());
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
            CheckBox checkBox = view.findViewById(R.id.checkbox);
            TextView title = view.findViewById(R.id.title);
            TextView description = view.findViewById(R.id.description);
            TextView deadline = view.findViewById(R.id.deadline);
        }
    }
}