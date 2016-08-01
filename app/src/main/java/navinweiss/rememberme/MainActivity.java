package navinweiss.rememberme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button button;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.emergency_button);
        final Context c = this;
        // add button listener
        button.setOnClickListener(new View.OnClickListener() {


            @SuppressWarnings("MissingPermission")
            @Override
            public void onClick(View view) {
                doTheDo();
////                Intent callIntent = new Intent(MainActivity.this, Intent.ACTION_CALL);
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse(String.valueOf(R.string.emergency_number)));
//                if (ActivityCompat.checkSelfPermission(c, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    Log.d("ttt", "Something with permissions");
//                    return;
//                }
//                startActivity(callIntent);

            }

        });

    }

    private void doTheDo() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:8122295424"));
        startActivity(intent);
    }

}