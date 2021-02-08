package com.oaoaoa.battleships.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.oaoaoa.battleships.R;

import java.util.Locale;



public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        int     playerScore = getIntent().getIntExtra("playerScore", -1);
        int     enemyScore  = getIntent().getIntExtra("enemyScore", -1);
        boolean hasWon      = getIntent().getBooleanExtra("hasWon", false);

        TextView tvPlayerScoreView = findViewById(R.id.textView_results_playerScore);
        TextView tvEnemyScoreView  = findViewById(R.id.textView_results_enemyScore);
        TextView tvHeader          = findViewById(R.id.textView_results_header);
        Button   bBack             = findViewById(R.id.button_results);

        tvPlayerScoreView.setText(String.format(Locale.US, "%d", playerScore));
        tvEnemyScoreView.setText(String.format(Locale.US, "%d", enemyScore));
        tvHeader.setText(String.format(Locale.US, "%b", hasWon));

        bBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
                startActivity(intent);
                ResultsActivity.this.finish();
            }
        });
    }

}
