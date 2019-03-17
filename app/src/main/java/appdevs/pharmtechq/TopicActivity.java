package appdevs.pharmtechq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class TopicActivity extends AppCompatActivity {

    Toolbar toolBar;
    FirebaseAuth authDb;
    TextView textViewTopic;
    String topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        authDb = FirebaseAuth.getInstance();
        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        textViewTopic = findViewById(R.id.textViewTopic);

        Intent intent = getIntent();
        topic = intent.getStringExtra("TOPIC");

        textViewTopic.setText(topic);
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
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                return true;
            case R.id.action_logout:
                authDb.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void quizButton(View view) {
        Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
        intent.putExtra("TOPIC", topic);
        startActivity(intent);
    }
}
