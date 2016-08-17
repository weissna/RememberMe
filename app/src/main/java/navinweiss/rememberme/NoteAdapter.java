package navinweiss.rememberme;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weissna on 8/16/2016.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<Note> mNoteList;
    private Callback mCallback;
    private DatabaseReference mNotesRef;

    public NoteAdapter(Callback callback){
        mCallback = callback;
        mNoteList = new ArrayList<>();
        mNotesRef = FirebaseDatabase.getInstance().getReference().child("notes");
        mNotesRef.addChildEventListener(new NotesChildEventListener());

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note note = mNoteList.get(position);
        holder.mNoteText.setText(mNoteList.get(position).getNote());
//        holder.itemView.setOnClickListener(new View.OnClickListener(){);

    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    public void add(Note note){
        mNotesRef.push().setValue(note.getNote());
    }

    public void update(Note note, String newNote){
        note.setNote(newNote);
        mNotesRef.child(note.getKey()).setValue(note);
    }

    private void deleteItem(Note note) {
//        mNoteList.remove(pos);
//        notifyItemRemoved(pos);
        mNotesRef.child(note.getKey()).removeValue();
    }

    public interface Callback {
        void onEdit(Note note);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mNoteText;

        public ViewHolder(View itemView) {
            super(itemView);
            mNoteText = (TextView)itemView.findViewById(R.id.note_text);
        }
    }



    class NotesChildEventListener implements ChildEventListener {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//            Note note = dataSnapshot.getValue(Note.class);
//            note.setKey(dataSnapshot.getKey());


        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }




}

