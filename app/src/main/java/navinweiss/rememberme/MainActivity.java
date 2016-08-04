package navinweiss.rememberme;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends Activity {

    private Button emergencyCallButton;
    private Button notesButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
//        DatabaseReference ref = db.getReference("notes");
//        ref.setValue("Yo, world1");

        emergencyCallButton = (Button) findViewById(R.id.emergency_button);
        notesButton = (Button) findViewById(R.id.ReminderButton);
        // add button listener
        emergencyCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Log.d("ttt", "emergency?");
                    doTheDo();
            }
        });

        notesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ttt", "Notes");
                Intent notes = new Intent(MainActivity.this, NotesActivity.class);
                MainActivity.this.startActivity(notes);
            }
        });


    }

    private void doTheDo() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        String temp = this.getString(R.string.emergency_number);
        intent.setData(Uri.parse("tel:"+temp));
        startActivity(intent);
    }

}