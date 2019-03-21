package appdevs.pharmtechq;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import appdevs.pharmtechq.adapters.QuizScoresAdapter;
import appdevs.pharmtechq.adapters.ReferencesAdapter;
import appdevs.pharmtechq.models.QuizScore;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolBar;
    private FirebaseAuth authDb;
    private DatabaseReference db;
    private ArrayList<QuizScore> quizScores;
    private String currentUserId;
    private String latestQuizTopic;
    private int latestCorrectAttempts;
    private int latestTotalAttempts;
    private TextView textViewLatestTopic;
    private TextView textViewLatestResult;
    private float latestResultPercent;
    private TextView textViewProfileWelcomeLabel;
    private ArrayList<String> topics;
    private ArrayList<String> results;
    private TextView textViewTakeAQuiz;

    private static final String TAG = "ProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        authDb = FirebaseAuth.getInstance();

        if(authDb.getCurrentUser() != null) {
            currentUserId = authDb.getCurrentUser().getUid();
        }

        db = FirebaseDatabase.getInstance().getReference("userScores").child(currentUserId);
        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        textViewLatestTopic = findViewById(R.id.textViewLatestTopic);
        textViewLatestResult = findViewById(R.id.textViewLatestResult);
        textViewProfileWelcomeLabel = findViewById(R.id.textViewProfileWelcomeLabel);
        textViewTakeAQuiz = findViewById(R.id.textViewTakeAQuiz);
        quizScores = new ArrayList<>();
        topics = new ArrayList<>();
        results = new ArrayList<>();

        // get quizActivity results
        Intent intent = getIntent();
        latestQuizTopic = intent.getStringExtra("TOPIC");
        latestCorrectAttempts = intent.getIntExtra("CORRECT_ATTEMPTS", 0);
        latestTotalAttempts = intent.getIntExtra("TOTAL_ATTEMPTS", 0);

        populateWelcomeLabel();

        if(latestQuizTopic == null && latestCorrectAttempts == 0) {
            textViewTakeAQuiz.setVisibility(View.VISIBLE);
            textViewLatestTopic.setVisibility(View.GONE);
            textViewLatestResult.setVisibility(View.GONE);

            textViewTakeAQuiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), SelectTopicActivity.class));
                }
            });
        }
        else {
            textViewTakeAQuiz.setVisibility(View.GONE);
            textViewLatestTopic.setVisibility(View.VISIBLE);
            textViewLatestResult.setVisibility(View.VISIBLE);

        }

        populateLatestAttempt();

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                quizScores.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    QuizScore quizScore = snapshot.getValue(QuizScore.class);
                    quizScores.add(quizScore);
                }

                // if the user completed a quiz, add the results to the database
                // if the user traveled to the profile activity from outside the quiz,
                // do not add anything to the database
                if(latestQuizTopic != null) {
                    addCurrentQuizScore();
                }

                //get the topics and results for the summary progress recylerview
                for(QuizScore qs : quizScores) {
                    topics.add(qs.getQuizScoreTopic());

                    int correctAttempts = qs.getQuizScoreCorrectAttempts();
                    int totalAttempts = qs.getQuizScoreTotalAttempts();
                    float resultPercent = Math.round(((float)correctAttempts / totalAttempts) * 100);
                    String quizResult = resultPercent + "%";
                    results.add(quizResult);
                }

                initQuizScoresAdapter(topics, results);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
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
                return true;
            case R.id.action_logout:
                authDb.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void populateWelcomeLabel() {
        String welcome = "Welcome, " + authDb.getCurrentUser().getDisplayName();
        textViewProfileWelcomeLabel.setText(welcome);
    }

    private void addCurrentQuizScore() {
        // check if there is already a topic present in database
        // if there is, add results from last quiz to total stored in database
        // and then update
        boolean newEntry = true;
        for(QuizScore q : quizScores) {
            if(q.getQuizScoreTopic().equals(latestQuizTopic)) {
                int previousCorrectAttempts = q.getQuizScoreCorrectAttempts();
                int previousTotalAttempts = q.getQuizScoreTotalAttempts();
                previousTotalAttempts += latestTotalAttempts;
                previousCorrectAttempts += latestCorrectAttempts;

                db.child(q.getQuizScoreId()).child("quizScoreCorrectAttempts").setValue(previousCorrectAttempts);
                db.child(q.getQuizScoreId()).child("quizScoreTotalAttempts").setValue(previousTotalAttempts);

                newEntry = false;

            }
        }
        if(newEntry) {
            String quizScoreId = db.push().getKey();
            QuizScore quizScore = new QuizScore(quizScoreId, latestQuizTopic,
                    latestCorrectAttempts, latestTotalAttempts);
            if(quizScoreId != null) {
                db.child(quizScoreId).setValue(quizScore);
            }
        }
    }

    //populates the textviews with the results of the last finished quiz
    private void populateLatestAttempt() {
        textViewLatestTopic.setText(latestQuizTopic);

        latestResultPercent = Math.round(((float)latestCorrectAttempts / latestTotalAttempts) * 100);

        String latestResult = latestResultPercent + "%";

        if(latestResultPercent < 49) {
            textViewLatestResult.setTextColor(this.getResources().getColor(R.color.under_50_percent));
        }
        else if (latestResultPercent >= 50 && latestResultPercent < 79) {
            textViewLatestResult.setTextColor(this.getResources().getColor(R.color.under_80_percent));
        }
        else {
            textViewLatestResult.setTextColor(this.getResources().getColor(R.color.under_100_percent));
        }
        textViewLatestResult.setText(latestResult);
    }

    private void initQuizScoresAdapter(ArrayList<String> topics, ArrayList<String> results) {
        RecyclerView recyclerViewQuizScores = findViewById(R.id.recyclerViewQuizScores);
        QuizScoresAdapter quizScoresAdapter = new QuizScoresAdapter(this, topics, results);
        recyclerViewQuizScores.setAdapter(quizScoresAdapter);
        recyclerViewQuizScores.setLayoutManager(new LinearLayoutManager(this));
    }
}

