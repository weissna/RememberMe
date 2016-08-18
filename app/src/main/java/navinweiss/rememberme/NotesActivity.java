package navinweiss.rememberme;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weissna on 8/4/2016.
 */
public class NotesActivity extends Activity
{
    private Button newNoteButton;
    private Button saveNote;
    private EditText note;
    private long numOfNotes;
    private Note mNote;
    private List<String> mNotes = new ArrayList<>();
    private TextView last;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notesandreminders);
        last = (TextView) findViewById(R.id.note_text);

        readNotesFromDatabase();
    }


    public void readNotesFromDatabase(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference ref = db.getReference("notes");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> s = dataSnapshot.getChildren();
                for(DataSnapshot d : s){
                    String t = d.child("note").getValue().toString();
                    mNotes.add(t);
                    Log.d("sss", t);
                }
                displayNotes(mNotes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void displayNotes(List<String> mNotes) {
        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        for(int i = 0; i<mNotes.size();i++){
            TextView v = new TextView(this);
            v.setText(mNotes.get(i));
            v.setTextSize(15);
            linearLayout.addView(v);
        }
    }


}
