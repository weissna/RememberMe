package navinweiss.rememberme;

import android.app.Activity;
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
        // add button listener
        button.setOnClickListener(new View.OnClickListener() {


            @SuppressWarnings("MissingPermission")
            @Override
            public void onClick(View view) {
                doTheDo();

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