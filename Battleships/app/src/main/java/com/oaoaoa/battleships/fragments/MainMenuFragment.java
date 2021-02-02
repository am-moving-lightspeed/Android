package com.oaoaoa.battleships.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.oaoaoa.battleships.R;
import com.oaoaoa.battleships.activities.ProfileActivity;
import com.oaoaoa.battleships.activities.StatisticsActivity;



public class MainMenuFragment extends Fragment {

    public MainMenuFragment() {

        super(R.layout.fragment_main_menu);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        Button bStartGame  = view.findViewById(R.id.button_main_startGame);
        Button bProfile    = view.findViewById(R.id.button_main_profile);
        Button bStatistics = view.findViewById(R.id.button_main_statistics);
        Button bQuitGame   = view.findViewById(R.id.button_main_quitGame);

        // region Events
        bStartGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                requireActivity().getSupportFragmentManager()
                                 .beginTransaction()
                                 .setReorderingAllowed(true)
                                 .replace(R.id.fragmentContainerView_main,
                                          StartGameMenuFragment.class,
                                          null)
                                 .commit();
            }
        });

        bProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(requireActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        bStatistics.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(requireActivity(), StatisticsActivity.class);
                startActivity(intent);
            }
        });

        bQuitGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                requireActivity().finish();
                System.exit(0);
            }
        });
        // endregion
    }

}
