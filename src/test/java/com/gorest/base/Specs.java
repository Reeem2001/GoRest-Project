package com.gorest.base;

import com.gorest.data.Route;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Specs {
    public static String token = "5d801094647cc79f421464be025eb148aaff665deb32e2f807e44f20e68c3466";
    public static String getEnv(){
        String env = System.getProperty("env","PRODUCTION");
        String baseURL;
        switch (env){
            case "PRODUCTION":
                baseURL = Route.BASE_ROUTE;
                break;
            case "LOCAL":
                baseURL = "http://localhost:8080";
                break;
            default:
                throw new RuntimeException("Environment is not supported");
        }
        return baseURL;
    }

    public static RequestSpecification getRequestSpec(){
        return given()
                .baseUri(getEnv())
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .log().all();
    }
}
