package com.pulakdebasish.braintrainer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton, playAgainButton;
    TextView sumText, timerText, responseText, scoreboard, instructions;
    Integer locationOfCorrectAnswer;
    RelativeLayout gameLayout;
    Boolean gameOver = false;

    int correctAnswers = 0;
    int totalQuestions = 0;

    Button option0, option1, option2, option3;

    ArrayList<Integer> answers = new ArrayList<>();
    ArrayList<String> operator = new ArrayList<>();

    public void start(View view) {

        newQuestion();

        gameLayout = (RelativeLayout) findViewById(R.id.gameLayout);

        instructions = (TextView) findViewById(R.id.instructions);

        timerText = (TextView) findViewById(R.id.timerText);

        responseText = (TextView) findViewById(R.id.resultResponse);

        instructions.setVisibility(View.INVISIBLE);

        startButton.setVisibility(View.INVISIBLE);

        gameLayout.setVisibility(View.VISIBLE);

        scoreboard = (TextView) findViewById(R.id.scoreboard);

        new CountDownTimer(30100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerText.setText(String.valueOf(millisUntilFinished/1000 + "s"));
            }

            @Override
            public void onFinish() {
                timerText.setText("0s");
                responseText.setText("Your score is: " +
                        Integer.toString(correctAnswers) + "/" + Integer.toString(totalQuestions));
                playAgainButton.setVisibility(View.VISIBLE);
                gameOver = true;
            }
        }.start();

    }

    public void playAgain(View view) {

        playAgainButton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.VISIBLE);
        instructions.setVisibility(View.VISIBLE);
        correctAnswers = 0;
        totalQuestions = 0;
        timerText.setText("30s");
        scoreboard.setText("0/0");
        responseText.setText("");
        gameOver = false;
        newQuestion();
    }

    public void chooseAnswer(View view) {

        if(!gameOver) {

            Button clickedButton = (Button) view;

            responseText = (TextView) findViewById(R.id.resultResponse);

            int tagNumber = Integer.parseInt(clickedButton.getTag().toString());

            if (tagNumber == locationOfCorrectAnswer) {
                responseText.setText("Correct!");
                ++correctAnswers;
                ++totalQuestions;
            } else {
                responseText.setText("Incorrect");
                ++totalQuestions;
            }

            scoreboard.setText(Integer.toString(correctAnswers) + "/" + Integer.toString(totalQuestions));

            newQuestion();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startButton = (Button) findViewById(R.id.startButton);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        sumText = (TextView) findViewById(R.id.sumText);

        option0 = (Button) findViewById(R.id.button);
        option1 = (Button) findViewById(R.id.button1);
        option2 = (Button) findViewById(R.id.button2);
        option3 = (Button) findViewById(R.id.button3);

        operator.add("+");
        operator.add("-");
        operator.add("x");

    }

    public void newQuestion(){

        answers.clear();

        Random rand = new Random();

        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        int correctAnswer = 0;
        int wrongAnswer, counter = 0;
        String op = operator.get(rand.nextInt(3)).toString();


        sumText.setText(Integer.toString(a) + " " + op + " " + Integer.toString(b));

        if(op.equals("+")){
            correctAnswer = a + b;
        }else if(op.equals("-")){
            correctAnswer = a - b;
        }else if(op.equals("x")){
            correctAnswer = a * b;
        }
        locationOfCorrectAnswer = rand.nextInt(4);

        for(int i = 0; i<4; i++) {

            if(i == locationOfCorrectAnswer) {
                answers.add(correctAnswer);
            }else {
                if(counter%2 == 0) wrongAnswer = correctAnswer + rand.nextInt(21);
                else wrongAnswer = correctAnswer - rand.nextInt(21);
                counter++;
                while (wrongAnswer == correctAnswer) {
                    if(counter%2 == 0) wrongAnswer = correctAnswer + rand.nextInt(21);
                    else wrongAnswer = correctAnswer - rand.nextInt(21);
                    counter++;
                }
                answers.add(wrongAnswer);
            }
        }

        option0.setText(Integer.toString(answers.get(0)));
        option1.setText(Integer.toString(answers.get(1)));
        option2.setText(Integer.toString(answers.get(2)));
        option3.setText(Integer.toString(answers.get(3)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
