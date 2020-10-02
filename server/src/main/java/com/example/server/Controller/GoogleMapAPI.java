package com.example.server.Controller;

import com.example.server.Config;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.TravelMode;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class GoogleMapAPI {
    static private Config instance = Config.getInstance();
    static private int stringSizeLimit = 135;
    static private HashMap<String, TravelMode>  stringToMode=  new HashMap<String, TravelMode>(){
        {
            put("W", TravelMode.WALKING);
            put("D", TravelMode.DRIVING);
        }
    };
    @Autowired
    public GoogleMapAPI() {
    }

    public ArrayList<String> getDirections(String origin, String destination, String mode,  String[] waypoints) throws InterruptedException, ApiException, IOException {
        GeoApiContext context =
                new GeoApiContext.Builder()
                        .apiKey(instance.getProperty("google_api_key"))
                        .build();
        DirectionsResult directionsResult = DirectionsApi.newRequest(context)
                .origin(origin)
                .destination(destination)
                .mode(stringToMode.get(mode))
                .waypoints(waypoints)
                .await();
        DirectionsLeg[] legs = directionsResult.routes[0].legs;

        ArrayList<String> result = new ArrayList<>();
        String address = String.format("%s; %s", legs[0].startAddress,legs[0].endAddress);
        String summary = String.format("; %s; %s", legs[0].distance,legs[0].duration);
        for(int i =1; i < legs.length; i ++){
            address += String.format("; %s",legs[i].endAddress);
            summary += String.format("; %s; %s",legs[i].distance,legs[i].duration);
        }
        result.add(address+summary);
        int curLength = 0;
        String curMessage = null;
        for(int j =0; j < legs.length; j ++){
            DirectionsStep[] steps = legs[j].steps;
            for(int i =0; i < steps.length; i ++){
                String direction = Jsoup.parse(steps[i].htmlInstructions).text();
                String message = String.format("%s; %s; %s |",direction,steps[i].distance, steps[i].duration);
                if(curLength + message.length() < stringSizeLimit){
                    curMessage = curMessage == null ? message : curMessage+message;
                    curLength += message.length() +1 ;
                }else{
                    result.add(curMessage);
                    curLength = message.length() +1;
                    curMessage = message;
                }
            }
            curMessage += '#';
        }
        for(int i = 0; i < result.size(); i++){
            result.set(i, result.get(i) + String.format(" (%d/%d)", i+1, result.size()));
        }
        return result;
    }
    public ArrayList<String> getDirections(String origin, String destination, String mode) throws InterruptedException, ApiException, IOException {
        return getDirections(origin, destination, mode,  new String[0]);
    }
}
