package appdevs.pharmtechq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import appdevs.pharmtechq.models.Question;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        // instantiate new explicit intent
        Intent intent = new Intent(getApplicationContext(), SelectTopicActivity.class);

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
