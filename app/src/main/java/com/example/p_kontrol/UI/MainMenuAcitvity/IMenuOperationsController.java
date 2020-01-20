package com.example.p_kontrol.UI.MainMenuAcitvity;

interface IMenuOperationsController{
    /**
     * implements interface IMenuOperationsController.
     * to controll what happens when Profile Button is clicked
     * @return void
     */
    void menuBtn_profile();
    /**
     * implements interface IMenuOperationsController.
     * to controll what happens when FreePark Button is clicked
     */
    void menuBtn_FreePark();
    /**
     * implements interface IMenuOperationsController.
     * to controll what happens when Contribute Button is clicked
     */
    void menuBtn_Contribute();
    /**
     * implements interface IMenuOperationsController.
     * to controll what happens when Feedback Button is clicked
     */
    void menuBtn_FeedBack();
    /**
     * implements interface IMenuOperationsController.
     * to controll what happens when Parking Button is clicked
     */
    void menuBtn_Parking();
    /**
     * implements interface IMenuOperationsController.
     * to controll what happens when P-Vagt Button is clicked
     */
    void menuBtn_PVagt();
}