package com.oaoaoa.battleships.activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oaoaoa.battleships.R;
import com.oaoaoa.battleships.misc.AnimationsProvider;
import com.oaoaoa.battleships.misc.MapManager;
import com.oaoaoa.battleships.models.Map;

import es.dmoral.toasty.Toasty;



public class MapEditorActivity extends AppCompatActivity {

    private Map mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapeditor);

        AnimationsProvider.startBackgroundGradientAnimation(
            findViewById(R.id.constraintLayout_mapEditor_container)
        );

        final SharedPreferences preferences   = getSharedPreferences("settings", Context.MODE_PRIVATE);
        String                  serializedMap = preferences.getString("map", null);

        if (serializedMap != null) {
            mMap = new Gson().fromJson(serializedMap, new TypeToken<Map>() {}.getType());
        }
        if (mMap == null) {
            mMap = new Map();
        }

        GridLayout grid = findViewById(R.id.gridLayout_mapEditor);
        MapManager.initMapView(getApplicationContext(), grid, mMap);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                View cell = grid.findViewWithTag("imageButton_mapEditor_cell" + i + j);

                cell.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        MapManager.setShip(getApplicationContext(), view, mMap);
                    }
                });
            }
        }


        Button bSaveMap = findViewById(R.id.button_mapEditor_saveMap);
        bSaveMap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (MapManager.isValid(mMap)) {

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("map", new Gson().toJson(mMap));
                    editor.apply();

                    Intent intent = new Intent(MapEditorActivity.this, MainActivity.class);
                    intent.putExtra("fragment", "menu_startgame");
                    startActivity(intent);
                }
                else {
                    Toasty.custom(MapEditorActivity.this,
                                  R.string.mapEditor_toasty,
                                  null,
                                  R.color.colour_maroon,
                                  Toast.LENGTH_SHORT,
                                  false,
                                  true)
                          .show();
                }
            }
        });
    }


//    @Override
//    protected void onDestroy() {
//
//        super.onDestroy();
//        System.exit(0);
//    }

}
