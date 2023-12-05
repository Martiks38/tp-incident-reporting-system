package com.tp.application;

public class Notification {
    private NotificationStrategy strategy;

    public void setStrategy(NotificationStrategy strategy){
        this.strategy = strategy;
    }

    public void executeStrategy(String message){
        strategy.send(message);
    }

}
