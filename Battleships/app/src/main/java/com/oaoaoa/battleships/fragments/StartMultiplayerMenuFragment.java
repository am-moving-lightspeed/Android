package com.oaoaoa.battleships.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.oaoaoa.battleships.R;
import com.oaoaoa.battleships.activities.GameStartedActivity;



public class StartMultiplayerMenuFragment extends Fragment {

    public StartMultiplayerMenuFragment() {

        super(R.layout.fragment_main_menu_startmultiplayer);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        Button bCreate  = view.findViewById(R.id.button_main_createSession);
        Button bConnect = view.findViewById(R.id.button_main_connectToSession);

        bCreate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(requireActivity(), GameStartedActivity.class);
                intent.putExtra("isHost", true);
                intent.putExtra("isAIEnemy", false);
                startActivity(intent);
            }
        });

        bConnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(requireActivity(), GameStartedActivity.class);
                intent.putExtra("isHost", false);
                intent.putExtra("isAIEnemy", false);
                startActivity(intent);
            }
        });
    }

}
