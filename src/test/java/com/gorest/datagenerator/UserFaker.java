package com.gorest.datagenerator;

import com.github.javafaker.Faker;
import com.gorest.models.UserPojo;

public class UserFaker {
    static Faker faker = new Faker();
    public static UserPojo generateUser() {
        return new UserPojo(
                 faker.name().fullName()
                ,faker.internet().emailAddress()
                ,faker.demographic().sex().toLowerCase()
                ,"active");
    }

}
