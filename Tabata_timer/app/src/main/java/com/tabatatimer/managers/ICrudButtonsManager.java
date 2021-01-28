package com.tabatatimer.managers;


public interface ICrudButtonsManager {

    void toggleCrudButtonsVisibility();
    void performAppearingAnimation();
    void performDisappearingAnimation();
    boolean areCrudButtonsHidden();

}
