/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wildpark.dswp.controllers;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;

/**
 *
 * @author Panker-RDP
 */
@Named(value = "templateController")
@SessionScoped
public class TemplateController implements Serializable {
private LineChartModel chart=new LineChartModel();
private MapModel mapModel=new DefaultMapModel();
    
    public TemplateController() {
    }

    public ChartModel getChart() {
        chart=new LineChartModel();
        LineChartSeries lcs=new LineChartSeries("template");
        lcs.set(1, 26);
        lcs.set(2, 34);
        lcs.set(3, 14);
        lcs.set(4, 23);
        lcs.set(5, 52);
        lcs.set(6, 62);
        lcs.set(7, 37);
        lcs.set(8, 46);
        lcs.set(9, 13);
        lcs.set(10, 18);
        lcs.set(11, 33);
        chart.addSeries(lcs);       
        chart.setLegendPosition("nw");
        chart.setTitle("Deafult chart");
        return chart;
    }

    public void setChart(LineChartModel chart) {
        this.chart = chart;
    }

    public MapModel getMapModel() {
        
        return mapModel;
    }

    public void setMapModel(MapModel mapModel) {
        this.mapModel = mapModel;
    }
    
    
    
    
    
}
