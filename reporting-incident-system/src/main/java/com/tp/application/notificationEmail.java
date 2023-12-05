package com.tp.application;

public class notificationEmail implements NotificationStrategy{
    @Override
    public void send(String message) {
        System.out.println("Email: " + message);
    }
}
