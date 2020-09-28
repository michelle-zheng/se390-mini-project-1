package com.example.server;


import com.example.server.Controller.GoogleMapAPI;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class GoogleMapAPITests {
    @Test
    public void  googleMapAPItest (){
        try{
            GoogleMapAPI mapApi = new GoogleMapAPI();
            //ArrayList<String> result = mapApi.getDirections("Toronto.", "Vancouver,");
            ArrayList<String> result = mapApi.getDirections("1509 Earlston Ave.", "675 Jolly Pl,");
            for(int i = 0; i< result.size(); i++){
                System.out.println(result.get(i));
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
