package net.wildpark.dswp.controllers.pageControllers;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;
import java.util.Scanner;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.primefaces.json.JSONObject;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@Named(value = "gmController")
@RequestScoped
public class GoogleMapsController{

    private MapModel mapModel;
    private String center;
    private String id;
    private int count=10;
    
    public GoogleMapsController() {
    }
   
    
    public MapModel getTestsMarkers() {
        MapModel mm = new DefaultMapModel();
        int start = 0;
        Double lat = 46.95694118331636;
        Double lng = 32.01210021972656;

        while (start <= count) {
            /*
                at:46.93537809500658, Lng:32.05741882324219
                46.98107216122198, Lng:31.970558166503906
             */
            Double lat1,lng1;
            if(new Random().nextBoolean()){
                lat1=lat+Math.random();
                lng1=lng-Math.random();
            }else{
                lat1=lat-Math.random();
                lng1=lng-Math.random();
            }
            if (lat1 > 46.93537809500658 && lng1 > 31.970558166503906 && lat1 < 46.98107216122198 && lng1 < 32.05741882324219) {
                mm.addOverlay(new Marker(new LatLng(lat1, lng1)));
                start++;
            }

        }
        return mm;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    

}
