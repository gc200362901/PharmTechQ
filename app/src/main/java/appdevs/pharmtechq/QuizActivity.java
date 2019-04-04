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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import appdevs.pharmtechq.adapters.AnswersAdapter;
import appdevs.pharmtechq.adapters.ReferencesAdapter;
import appdevs.pharmtechq.models.Question;

public class QuizActivity extends AppCompatActivity {

    private Toolbar toolBar;
    private FirebaseAuth authDb;
    private DatabaseReference db;
    public static ArrayList<Question> quizQuestions;
    public static int quizQuestionCount;
    public static String quizTopic;
    public static int correctAttempts;
    public static int totalAttempts ;
    private TextView textViewQuestion;
    private ConstraintLayout rootExplanationConstraint;
    private ConstraintLayout rootReferenceConstraint;
    private TextView textViewExplanation;
    private RecyclerView recyclerViewReferences;
    private Button buttonNext;
    private ProgressBar progressBarQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        quizQuestions = new ArrayList<>();
        //menu
        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        //database
        authDb = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference("questions");
        //views
        textViewQuestion = findViewById(R.id.textViewQuestion);
        rootExplanationConstraint = findViewById(R.id.rootExplanationConstraint);
        rootReferenceConstraint = findViewById(R.id.rootReferencesConstraint);
        textViewExplanation = findViewById(R.id.textViewExplanation);
        recyclerViewReferences = findViewById(R.id.recyclerViewReferences);
        buttonNext = findViewById(R.id.buttonNext);
        progressBarQuiz = findViewById(R.id.progressBarQuiz);

        //remove views until user clicks making them appear
        rootExplanationConstraint.setVisibility(View.GONE);
        rootReferenceConstraint.setVisibility(View.GONE);
        textViewExplanation.setVisibility(View.GONE);
        recyclerViewReferences.setVisibility(View.GONE);

        getTopicQuestion();
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

    private void getTopicQuestion() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Intent intent = getIntent();
                quizTopic = intent.getStringExtra("TOPIC");
                quizQuestions.clear();
                quizQuestionCount = 0;
                correctAttempts = 0;
                totalAttempts = 0;
                //tmpQuestions used to store all questions from question bank
                ArrayList<Question> tmpQuestions = new ArrayList<>();
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Question question = postSnapShot.getValue(Question.class);
                    tmpQuestions.add(question);
                }
                //get questions for chosen topic from tmpQuestions and store in quizQuestions
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
                //populate references
                ArrayList<String> references = new ArrayList<>(quizQuestions.get(quizQuestionCount).getReferences());
                initRecyclerViewReferences(references);

                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        quizQuestionCount += 1;
                        //quiz has more questions
                        //go to next question in quizQuestions arraylist
                        if(quizQuestionCount < quizQuestions.size()) {
                            textViewExplanation.setVisibility(View.GONE);
                            recyclerViewReferences.setVisibility(View.GONE);
                            rootReferenceConstraint.setVisibility(View.GONE);
                            rootExplanationConstraint.setVisibility(View.GONE);
                            //populate question
                            String currentQuestion = quizQuestions.get(quizQuestionCount).getQuestion();
                            textViewQuestion.setText(currentQuestion);
                            //populate answers
                            ArrayList<String> answers = new ArrayList<>(quizQuestions.get(quizQuestionCount).getAnswers());
                            initRecyclerViewAnswers(answers);
                            //populate references
                            ArrayList<String> references = new ArrayList<>(quizQuestions.get(quizQuestionCount).getReferences());
                            initRecyclerViewReferences(references);
                        }
                        //quiz is finished
                        // go to profile
                        else {
                            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                            intent.putExtra("TOPIC", quizTopic);
                            intent.putExtra("CORRECT_ATTEMPTS", correctAttempts);
                            intent.putExtra("TOTAL_ATTEMPTS", totalAttempts);
                            startActivity(intent);
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void initRecyclerViewAnswers(ArrayList<String> answers) {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewAnswers);
        AnswersAdapter answersAdapter = new AnswersAdapter(this, answers, rootExplanationConstraint,
                rootReferenceConstraint, textViewExplanation, recyclerViewReferences, progressBarQuiz, buttonNext);
        recyclerView.setAdapter(answersAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initRecyclerViewReferences(ArrayList<String> references) {
        RecyclerView recyclerViewReferences = findViewById(R.id.recyclerViewReferences);
        ReferencesAdapter referencesAdapter = new ReferencesAdapter(this, references);
        recyclerViewReferences.setAdapter(referencesAdapter);
        recyclerViewReferences.setLayoutManager(new LinearLayoutManager(this));
    }
}
