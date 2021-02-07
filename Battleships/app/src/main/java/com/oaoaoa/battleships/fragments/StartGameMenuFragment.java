package com.oaoaoa.battleships.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.oaoaoa.battleships.R;
import com.oaoaoa.battleships.activities.GameStartedActivity;
import com.oaoaoa.battleships.activities.MapEditorActivity;



public class StartGameMenuFragment extends Fragment {

    public StartGameMenuFragment() {

        super(R.layout.fragment_main_menu_startgame);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        View bCustomizeMap      = view.findViewById(R.id.button_main_customizeMap);
        View bStartSingleplayer = view.findViewById(R.id.button_main_startSingleplayer);
        View bStartMultiplayer  = view.findViewById(R.id.button_main_startMultiplayer);

        // region Events
        bCustomizeMap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(requireActivity(), MapEditorActivity.class);
                startActivity(intent);
            }
        });

        bStartSingleplayer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(requireActivity(), GameStartedActivity.class);
                intent.putExtra("isAIEnemy", true);
                startActivity(intent);
            }
        });

//        bStartMultiplayer.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(requireActivity(), GameStartedActivity.class);
//                startActivity(intent);
//            }
//        });
        // endregion
    }

}
