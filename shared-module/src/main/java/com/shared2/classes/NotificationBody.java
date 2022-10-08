package com.shared2.classes;

public class NotificationBody {
    private String pesel;
    private String email;
    private String message;

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationBody(String pesel, String email, String message) {
        this.pesel = pesel;
        this.email = email;
        this.message = message;
    }
}
