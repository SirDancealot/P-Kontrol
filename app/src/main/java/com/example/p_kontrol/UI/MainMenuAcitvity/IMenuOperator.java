package com.example.p_kontrol.UI.MainMenuAcitvity;

interface IMenuOperator{
    void toggleMenu();
    void closeMenu();
    void openMenu();

    boolean isMenuOpen();


    void toggleFreeParkEnabled();
    void toggleParking();
    void toggleCreateTip();

}