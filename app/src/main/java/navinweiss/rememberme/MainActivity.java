package navinweiss.rememberme;

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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, NoteAdapter.Callback {

    private Button emergencyCallButton;
    private Button notesButton;
    private Button saveNoteButton;
    private Button newNoteButton;
    private TextView newNoteText;
    public FirebaseDatabase db;
    private GoogleMap mMap;
    private List<LatLng> routePoints = new ArrayList<>();
    private DatabaseReference mDBRef;
    private NoteAdapter mAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new navinweiss.rememberme.NoteAdapter(this);
        db = setupDatabase();
        readFromDatabase(db);
        mDBRef = FirebaseDatabase.getInstance().getReference().child("notes");

        newNoteButton = (Button) findViewById(R.id.newNoteButton);
        emergencyCallButton = (Button) findViewById(R.id.emergency_button);
        notesButton = (Button) findViewById(R.id.ReminderButton);
        saveNoteButton = (Button) findViewById(R.id.saveNoteButton);
        newNoteText = (TextView) findViewById(R.id.noteText);

        // add button listener
        setButtonListeners();
        locationSheniganry();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    public void locationSheniganry(){
        LocationManager service = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = service.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = service.getLastKnownLocation(provider);
        LatLng userLocation = new LatLng(location.getLatitude(),location.getLongitude());
        routePoints.add(userLocation);

        service.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng temp = new LatLng(location.getLatitude(),location.getLongitude());
                routePoints.add(temp);
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


        // Add a marker in Sydney and move the camera
        LatLng terreHaute = new LatLng(39.4696, -87.3898);
        final Marker terreHauteMarker = mMap.addMarker(new MarkerOptions().position(terreHaute).title("Terre Haute"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(terreHaute, 17.0f));
        routePoints.add(terreHaute);
        locationSheniganry();

//        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
//            @Override
//            public void onMapLongClick(LatLng latLng) {
//                createMarker(latLng);
//            }
//        });
//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                Toast.makeText(MainActivity.this, "You clicked a marker", Toast.LENGTH_SHORT).show();
//                if (marker.equals(terreHauteMarker)) {
//                    Toast.makeText(MainActivity.this, "You clicked Terre Haute", Toast.LENGTH_SHORT).show();
//                }
//                return false;
//            }
//        });
    }



    private void doTheDo() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        String temp = this.getString(R.string.emergency_number);
        intent.setData(Uri.parse("tel:"+temp));
        startActivity(intent);
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
        Note temp = new Note("Temp note");
        ref.push().child("note").setValue(temp.getNote());
        return db;
    }



    private void setButtonListeners() {
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

        newNoteButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                saveNoteButton.setVisibility(View.VISIBLE);
                newNoteText.setVisibility(View.VISIBLE);
                newNoteText.setFocusable(true);
            }
        });

        saveNoteButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
//                if(newNoteText != null || !newNoteText.getText().equals("")){
//                    Note temp = new Note(newNoteText.getText().toString());
//                    mAdapter.add(temp);
//                }
            }
        });

        newNoteText.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                newNoteText.setFocusable(true);
                newNoteText.requestFocus();
            }
        });
    }


    @Override
    public void onEdit(Note note) {

    }
}