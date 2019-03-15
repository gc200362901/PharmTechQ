package appdevs.pharmtechq;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

    private HashSet<String> tmpTopics;
    ArrayList<String> topics;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_topic);

        populateTopics();
    }

    private void populateTopics() {
        
        db = FirebaseDatabase.getInstance().getReference("questions");
        tmpTopics = new HashSet<>();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapShot: dataSnapshot.getChildren()) {
                    Question question = postSnapShot.getValue(Question.class);
                    tmpTopics.add(question.getQuestionTopic());
                }

                //convert topics hashset into arraylist for recyclerview
                topics = new ArrayList<>(tmpTopics);

                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void initRecyclerView() {
        
        RecyclerView recyclerView = findViewById(R.id.recyclerViewTopics);
        TopicsAdapter topicsAdapter = new TopicsAdapter(this, topics);
        recyclerView.setAdapter(topicsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
