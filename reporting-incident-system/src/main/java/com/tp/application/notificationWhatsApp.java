package com.tp.application;

public class notificationWhatsApp implements NotificationStrategy{
    @Override
    public void send(String message) {
        System.out.println("WhatsApp: " + message);
    }
}
