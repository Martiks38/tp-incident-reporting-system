package com.tp.application;

public class NotificationWhatsApp implements NotificationStrategy {

    @Override
    public void send(String message) {
        System.out.println("WhatsApp: " + message);
    }
}
