package com.example.tpandroid.retrofit.responses;

public class RegisterEventResponse {
    private Boolean success;
    private String  env;
    private EventResponse  event;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public EventResponse getData() {
        return event;
    }
}
