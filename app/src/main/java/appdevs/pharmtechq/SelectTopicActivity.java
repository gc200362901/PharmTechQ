package appdevs.pharmtechq;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

import appdevs.pharmtechq.adapters.TopicsAdapter;
import appdevs.pharmtechq.models.Question;

public class SelectTopicActivity extends AppCompatActivity {

    Toolbar toolBar;
    private HashSet<String> tmpTopics;
    ArrayList<String> topics;
    private DatabaseReference db;
    FirebaseAuth authDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_topic);

        authDb = FirebaseAuth.getInstance();
        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        populateTopics();
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

    /**
     * Connect to Firebase database to question.topic. Add topics to hashset to remove duplicates
     * and then convert hashset to arraylist to be used in recyclerview
     */
    private void populateTopics() {
        
        db = FirebaseDatabase.getInstance().getReference("questions");
        tmpTopics = new HashSet<>();

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapShot: dataSnapshot.getChildren()) {
                    Question question = postSnapShot.getValue(Question.class);
                    tmpTopics.add(question.getQuestionTopic());
                }

                topics = new ArrayList<>(tmpTopics);

                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    /**
     * Get reference to recyclerview. Instantiate the adapter for the recyclerview with the
     * context and topics arraylist arguments. Set the adapter for the recyclerview. Set the
     * layout manager for the recyclerview to linear layout manager so recyclerview loads vertical
     * list
     */
    private void initRecyclerView() {
        
        RecyclerView recyclerView = findViewById(R.id.recyclerViewTopics);
        TopicsAdapter topicsAdapter = new TopicsAdapter(this, topics);
        recyclerView.setAdapter(topicsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
