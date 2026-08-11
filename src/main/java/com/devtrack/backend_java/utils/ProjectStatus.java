package com.devtrack.backend_java.utils;

public enum ProjectStatus {

    New("New"),

    Done("Done"),

    InProgress("In Progress"),

    OnHold("On Hold"),
    ShutDown("Shut Down");

    private String description;

    private ProjectStatus(String description){
        this.description = description;
    }

    public String getStatusDescription(){
        return description;
    }

}
