package com.example.p_kontrol.UI.MainMenuAcitvity;

interface IMenuOperator{
    /**
     *  is Called mainly from the Menu Handle.
     *  toggleMenu opens and closes the Menu repeatedly
     * */
    void toggleMenu();
    /**
     *  closes the menu
     * */
    void closeMenu();
    /**
     *  opens the menu
     * */
    void openMenu();
    /**
     *  returns false if map is closed, true if it is open.
     * */
    boolean isMenuOpen();
    /**
     *  Enables and Disables the FreePark State.
     *  Free Park is a Filter state that only shows tips in the category of free tips.
     * */
    void toggleMenuBtnFreePark();
    /**
     * Enables and Disables the Parking State
     * Parking state is a state where you place down your location as a parking location,
     * and that location is then set to listens for P alerts.
     * */
    void toggleMenuBtnParking();

    /**
     * shows or hides the Menu Container handle with an animation
     * */
    void setMenuHandleVisibility(int visibility);
    /**
     * is Used to disable all toggled menu Buttons
     * */
    void deToggleMenuButton();
}