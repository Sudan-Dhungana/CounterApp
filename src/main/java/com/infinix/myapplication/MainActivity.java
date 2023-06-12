package com.infinix.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Declare and initialize the views
    private TextView countTextView;
    private Button startStopButton;

    // Declare and initialize the timer variable
    private CountDownTimer timer;
    private TextToSpeech tts;

    // Declare and initialize the timer duration (10 seconds)
    private final long timerDuration = 11000;

    // Declare and initialize the count variable
    private int count = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the references of the views by their IDs
        countTextView = findViewById(R.id.count_text_view);
        Button increaseButton = findViewById(R.id.increase_button);
        Button decreaseButton = findViewById(R.id.decrease_button);
        Button resetButton = findViewById(R.id.reset_button);
        startStopButton = findViewById(R.id.start_stop_button);

        countTextView.setText("10");

        // Create a new TextToSpeech object and pass this as the context and listener
        tts = new TextToSpeech(this, this::onInit);

        // Set onClickListeners on the buttons
        increaseButton.setOnClickListener(v -> {
            // Call the increment method
            increment();
        });

        decreaseButton.setOnClickListener(v -> {
            // Call the decrement method
            decrement();
        });

        // Set onClickListener on the button
        startStopButton.setOnClickListener(v -> {
            // Check the text of the button
            if (startStopButton.getText().equals("Start")) {
                // Call the startTimer method
                startTimer();
            } else {
                // Call the stopTimer method
                stopTimer();
            }
        });

        resetButton.setOnClickListener(v -> {
            count = 0;
            countTextView.setText("0");
        });
    }
    // Define the startTimer method
    @SuppressLint("SetTextI18n")
    private void startTimer() {
        // Create a new CountDownTimer object with the timer duration and 1 second interval
        timer = new CountDownTimer(timerDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the text of the TextView to display the remaining time in seconds
                countTextView.setText(String.valueOf(millisUntilFinished / 1000));
                // Use the speak method to say the remaining time in seconds
                tts.speak(String.valueOf(millisUntilFinished / 1000),
                        TextToSpeech.QUEUE_FLUSH, null, null);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                // Update the text of the TextView to display "0"
                countTextView.setText("0");
                // Use the speak method to say "Finished"
                tts.speak("CountDown, Finished", TextToSpeech.QUEUE_FLUSH,
                        null,null);
                // Update the text of the button to display "Start"
                startStopButton.setText("Start");
                // Update the text of the button to display "Start"
                startStopButton.setText("Start");
            }
        };

        // Start the timer
        timer.start();

        // Update the text of the button to display "Stop"
        startStopButton.setText("Stop");
    }

    // Define the stopTimer method
    @SuppressLint("SetTextI18n")
    private void stopTimer() {
            // Cancel the timer
            timer.cancel();

            // Update the text of the button to display "Start"
            startStopButton.setText("Start");
    }

    // Define the increment method
    private void increment() {
        // Increase the count by one
        count++;
        // Set the text of the TextView to display the new value
        countTextView.setText(String.valueOf(count));
    }

    // Define the decrement method
    private void decrement() {
        // Decrease the count by one
        count--;
        // Set the text of the TextView to display the new value
        countTextView.setText(String.valueOf(count));
    }

    public void onInit(int status) {
        // Check if TextToSpeech is successfully initialized
        if (status == TextToSpeech.SUCCESS) {
            // Set the language and locale for TextToSpeech
            tts.setLanguage(Locale.US);
        }
    }

    @Override
    protected void onDestroy() {
        // Don't forget to shutdown TextToSpeech!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }

        super.onDestroy();
    }
}
