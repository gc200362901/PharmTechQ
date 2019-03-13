package appdevs.pharmtechq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        // instantiate new explicit intent
        Intent intent = new Intent(getApplicationContext(), selectTopic.class);

        //validation here


        //auth here



        // clear out email and password fields


        //start select Topic activity
        startActivity(intent);
    }

    public void register(View view) {

        // instantiate new explicit intent
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);

        //start register activity
        startActivity(intent);
    }
}
