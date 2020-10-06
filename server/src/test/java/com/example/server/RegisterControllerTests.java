package com.example.server;

import com.example.server.Controller.RegisterController;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

public class RegisterControllerTests {

    public RegisterController rc;

    @BeforeTest
    public void prepareTest(){
        rc = new RegisterController();

    }


    @Test
    public void successfulRegistration(){

        Assert.assertEquals(rc.register("successfulNumber"), RegisterController.SUCCESS);
    }

    @Test
    public void duplicatedRegistration(){
        Assert.assertEquals(rc.register("existsNumber"), RegisterController.ALREADY_EXISTS);
    }

    @Test
    public void failedRegistration(){

        Assert.assertEquals(rc.register(""), RegisterController.FAILURE);
    }

}
