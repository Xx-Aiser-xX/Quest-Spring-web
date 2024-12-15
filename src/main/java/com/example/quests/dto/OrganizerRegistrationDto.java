package com.example.quests.dto;

public class OrganizerRegistrationDto{
    private String name;
    private String email;
    private String phone;
    private String city;
    private String description;
    private String password;
    private String confirmPassword;
    private String photoUrl;

    public OrganizerRegistrationDto(String name, String email, String phone, String city, String description, String password, String confirmPassword, String photoUrl) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.description = description;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
