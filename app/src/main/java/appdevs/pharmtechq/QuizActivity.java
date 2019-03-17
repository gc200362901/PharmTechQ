package appdevs.pharmtechq;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import appdevs.pharmtechq.adapters.AnswersAdapter;
import appdevs.pharmtechq.models.Question;

public class QuizActivity extends AppCompatActivity {

    private Toolbar toolBar;
    private FirebaseAuth authDb;
    private DatabaseReference db;
    public static ArrayList<Question> quizQuestions;
    public static int quizQuestionCount;
    public static String quizTopic;
    private TextView textViewQuestion;
    ConstraintLayout rootExplanationConstraint;
    ConstraintLayout rootReferenceConstraint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        quizQuestions = new ArrayList<>();
        quizQuestionCount = 0;
        //database
        authDb = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference("questions");
        //actionbar menu
        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        //views
        textViewQuestion = findViewById(R.id.textViewQuestion);
        rootExplanationConstraint = findViewById(R.id.rootExplanationConstraint);
        rootReferenceConstraint = findViewById(R.id.rootReferencesConstraint);

        rootExplanationConstraint.setVisibility(View.INVISIBLE);
        rootReferenceConstraint.setVisibility(View.INVISIBLE);

        getTopicQuestion();
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

    private void getTopicQuestion() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //user's first question on this activity coming for topics activity
                if(quizQuestions.size() == 0) {
                    ArrayList<Question> tmpQuestions = new ArrayList<>();
                    Intent intent = getIntent();
                    quizTopic = intent.getStringExtra("TOPIC");
                    for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        Question question = postSnapShot.getValue(Question.class);
                        tmpQuestions.add(question);
                    }
                    for (int i = 0; i < tmpQuestions.size(); i++) {
                        if (tmpQuestions.get(i).getQuestionTopic().equals(quizTopic)) {
                            quizQuestions.add(tmpQuestions.get(i));
                        }
                    }
                    //populate question
                    String currentQuestion = quizQuestions.get(quizQuestionCount).getQuestion();
                    textViewQuestion.setText(currentQuestion);
                    //populate answers
                    ArrayList<String> answers = new ArrayList<>(quizQuestions.get(quizQuestionCount).getAnswers());
                    initRecyclerViewAnswers(answers);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void initRecyclerViewAnswers(ArrayList<String> answers) {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewAnswers);
        AnswersAdapter answersAdapter = new AnswersAdapter(this, answers, rootExplanationConstraint, rootReferenceConstraint);
        recyclerView.setAdapter(answersAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
