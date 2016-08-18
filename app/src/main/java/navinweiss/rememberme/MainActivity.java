package navinweiss.rememberme;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Button emergencyCallButton;
    private Button notesButton;
    private Button saveNoteButton;
    private Button newNoteButton;
    private TextView newNoteText;
    public FirebaseDatabase db;
    private GoogleMap mMap;
    private List<LatLng> routePoints = new ArrayList<>();
    private DatabaseReference mDBRefNotes;
    private DatabaseReference mDBRef;
    private String phNo;
//    private NoteAdapter mAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mAdapter = new navinweiss.rememberme.NoteAdapter(this);
        db = setupDatabase();
        readFromDatabase(db);
        mDBRefNotes = FirebaseDatabase.getInstance().getReference().child("notes");
        mDBRef = FirebaseDatabase.getInstance().getReference();
        phNo = "8122295424";

        newNoteButton = (Button) findViewById(R.id.newNoteButton);
        emergencyCallButton = (Button) findViewById(R.id.emergency_button);
        notesButton = (Button) findViewById(R.id.ReminderButton);
        saveNoteButton = (Button) findViewById(R.id.saveNoteButton);
        newNoteText = (TextView) findViewById(R.id.noteText);
        newNoteText.setVisibility(View.INVISIBLE);

        // add button listener
        setButtonListeners();
        locationSheniganry();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    public void locationSheniganry() {
        LocationManager service = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = service.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

//            @Override
//            public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
//
//            }
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        Location location = service.getLastKnownLocation(provider);
//        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
//        routePoints.add(userLocation);

        service.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng temp = new LatLng(location.getLatitude(), location.getLongitude());
                routePoints.add(temp);
                mMap.addMarker(new MarkerOptions().position(temp).title(("" + Calendar.getInstance().getTime())));
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(temp, 17.0f));
            }

            @Override
            public void onProviderDisabled(String provider) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProviderEnabled(String provider) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
                // TODO Auto-generated method stub
            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LocationManager locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);

        LatLng terreHaute = new LatLng(39.4696, -87.3898);
        mMap.addMarker(new MarkerOptions().position(terreHaute));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(terreHaute, 17.0f));
        routePoints.add(terreHaute);
        locationSheniganry();

    }



    private void readFromDatabase(FirebaseDatabase db) {
//        Log.d("ttt", "Read stuff");
        db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("notes");
        Log.d("ttt", (ref.getKey()).toString());

    }

    private FirebaseDatabase setupDatabase() {
        db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("notes");
//        Note temp = new Note("Temp note");
//        ref.push().child("note").setValue(temp.getNote());

        return db;
    }



    private void setButtonListeners() {
        emergencyCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ttt", "emergency?");
                Intent intent = new Intent(Intent.ACTION_DIAL);
//                mAdapter.
//                final String[] s = {""};
                Log.d("phon", getPhoneNumber());
                intent.setData(Uri.parse("tel:"+getPhoneNumber()));
                startActivity(intent);
            }
        });

//        emergencyCallButton.setOnLongClickListener(new View.OnLongClickListener(){
//
//            @Override
//            public boolean onLongClick(View v) {
//            }
//        });

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

        newNoteButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                newNoteButton.setVisibility(View.INVISIBLE);
                saveNoteButton.setVisibility(View.VISIBLE);
                newNoteText.setVisibility(View.VISIBLE);
                newNoteText.setFocusable(true);
                newNoteText.requestFocus();
            }
        });

        saveNoteButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
//                if(newNoteText != null || !newNoteText.getText().equals("")){
//                    Note temp = new Note(newNoteText.getText().toString());
//                    mAdapter.add(temp);
//                }
                mDBRefNotes.push().child("note").setValue(newNoteText.getText().toString());
                newNoteText.setText("");
                newNoteText.setVisibility(View.INVISIBLE);
                saveNoteButton.setVisibility(View.INVISIBLE);
                newNoteButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private String getPhoneNumber() {
        mDBRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> t = dataSnapshot.child("phone").child("emergCon").getChildren();
                for(DataSnapshot d : t) {
                    phNo = d.getValue().toString();
                    Log.d("phoe", phNo);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return phNo;
    }


    class NotesChildEventListener implements ChildEventListener{

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Note note = dataSnapshot.getValue(Note.class);
            note.setKey(dataSnapshot.getKey());
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