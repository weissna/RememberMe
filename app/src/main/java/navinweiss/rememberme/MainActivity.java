package navinweiss.rememberme;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends Activity {

    private Button emergencyCallButton;
    private Button notesButton;
    public FirebaseDatabase db;
    private Note mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = setupDatabase();
        readFromDatabase(db);

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
//                NotesActivity n = new NotesActivity(MainActivity.this);
                Intent notes = new Intent(MainActivity.this, NotesActivity.class);
//                notes.
                MainActivity.this.startActivity(notes);
            }
        });


//        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_View);
//        mLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setHasFixedSize(true);
//        mAdapter = new Note(recyclerView);
//        recyclerView.setAdapter(mAdapter);

    }

    private void doTheDo() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        String temp = this.getString(R.string.emergency_number);
        intent.setData(Uri.parse("tel:"+temp));
        startActivity(intent);
    }

    private void readFromDatabase(FirebaseDatabase db) {
        Log.d("ttt", "Read stuff");
    }

    private FirebaseDatabase setupDatabase() {
        db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("notes");
//        ref.child("note1").setValue("Note 1");
//        ref.child("note2").setValue("Note 2");
        return db;
    }



}