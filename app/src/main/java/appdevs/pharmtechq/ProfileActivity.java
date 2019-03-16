package appdevs.pharmtechq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    Toolbar toolBar;
    FirebaseAuth authDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        authDb = FirebaseAuth.getInstance();
        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_select_topics:
                startActivity(new Intent(getApplicationContext(), SelectTopicActivity.class));
                return true;
            case R.id.action_profile:
                return true;
            case R.id.action_logout:
                authDb.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
