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

@RestController
public class GoogleMapAPI {
    static private Config instance = Config.getInstance();
    @Autowired
    public GoogleMapAPI() {
    }

    public ArrayList<String> getDirections(String origin, String destination) throws InterruptedException, ApiException, IOException {
        GeoApiContext context =
                new GeoApiContext.Builder()
                        .apiKey(instance.getProperty("google_api_key"))
                        .build();
        DirectionsResult directionsResult = DirectionsApi.newRequest(context)
                .origin(origin)
                .destination(destination)
                .mode(TravelMode.WALKING)
                .await();
        DirectionsLeg leg = directionsResult.routes[0].legs[0];
        DirectionsStep[] steps = leg.steps;
        String startAddress = leg.startAddress;
        String endAddress = leg.endAddress;
        ArrayList<String> result = new ArrayList<String>();
        result.add(String.format("%s; %s",startAddress,endAddress));
        for(int i =0; i < steps.length; i ++){
            String direction = Jsoup.parse(steps[i].htmlInstructions).text();
            result.add(String.format("%s; %s; %s",direction,steps[i].distance, steps[i].duration));
        }
        return result;
    }
}
