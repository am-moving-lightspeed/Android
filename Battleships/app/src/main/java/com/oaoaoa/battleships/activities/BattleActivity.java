package com.oaoaoa.battleships.activities;


import android.content.Intent;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oaoaoa.battleships.R;
import com.oaoaoa.battleships.misc.AnimationsProvider;
import com.oaoaoa.battleships.misc.MapManager;
import com.oaoaoa.battleships.models.GregoryBot;
import com.oaoaoa.battleships.models.Map;
import com.oaoaoa.battleships.models.states.Cell;
import com.oaoaoa.battleships.models.states.MapMode;
import com.oaoaoa.battleships.models.states.SessionState;
import com.oaoaoa.battleships.misc.Constants.FirebaseConstants;
import com.oaoaoa.battleships.models.states.IntentStatus;
import com.oaoaoa.battleships.models.states.Turn;

import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;



public class BattleActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;

    private DatabaseReference mSession;
    private DatabaseReference mPlayerName;
    private DatabaseReference mEnemyName;
    private DatabaseReference mSessionIntent;
    private DatabaseReference mSessionState;
    private DatabaseReference mTurn;

    private GregoryBot mGregoryBot;

    private Map mPlayerMap;
    private Map mEnemyMap;

    private String mSessionId;
    private String mPlayerNameString;
    private String mEnemyNameString;

    private boolean mIsHost;
    private boolean mIsAIEnemy;

    private Date mDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        AnimationsProvider.startBackgroundGradientAnimation(
            findViewById(R.id.constraintLayout_battle_container)
        );

        mSessionId = getIntent().getStringExtra("sessionId");
        mIsHost    = getIntent().getBooleanExtra("isHost", true);
        mIsAIEnemy = getIntent().getBooleanExtra("isAIEnemy", true);
        mPlayerMap = (Map) getIntent().getSerializableExtra("map");
        mEnemyMap  = new Map();
        mDate      = new Date();

        mDatabase = FirebaseDatabase.getInstance();

        mSession = mDatabase.getReference(FirebaseConstants.SESSION)
                            .child(mSessionId);

        mPlayerName    = mIsHost ?
                         mSession.child(FirebaseConstants.SESSION_HOST) :
                         mSession.child(FirebaseConstants.SESSION_NONHOST);
        mEnemyName     = mIsHost ?
                         mSession.child(FirebaseConstants.SESSION_NONHOST) :
                         mSession.child(FirebaseConstants.SESSION_HOST);
        mTurn          = mSession.child(FirebaseConstants.SESSION_TURN);
        mSessionIntent = mSession.child(FirebaseConstants.SESSION_INTENT);
        mSessionState  = mSession.child(FirebaseConstants.SESSION_STATE);

        mSessionState.setValue(SessionState.AWAITING);

        setPlayerTurnEvents();
        setSessionIntentEvents();
        setSessionStateEvents();

        if (mIsHost) {
            setWaitingEnemyEvents();
        }
        else {
            setEnemyJoinedEvents();
            mPlayerName.setValue(getIntent().getStringExtra("name"));
        }

        MapManager.setOnShotPerformedListener(new MapManager.OnShotPerformedListener() {

            @Override
            public void onShotPerformed(int x, int y) {

                Turn turn = mIsHost ? Turn.HOST : Turn.NONHOST;

                String intent = String.format(Locale.US,
                                              "%d:%d#%s#%s",
                                              x,
                                              y,
                                              turn.name(),
                                              IntentStatus.PENDING.name());

                mSessionIntent.setValue(intent);
            }
        });
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();

        MapManager.removeOnShotPerformedListener();

        mSession.child(FirebaseConstants.SESSION_STATE)
                .setValue(SessionState.ENDED);
        mSession.child(FirebaseConstants.SESSION_TURN)
                .removeValue();
        mSession.child(FirebaseConstants.SESSION_INTENT)
                .removeValue();
    }


    // region Events
    private void setWaitingEnemyEvents() {

        mEnemyName.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!snapshot.exists()) {
                    return;
                }

                String   enemyName = snapshot.getValue(String.class);
                TextView header    = findViewById(R.id.textView_battle_headerEnemy);
                header.setText(enemyName);
                mEnemyNameString = enemyName;

                String playerName = getIntent().getStringExtra("name");
                mPlayerName.setValue(playerName);
                mPlayerNameString = playerName;

                mSessionState.setValue(SessionState.STARTED);
                mSession.child(FirebaseConstants.SESSION_DATETIME)
                        .setValue(mDate.toString());
                mTurn.setValue(Turn.HOST);

                mEnemyName.removeEventListener(this);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


    private void setEnemyJoinedEvents() {

        mEnemyName.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!snapshot.exists()) {
                    return;
                }

                TextView header = findViewById(R.id.textView_battle_headerEnemy);
                header.setText(snapshot.getValue(String.class));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


    private void setSessionStateEvents() {

        mSessionState.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!snapshot.exists()) {
                    return;
                }

                SessionState state = snapshot.getValue(SessionState.class);
                if (state == SessionState.STARTED) {
                    showToast(
                        R.string.battle_toasty_started,
                        R.color.colour_lightSeaGreen
                    );
                }
                else if (state == SessionState.ENDED) {

                    if (mIsHost) {
                        saveGameResults();
                    }

                    Intent intent = new Intent(BattleActivity.this, ResultsActivity.class);
                    intent.putExtra("playerScore", mPlayerMap.getShipsLeft());
                    intent.putExtra("enemyScore", mEnemyMap.getShipsLeft());
                    intent.putExtra("hasWon",
                                    mPlayerMap.getShipsLeft() > mEnemyMap.getShipsLeft());
                    startActivity(intent);

                    BattleActivity.this.finish();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


    private void setPlayerTurnEvents() {

        mTurn.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!snapshot.exists()) {
                    return;
                }

                Turn turn = snapshot.getValue(Turn.class);

                if ((mIsHost && turn == Turn.HOST) ||
                    (!mIsHost && turn == Turn.NONHOST)) {

                    MapManager.initMapView(
                        getApplicationContext(),
                        (GridLayout) findViewById(R.id.gridLayout_battle_player),
                        mPlayerMap,
                        MapMode.INACTIVE
                    );
                    MapManager.initMapView(
                        getApplicationContext(),
                        (GridLayout) findViewById(R.id.gridLayout_battle_enemy),
                        mEnemyMap,
                        MapMode.BATTLE
                    );

                    showToast(
                        R.string.battle_toasty_yourTurn,
                        R.color.colour_lightSeaGreen
                    );
                }
                else {
                    MapManager.initMapView(
                        getApplicationContext(),
                        (GridLayout) findViewById(R.id.gridLayout_battle_player),
                        mPlayerMap,
                        MapMode.INACTIVE
                    );
                    MapManager.initMapView(
                        getApplicationContext(),
                        (GridLayout) findViewById(R.id.gridLayout_battle_enemy),
                        mEnemyMap,
                        MapMode.INACTIVE
                    );

                    showToast(
                        R.string.battle_toasty_enemyTurn,
                        R.color.colour_maroon
                    );
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


    private void setSessionIntentEvents() {

        mSessionIntent.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!snapshot.exists()) {
                    return;
                }

                // intent: x:y#Turn#IntentStatus
                String[]     intent       = snapshot.getValue(String.class).split("#");
                IntentStatus intentStatus = IntentStatus.valueOf(intent[2]);
                Turn         turn         = Turn.valueOf(intent[1]);
                int          x            = Integer.parseInt(intent[0].split(":")[0]);
                int          y            = Integer.parseInt(intent[0].split(":")[1]);

                if (intentStatus == IntentStatus.PENDING) {
                    // Enemy shoots you.
                    if ((mIsHost && turn != Turn.HOST) ||
                        (!mIsHost && turn != Turn.NONHOST)) {

                        Cell cellStatus = MapManager.resolveShot(mPlayerMap, x, y);

                        if (mPlayerMap.getShipsLeft() == 0) {
                            mSessionState.setValue(SessionState.ENDED);
                        }

                        IntentStatus newIntentStatus = cellStatus == Cell.HIT ?
                                                       IntentStatus.RESOLVED_HIT :
                                                       IntentStatus.RESOLVED_MISS;

                        String newIntent = String.format(Locale.US,
                                                         "%d:%d#%s#%s",
                                                         x,
                                                         y,
                                                         turn.name(),
                                                         newIntentStatus.name());

                        mSessionIntent.setValue(newIntent);
                    }
                }
                else {
                    if ((mIsHost && turn == Turn.HOST) ||
                        (!mIsHost && turn == Turn.NONHOST)) {

                        if (intentStatus == IntentStatus.RESOLVED_HIT) {
                            mEnemyMap.setCell(x, y, Cell.HIT);
                            mEnemyMap.decreaseShips();
                        }
                        else {
                            mEnemyMap.setCell(x, y, Cell.MISS);
                        }

                        if (mIsHost) {
                            mTurn.setValue(Turn.NONHOST);
                        }
                        else {
                            mTurn.setValue(Turn.HOST);
                        }
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
    // endregion


    private void showToast(int text, int colour) {

        Toasty.custom(BattleActivity.this,
                      text,
                      null,
                      colour,
                      Toast.LENGTH_SHORT,
                      false,
                      true)
              .show();
    }


    private void saveGameResults() {

        DatabaseReference stats = mDatabase.getReference(FirebaseConstants.STATISTICS)
                                           .child(mSessionId);
        stats.child(FirebaseConstants.STATISTICS_HOST)
             .setValue(mPlayerNameString);
        stats.child(FirebaseConstants.STATISTICS_NONHOST)
             .setValue(mEnemyNameString);
        stats.child(FirebaseConstants.STATISTICS_HOST_SCORE)
             .setValue(mPlayerMap.getShipsLeft());
        stats.child(FirebaseConstants.STATISTICS_NONHOST_SCORE)
             .setValue(mEnemyMap.getShipsLeft());
        stats.child(FirebaseConstants.STATISTICS_DATETIME)
             .setValue(mDate.toString());
    }

}
