package navinweiss.rememberme;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by weissna on 8/4/2016.
 */
public class NotesActivity extends Activity
{
    private Button newNoteButton;
    private Button saveNote;
    private EditText note;
    private Context mContext;

    public NotesActivity(Context context){
        mContext = context;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notesandreminders);

        saveNote = (Button) findViewById(R.id.save_note);
        saveNote.setVisibility(View.GONE);
        newNoteButton = (Button) findViewById(R.id.newNoteButton);
        note = (EditText) findViewById(R.id.note1);

        note.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                newNoteButton.setVisibility(View.INVISIBLE);
                saveNote.setVisibility(View.VISIBLE);
                return false;
            }
        });

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        newNoteButton.setVisibility(View.VISIBLE);
        saveNote.setVisibility(View.INVISIBLE);



    }


}
