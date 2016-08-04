package navinweiss.rememberme;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by weissna on 8/4/2016.
 */
public class NotesActivity extends Activity
{
    private Button newNoteButton;
    private TextView note;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notesandreminders);
        newNoteButton = (Button) findViewById(R.id.newNoteButton);
        note = (TextView) findViewById(R.id.note1);

        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }
}
