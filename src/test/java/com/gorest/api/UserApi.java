package com.gorest.api;

import com.gorest.base.Specs;
import com.gorest.data.Route;
import com.gorest.models.UserPojo;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserApi {
    public static Response createUser(UserPojo user){
        return given()
                .spec(Specs.getRequestSpec())
                .body(user)
                .when().post(Route.USERS_ROUTE)
                .then().log().all()
                .extract().response();
    }

    public static Response getUserByID(int userID){
        return given()
                .spec(Specs.getRequestSpec())
                .when().get(Route.USERS_ROUTE + "/" + userID )
                .then().log().all()
                .extract().response();
    }

    public static Response editeUserByID(UserPojo user , int userID){
        return given()
                .spec(Specs.getRequestSpec())
                .body(user)
                .when().put(Route.USERS_ROUTE + "/" + userID )
                .then().log().all()
                .extract().response();
    }

    public static Response deleteUserByID( int userID){
        return given()
                .spec(Specs.getRequestSpec())
                .when().delete(Route.USERS_ROUTE + "/" + userID )
                .then().log().all()
                .extract().response();
    }


}
