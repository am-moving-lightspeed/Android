package com.tabatatimer.handlers;


public interface ICrudButtonsHandler {

    void toggleCrudButtonsVisibility();
    void performAppearingAnimation();
    void performDisappearingAnimation();
    boolean areCrudButtonsHidden();

}
