package com.sls.dropwizarddemo;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class Employee {
    @NotNull
    private Integer id;
    @NotBlank @Length(min=2, max=255)
    private String firstName;
    @NotBlank @Length(min=2, max=255)
    private String lastName;
    @Pattern(regexp=".+@.+\\.[a-z]+")
    private String email;

    public Employee(){
    }

    public Employee(Integer id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Emplyee [id=" + id + ", firstName=" + firstName + ", lastName="
                + lastName + ", email=" + email + "]";
    }
}
