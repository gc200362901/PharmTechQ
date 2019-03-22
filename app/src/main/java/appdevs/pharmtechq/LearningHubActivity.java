package appdevs.pharmtechq;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import appdevs.pharmtechq.adapters.LearningHubAdapter;
import appdevs.pharmtechq.models.LearningHubEntry;

public class LearningHubActivity extends AppCompatActivity {

    private static final String TAG = "LearningHubActivity";
    
    private Toolbar toolBar;
    private FirebaseAuth authDb;
    private DatabaseReference db;
    private TextView textViewLearningHubTopic;
    private ArrayList<LearningHubEntry> learningHubEntries;
    private String quizTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_hub);

        Intent intent = getIntent();
        quizTopic = intent.getStringExtra("TOPIC");
        db = FirebaseDatabase.getInstance().getReference("learningHub").child(quizTopic);

        learningHubEntries = new ArrayList<>();

        authDb = FirebaseAuth.getInstance();
        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        textViewLearningHubTopic = findViewById(R.id.textViewLearningHubTopic);

        populateLearningHub();

    }

    @Override
    public void onStart() {

        super.onStart();

        if(authDb.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
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

    private void populateLearningHub() {

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    LearningHubEntry learningHubEntry = postSnapShot.getValue(LearningHubEntry.class);
                    learningHubEntries.add(learningHubEntry);
                }
                textViewLearningHubTopic.setText(quizTopic);
                initRecyclerViewLearningHub(learningHubEntries);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void initRecyclerViewLearningHub(ArrayList<LearningHubEntry> learningHubEntries) {
        
        RecyclerView recyclerViewLearningHub = findViewById(R.id.recyclerViewLearningHub);
        LearningHubAdapter learningHubAdapter = new LearningHubAdapter(this, learningHubEntries);
        recyclerViewLearningHub.setAdapter(learningHubAdapter);
        recyclerViewLearningHub.setLayoutManager(new LinearLayoutManager(this));
    }
}
