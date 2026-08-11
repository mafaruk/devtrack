package com.devtrack.backend_java.utils;

public enum TaskStatus {

    New("New"),
    Done("Done"),
    InProgress("In Progress"),
    OnHold("On Hold"),
    Review("Review"),
    InTest("In Test");

    private String description;

    private TaskStatus(String description){
        this.description = description;
    }

    public String getStatusDescription(){
        return description;
    }

}
