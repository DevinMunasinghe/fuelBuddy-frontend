package com.example.fuelapp.APIManager;

public class Globaldata {

    private static Globaldata instance = new Globaldata();

    public static Globaldata getInstance() {
        return instance;
    }

    public static void setInstance(Globaldata instance) {
        Globaldata.instance = instance;
    }

    private String notification_indexOne;
    private String notification_indexTwo;
    private String notification_indexThree;
    private String Notification_indexFour;

    private Globaldata() {
    }

    public String getNotification_indexOne() {
        return notification_indexOne;
    }

    public void setNotification_indexOne(String notification_indexOne) {
        this.notification_indexOne = notification_indexOne;
    }

    public String getNotification_indexTwo() {
        return notification_indexTwo;
    }

    public void setNotification_indexTwo(String notification_indexTwo) {
        this.notification_indexTwo = notification_indexTwo;
    }

    public String getNotification_indexThree() {
        return notification_indexThree;
    }

    public void setNotification_indexThree(String notification_indexThree) {
        this.notification_indexThree = notification_indexThree;
    }

    public String getNotification_indexFour() {
        return Notification_indexFour;
    }

    public void setNotification_indexFour(String notification_indexFour) {
        Notification_indexFour = notification_indexFour;
    }
}
