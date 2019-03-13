package appdevs.pharmtechq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void saveRegister(View view) {
        //validates

        //saves the values into database online

        //sends back to login page
        // instantiate new explicit intent
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        //start register activity
        startActivity(intent);

    }
}
