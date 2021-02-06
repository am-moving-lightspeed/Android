package com.oaoaoa.battleships.activities;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

//import com.example.battleship.Enums.MapType;
//import com.example.battleship.GameLogic.Map;
//import com.example.battleship.GameLogic.MapHelpers.MapGenerator;
//import com.example.battleship.GameLogic.MapHelpers.MapValidator;
//import com.example.battleship.GameLogic.Ship;
//import com.example.battleship.Helpers.AnimationHelper;
//import com.example.battleship.Helpers.FirebaseHelper;
//import com.example.battleship.Helpers.IdGenerator;
//import com.example.battleship.Models.Game;
//import com.example.battleship.R;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oaoaoa.battleships.R;
import com.oaoaoa.battleships.misc.AnimationsProvider;
import com.oaoaoa.battleships.misc.IdentifierGenerator;
import com.oaoaoa.battleships.misc.MapManager;
import com.oaoaoa.battleships.models.Map;

import es.dmoral.toasty.Toasty;



public class GameStartedActivity extends AppCompatActivity {

    private EditText mSessionId;
    private Map      mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamestarted);

        AnimationsProvider.startBackgroundGradientAnimation(
            findViewById(R.id.constraintLayout_gameStarted_container)
        );

        boolean isHost    = getIntent().getBooleanExtra("isHost", true);
        boolean isAIEnemy = getIntent().getBooleanExtra("isAIEnemy", false);

        resolveControlsVisibility(isAIEnemy);

        mSessionId = findViewById(R.id.editText_gameStarted_sessionId);
        mSessionId.setText(IdentifierGenerator.generate());

        mMap = new Map();
        MapManager.initMapView(
            getApplicationContext(),
            (GridLayout) findViewById(R.id.gridLayout_gameStarted),
            mMap
        );

        Button bLoadMap        = findViewById(R.id.button_gameStarted_loadMap);
        Button bGenerateMap    = findViewById(R.id.button_gameStarted_generateMap);
        Button bStartBattle    = findViewById(R.id.button_gameStarted_startBattle);
        Button bStartBattleAlt = findViewById(R.id.button_gameStarted_startBattleAlt);

        setLoadMapButtonEvents(bLoadMap);
        setGenerateMapButtonEvents(bGenerateMap);
    }


    // region Events
    private void setLoadMapButtonEvents(View view) {

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                SharedPreferences preferences =
                    getSharedPreferences("settings", Context.MODE_PRIVATE);
                String serializedMap = preferences.getString("map", null);

                if (serializedMap != null) {
                    mMap = new Gson().fromJson(serializedMap, new TypeToken<Map>() {}.getType());
                }
                if (mMap == null) {
                    Toasty.custom(GameStartedActivity.this,
                                  R.string.gameStarted_toasty,
                                  null,
                                  R.color.colour_maroon,
                                  Toast.LENGTH_SHORT,
                                  false,
                                  true)
                          .show();
                }
                else {
                    MapManager.initMapView(
                        getApplicationContext(),
                        (GridLayout) findViewById(R.id.gridLayout_gameStarted),
                        mMap
                    );
                }
            }
        });
    }


    private void setGenerateMapButtonEvents(View view) {

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                mMap = MapManager.generateMap();
                MapManager.initMapView(
                    getApplicationContext(),
                    (GridLayout) findViewById(R.id.gridLayout_gameStarted),
                    mMap
                );
            }
        });
    }
    // endregion


    private void resolveControlsVisibility(boolean isAIEnemy) {

        if (isAIEnemy) {
            findViewById(R.id.editText_gameStarted_sessionId).setVisibility(View.GONE);
            findViewById(R.id.linearLayout_gameStarted_buttonsBar).setVisibility(View.GONE);
            findViewById(R.id.button_gameStarted_startBattleAlt).setVisibility(View.VISIBLE);
        }
    }

}