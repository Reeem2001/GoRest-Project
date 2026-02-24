package com.gorest.testcases;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gorest.api.UserApi;
import com.gorest.data.ErrorMessage;
import com.gorest.datagenerator.UserFaker;
import com.gorest.models.ErrorPojo;
import com.gorest.models.UserPojo;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class UserTest {
    // create user happy path
    @Test(description = "Create User With Valid Data")
    public void createUserWithValidData_TC1(){
        UserPojo user = UserFaker.generateUser();
        Response res = UserApi.createUser(user);
        UserPojo returnedUser = res.body().as(UserPojo.class);
        assertThat(res.statusCode(),equalTo(201));
        assertThat(returnedUser.getId(),not(equalTo(null)));
        assertThat(returnedUser.getName(),equalTo(user.getName()));
        assertThat(returnedUser.getEmail(),equalTo(user.getEmail()));
        assertThat(returnedUser.getGender(),equalTo(user.getGender()));
        assertThat(returnedUser.getStatus(),equalTo(user.getStatus()));
    }

    // create user blank name
    @Test(description = "Create User With Empty Name")
    public void createUserWithEmptyName_TC2(){
        UserPojo user = UserFaker.generateUser();
        user.setName("");
        Response res = UserApi.createUser(user);
        ObjectMapper mapper = new ObjectMapper();
        ErrorPojo returnedMessage = mapper.convertValue(res.body().path("[0]"),ErrorPojo.class);
        assertThat(res.statusCode(),equalTo(422));
        assertThat(returnedMessage.getField(),equalTo(ErrorMessage.NAME_FIELD));
        assertThat(returnedMessage.getMessage(),equalTo(ErrorMessage.NAME_CANNOT_BE_BLANK_ERROR));
    }

    // create user with null name
    @Test(description = "Create User With Null Name")
    public void createUserWithNullName_TC3(){
        UserPojo user = UserFaker.generateUser();
        user.setName(null);
        Response res = UserApi.createUser(user);
        ObjectMapper mapper = new ObjectMapper();
        ErrorPojo returnedMessage = mapper.convertValue(res.body().path("[0]"),ErrorPojo.class);
        assertThat(res.statusCode(),equalTo(422));
        assertThat(returnedMessage.getField(),equalTo(ErrorMessage.NAME_FIELD));
        assertThat(returnedMessage.getMessage(),equalTo(ErrorMessage.NAME_CANNOT_BE_BLANK_ERROR));
    }

    // create user number name
    @Test(description = "Create User With Number Name")
    public void createUserWithNumberName_TC4(){
        UserPojo user = UserFaker.generateUser();
        user.setName("112001");
        Response res = UserApi.createUser(user);
        ObjectMapper mapper = new ObjectMapper();
        ErrorPojo returnedMessage = mapper.convertValue(res.body().path("[0]"),ErrorPojo.class);
        assertThat(res.statusCode(),equalTo(422));
        assertThat(returnedMessage.getField(),equalTo(ErrorMessage.NAME_FIELD));
        assertThat(returnedMessage.getMessage(),equalTo(ErrorMessage.NAME_CANNOT_BE_NUMBER_ERROR));
    }

    // create user special character name
    @Test(description = "Create User With Special Character Name")
    public void createUserWithSpecialCharacterName_TC5(){
        UserPojo user = UserFaker.generateUser();
        user.setName("@");
        Response res = UserApi.createUser(user);
        ObjectMapper mapper = new ObjectMapper();
        ErrorPojo returnedMessage = mapper.convertValue(res.body().path("[0]"),ErrorPojo.class);
        assertThat(res.statusCode(),equalTo(422));
        assertThat(returnedMessage.getField(),equalTo(ErrorMessage.NAME_FIELD));
        assertThat(returnedMessage.getMessage(),equalTo(ErrorMessage.NAME_CANNOT_BE_SPECIAL_CHARACTER_ERROR));
    }

    // create user blank email
    @Test(description = "Create User With Empty Email")
    public void createUserWithEmptyEmail_TC6(){
        UserPojo user = UserFaker.generateUser();
        user.setEmail("");
        Response res = UserApi.createUser(user);
        ObjectMapper mapper = new ObjectMapper();
        ErrorPojo returnedMessage = mapper.convertValue(res.body().path("[0]"),ErrorPojo.class);
        assertThat(res.statusCode(),equalTo(422));
        assertThat(returnedMessage.getField(),equalTo(ErrorMessage.EMAIL_FIELD));
        assertThat(returnedMessage.getMessage(),equalTo(ErrorMessage.EMAIL_CANNOT_BE_BLANK_ERROR));
    }

    // create user invalid email without @
    @Test(description = "Create User With Invalid Email (without @)")
    public void createUserWithInvalidEmailWithoutAt_TC7(){
        UserPojo user = UserFaker.generateUser();
        user.setEmail("reemadel.com");
        Response res = UserApi.createUser(user);
        ObjectMapper mapper = new ObjectMapper();
        ErrorPojo returnedMessage = mapper.convertValue(res.body().path("[0]"),ErrorPojo.class);
        assertThat(res.statusCode(),equalTo(422));
        assertThat(returnedMessage.getField(),equalTo(ErrorMessage.EMAIL_FIELD));
        assertThat(returnedMessage.getMessage(),equalTo(ErrorMessage.EMAIL_CANNOT_BE_INVALID_ERROR));
    }

    // create user invalid email without domain
    @Test(description = "Create User With Invalid Email (without domain)")
    public void createUserWithInvalidEmailWithoutDomain_TC8(){
        UserPojo user = UserFaker.generateUser();
        user.setEmail("reemadel@");
        Response res = UserApi.createUser(user);
        ObjectMapper mapper = new ObjectMapper();
        ErrorPojo returnedMessage = mapper.convertValue(res.body().path("[0]"),ErrorPojo.class);
        assertThat(res.statusCode(),equalTo(422));
        assertThat(returnedMessage.getField(),equalTo(ErrorMessage.EMAIL_FIELD));
        assertThat(returnedMessage.getMessage(),equalTo(ErrorMessage.EMAIL_CANNOT_BE_INVALID_ERROR));
    }

    // create user invalid email start with numbers
    @Test(description = "Create User With Invalid Email (with numbers)")
    public void createUserWithInvalidEmailWithNumbers_TC9(){
        UserPojo user = UserFaker.generateUser();
        user.setEmail("11"+user.getEmail());
        Response res = UserApi.createUser(user);
        ObjectMapper mapper = new ObjectMapper();
        ErrorPojo returnedMessage = mapper.convertValue(res.body().path("[0]"),ErrorPojo.class);
        assertThat(res.statusCode(),equalTo(422));
        assertThat(returnedMessage.getField(),equalTo(ErrorMessage.EMAIL_FIELD));
        assertThat(returnedMessage.getMessage(),equalTo(ErrorMessage.EMAIL_CANNOT_BE_INVALID_ERROR));
    }

    // create user invalid email start with special character
    @Test(description = "Create User With Invalid Email (with special characters)")
    public void createUserWithInvalidEmailWithSpecialCharacter_TC10(){
        UserPojo user = UserFaker.generateUser();
        user.setEmail("!"+user.getEmail());
        Response res = UserApi.createUser(user);
        ObjectMapper mapper = new ObjectMapper();
        ErrorPojo returnedMessage = mapper.convertValue(res.body().path("[0]"),ErrorPojo.class);
        assertThat(res.statusCode(),equalTo(422));
        assertThat(returnedMessage.getField(),equalTo(ErrorMessage.EMAIL_FIELD));
        assertThat(returnedMessage.getMessage(),equalTo(ErrorMessage.EMAIL_CANNOT_BE_INVALID_ERROR));
    }

    // create user with existing email
    @Test(description = "Create User With Existing Email")
    public void createUserWithExistingEmail_TC11(){
        UserPojo user = UserFaker.generateUser();
        Response res0 = UserApi.createUser(user);
        assertThat(res0.statusCode(),equalTo(201));
        Response res = UserApi.createUser(user);
        ObjectMapper mapper = new ObjectMapper();
        ErrorPojo returnedMessage = mapper.convertValue(res.body().path("[0]"),ErrorPojo.class);
        assertThat(res.statusCode(),equalTo(422));
        assertThat(returnedMessage.getField(),equalTo(ErrorMessage.EMAIL_FIELD));
        assertThat(returnedMessage.getMessage(),equalTo(ErrorMessage.EMAIL_ALREADY_EXISTS_ERROR));
    }

    // create user blank geneder
    @Test(description = "Create User With Empty Gender")
    public void createUserWithEmptyGender_TC12(){
        UserPojo user = UserFaker.generateUser();
        user.setGender("");
        Response res = UserApi.createUser(user);
        ObjectMapper mapper = new ObjectMapper();
        ErrorPojo returnedMessage = mapper.convertValue(res.body().path("[0]"),ErrorPojo.class);
        assertThat(res.statusCode(),equalTo(422));
        assertThat(returnedMessage.getField(),equalTo(ErrorMessage.GENDER_FIELD));
        assertThat(returnedMessage.getMessage(),equalTo(ErrorMessage.GENDER_ERROR));
    }

    // create user invalid geneder
    @Test(description = "Create User With Invalid Gender")
    public void createUserWithInvalidGender_TC13(){
        UserPojo user = UserFaker.generateUser();
        user.setGender("reem");
        Response res = UserApi.createUser(user);
        ObjectMapper mapper = new ObjectMapper();
        ErrorPojo returnedMessage = mapper.convertValue(res.body().path("[0]"),ErrorPojo.class);
        assertThat(res.statusCode(),equalTo(422));
        assertThat(returnedMessage.getField(),equalTo(ErrorMessage.GENDER_FIELD));
        assertThat(returnedMessage.getMessage(),equalTo(ErrorMessage.GENDER_ERROR));
    }

    // create user blank status
    @Test(description = "Create User With Empty Status")
    public void createUserWithEmptyStatus_TC14(){
        UserPojo user = UserFaker.generateUser();
        user.setStatus("");
        Response res = UserApi.createUser(user);
        ObjectMapper mapper = new ObjectMapper();
        ErrorPojo returnedMessage = mapper.convertValue(res.body().path("[0]"),ErrorPojo.class);
        assertThat(res.statusCode(),equalTo(422));
        assertThat(returnedMessage.getField(),equalTo(ErrorMessage.STATUS_FIELD));
        assertThat(returnedMessage.getMessage(),equalTo(ErrorMessage.STATUS_ERROR));
    }

    // create user invalid status
    @Test(description = "Create User With Invalid Status")
    public void createUserWithInvalidStatus_TC15(){
        UserPojo user = UserFaker.generateUser();
        user.setStatus("good");
        Response res = UserApi.createUser(user);
        ObjectMapper mapper = new ObjectMapper();
        ErrorPojo returnedMessage = mapper.convertValue(res.body().path("[0]"),ErrorPojo.class);
        assertThat(res.statusCode(),equalTo(422));
        assertThat(returnedMessage.getField(),equalTo(ErrorMessage.STATUS_FIELD));
        assertThat(returnedMessage.getMessage(),equalTo(ErrorMessage.STATUS_ERROR));
    }


    // delete user happy path
    @Test(description = "Delete User With Valid ID")
    public void deleteUserWithValidID_TC16(){
        UserPojo user = UserFaker.generateUser();
        Response resCreate = UserApi.createUser(user);
        assertThat(resCreate.statusCode(),equalTo(201));
        int id = resCreate.as(UserPojo.class).getId();
        Response resDelete = UserApi.deleteUserByID(id);
        assertThat(resDelete.statusCode(),equalTo(204));
        Response resGet = UserApi.getUserByID(id);
        assertThat(resGet.statusCode(),equalTo(404));
    }

    // delete user invalid id
    @Test(description = "Delete User With Invalid ID")
    public void deleteUserWithInvalidID_TC17(){
        Response resDelete = UserApi.deleteUserByID("invalid".hashCode());
        assertThat(resDelete.statusCode(),equalTo(404));
        assertThat(resDelete.path(ErrorMessage.MESSAGE_FIELD),equalTo(ErrorMessage.NOT_FOUND_ERROR));
    }
    // edit user happy path
    @Test(description = "Edite User With Valid Data")
    public void editeUserWithValidData_TC18(){
        UserPojo user = UserFaker.generateUser();
        Response resCreate = UserApi.createUser(user);
        assertThat(resCreate.statusCode(),equalTo(201));
        int id = resCreate.as(UserPojo.class).getId();
        UserPojo userToUpdate = UserFaker.generateUser();
        Response resUpdate = UserApi.editeUserByID(userToUpdate,id);
        assertThat(resUpdate.statusCode(),equalTo(200));
        UserPojo updatedUser = resUpdate.as(UserPojo.class);
        assertThat(updatedUser.getId(),equalTo(id));
        assertThat(updatedUser.getName(),equalTo(userToUpdate.getName()));
        assertThat(updatedUser.getEmail(),equalTo(userToUpdate.getEmail()));
        assertThat(updatedUser.getGender(),equalTo(userToUpdate.getGender()));
        assertThat(updatedUser.getStatus(),equalTo(userToUpdate.getStatus()));
    }


    // edit user invalid id
    @Test(description = "Edite User With Invalid ID")
    public void editeUserWithInvalidID_TC19(){
        UserPojo userToUpdate = UserFaker.generateUser();
        Response resUpdate = UserApi.editeUserByID(userToUpdate,"invalid".hashCode());
        assertThat(resUpdate.statusCode(),equalTo(404));
        assertThat(resUpdate.path(ErrorMessage.MESSAGE_FIELD),equalTo(ErrorMessage.NOT_FOUND_ERROR));
    }

    // get user by valid id
    @Test(description = "Get User With Valid ID")
    public void getUserWithValidID_TC20(){
        UserPojo user = UserFaker.generateUser();
        Response resCreate = UserApi.createUser(user);
        assertThat(resCreate.statusCode(),equalTo(201));
        UserPojo createdUser = resCreate.as(UserPojo.class);
        Response resGet = UserApi.getUserByID(createdUser.getId());
        assertThat(resCreate.statusCode(),equalTo(201));
        UserPojo returnedUser = resGet.as(UserPojo.class);
        assertThat(returnedUser.getId(),equalTo(createdUser.getId()));
        assertThat(returnedUser.getName(),equalTo(createdUser.getName()));
        assertThat(returnedUser.getEmail(),equalTo(createdUser.getEmail()));
        assertThat(returnedUser.getGender(),equalTo(createdUser.getGender()));
        assertThat(returnedUser.getStatus(),equalTo(createdUser.getStatus()));
    }


    // get user by invalid id
    @Test(description = "Get User With Invalid ID")
    public void getUserWithInvalidID_TC21(){
        Response resGet = UserApi.getUserByID("invalid".hashCode());
        assertThat(resGet.statusCode(),equalTo(404));
        assertThat(resGet.path(ErrorMessage.MESSAGE_FIELD),equalTo(ErrorMessage.NOT_FOUND_ERROR));
    }


}
