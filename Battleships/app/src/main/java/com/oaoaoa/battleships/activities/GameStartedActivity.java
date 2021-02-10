package com.oaoaoa.battleships.activities;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oaoaoa.battleships.R;
import com.oaoaoa.battleships.misc.AnimationsProvider;
import com.oaoaoa.battleships.misc.IdentifierGenerator;
import com.oaoaoa.battleships.misc.MapManager;
import com.oaoaoa.battleships.models.Map;
import com.oaoaoa.battleships.models.states.MapMode;

import es.dmoral.toasty.Toasty;



public class GameStartedActivity extends AppCompatActivity {

    private EditText mSessionId;
    private Map      mMap;

    private boolean mIsHost;
    private boolean mIsAIEnemy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamestarted);

        AnimationsProvider.startBackgroundGradientAnimation(
            findViewById(R.id.constraintLayout_gameStarted_container)
        );

        mIsHost    = getIntent().getBooleanExtra("isHost", true);
        mIsAIEnemy = getIntent().getBooleanExtra("isAIEnemy", true);

        resolveControlsVisibility(mIsHost, mIsAIEnemy);

        mMap = new Map();
        MapManager.initMapView(
            getApplicationContext(),
            (GridLayout) findViewById(R.id.gridLayout_gameStarted),
            mMap,
            MapMode.CREATURE
        );

        Button bLoadMap     = findViewById(R.id.button_gameStarted_loadMap);
        Button bGenerateMap = findViewById(R.id.button_gameStarted_generateMap);
        Button bStartBattle;

        mSessionId = findViewById(R.id.editText_gameStarted_sessionId);

        if (mIsHost) {
            Button bShareId = findViewById(R.id.button_gameStarted_shareSessionId);
            Button bCopyId  = findViewById(R.id.button_gameStarted_copySessionId);
            bStartBattle = findViewById(R.id.button_gameStarted_startBattle);

            mSessionId.setText(IdentifierGenerator.generate());

            setCopyIdButtonEvents(bCopyId);
            setShareIdButtonEvents(bShareId);
        }
        else {
            mSessionId.setFocusable(true);
            mSessionId.setCursorVisible(true);
            mSessionId.setLongClickable(true);
            // TODO: resolve problem w/ typing

            bStartBattle = findViewById(R.id.button_gameStarted_startBattleAlt);
        }

        setLoadMapButtonEvents(bLoadMap);
        setGenerateMapButtonEvents(bGenerateMap);
        setStartBattleButtonEvents(bStartBattle);
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
                    showToast(R.string.gameStarted_toasty_noSavedMap);
                }
                else {
                    MapManager.initMapView(
                        getApplicationContext(),
                        (GridLayout) findViewById(R.id.gridLayout_gameStarted),
                        mMap,
                        MapMode.CREATURE
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
                    mMap,
                    MapMode.CREATURE
                );
            }
        });
    }


    private void setCopyIdButtonEvents(View view) {

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                ClipboardManager cbManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                EditText         etId      = findViewById(R.id.editText_gameStarted_sessionId);
                cbManager.setPrimaryClip(ClipData.newPlainText("Session ID", etId.getText()));
            }
        });
    }


    private void setShareIdButtonEvents(View view) {

//            bShareId.setOnClickListener(v ->
//                                            ShareCompat.IntentBuilder.from(this)
//                                                                     .setType("text/plain")
//                                                                     .setChooserTitle("Chooser title")
//                                                                     .setText(mSessionId.getText()
//                                                                                        .toString())
//                                                                     .startChooser());
    }


    private void setStartBattleButtonEvents(View view) {

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (MapManager.isValid(mMap)) {
                    final FirebaseUser user = FirebaseAuth.getInstance()
                                                          .getCurrentUser();

                    if (user != null) {
                        Intent intent = new Intent(GameStartedActivity.this,
                                                   BattleActivity.class);
                        intent.putExtra("sessionId", mSessionId.getText().toString());
                        intent.putExtra("isHost", mIsHost);
                        intent.putExtra("isAIEnemy", mIsAIEnemy);
                        intent.putExtra("name", user.getEmail().split("@")[0]);
                        intent.putExtra("map", mMap);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        // Not expected to get here.
                        showToast(R.string.all_toasty_signInRequired);
                    }
                }
                else {
                    showToast(R.string.all_toasty_invalidMap);
                }
            }
        });
    }
    // endregion


    private void resolveControlsVisibility(boolean isHost, boolean isAIEnemy) {

        if (isAIEnemy) {
            findViewById(R.id.editText_gameStarted_sessionId).setVisibility(View.GONE);
            findViewById(R.id.linearLayout_gameStarted_buttonsBar).setVisibility(View.GONE);
            findViewById(R.id.button_gameStarted_startBattleAlt).setVisibility(View.VISIBLE);
        }
        else if (!isHost) {
            findViewById(R.id.linearLayout_gameStarted_buttonsBar).setVisibility(View.GONE);
            findViewById(R.id.button_gameStarted_startBattleAlt).setVisibility(View.VISIBLE);
        }
    }


    private void showToast(int text) {

        Toasty.custom(GameStartedActivity.this,
                      text,
                      null,
                      R.color.colour_maroon,
                      Toast.LENGTH_SHORT,
                      false,
                      true)
              .show();
    }

}