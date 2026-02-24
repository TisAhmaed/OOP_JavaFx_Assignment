package org.example.oop_and_javafx;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private String department;
    private String major;
    private String email;
    private String imageUrl;

    public Student(int id, String firstName, String lastName,
                   String department, String major, String email,String imageUrl) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.major = major;
        this.email = email;
        this.imageUrl= imageUrl;

    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDepartment() {
        return department;
    }

    public String getMajor() {
        return major;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String v) {
        firstName = v;
    }

    public void setLastName(String v) {
        lastName = v;
    }

    public void setDepartment(String v) {
        department = v;
    }

    public void setMajor(String v) {
        major = v;
    }

    public void setEmail(String v) {
        email = v;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


}
