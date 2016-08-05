package navinweiss.rememberme;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notesandreminders);
        saveNote = (Button) findViewById(R.id.save_note);
        saveNote.setVisibility(View.INVISIBLE);
        newNoteButton = (Button) findViewById(R.id.newNoteButton);
//        note = (EditText) findViewById(R.id.note1);

//        final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_View);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setHasFixedSize(true);

//        note.setOnTouchListener(new View.OnTouchListener(){
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                newNoteButton.setVisibility(View.INVISIBLE);
//                saveNote.setVisibility(View.VISIBLE);
//                return false;
//            }
//        });

        newNoteButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                RelativeLayout mRlayout = (RelativeLayout) findViewById(R.id.relLayout);
                RelativeLayout.LayoutParams mRparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                note = new EditText(NotesActivity.this);
                note.setLayoutParams(mRparams);
                mRlayout.addView(note);
                note.setVisibility(View.VISIBLE);
                note.setHint("Write your note here");
                saveNote.setVisibility(View.VISIBLE);
            }
        });

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNoteToDatabase(note.getText().toString());
//                RelativeLayout mRlayout = (RelativeLayout) findViewById(R.id.relLayout);
//                RelativeLayout.LayoutParams mRparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                mRparams.addRule(RelativeLayout.BELOW, 10);
//                TextView temp1 = new TextView(NotesActivity.this);
//                temp1.setLayoutParams(mRparams);
//                mRlayout.addView(temp1);
//                temp1.setText(note.getText().toString());
//                temp1.setFocusable(false);


//                mNote = new Note(recyclerView);
//                recyclerView.setAdapter(mNote);
                String temp = note.getText().toString();
                mNotes.add(temp);
                numOfNotes++;
//                Note newNote = new Note(temp);

//                readNotesFromDatabase();
            }
        });
        saveNote.setVisibility(View.VISIBLE);
    }

    public void writeNoteToDatabase(String str){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("notes");


        String refStr = "notes" + numOfNotes;
        ref.child(refStr).setValue(str);
    }

    public void readNotesFromDatabase(){

//        String temp = ref.child().
//        Log.d("ttt", "Made it this far: "+temp);
////        int i = Integer.parseInt(temp);
////        Log.d("ttt", "Num: "+i);

    }


}
