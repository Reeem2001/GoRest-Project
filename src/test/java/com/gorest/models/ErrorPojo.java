package com.gorest.models;

public class ErrorPojo {

    private String field;
    private String message;

    public ErrorPojo(){

    }

    public ErrorPojo(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
