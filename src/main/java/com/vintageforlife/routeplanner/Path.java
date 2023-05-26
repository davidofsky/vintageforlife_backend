package com.vintageforlife.routeplanner;

import java.util.List;

import com.jayway.jsonpath.DocumentContext;
import com.vintageforlife.routeplanner.helpers.OpenRouteService;

public class Path {
    public Address from;
    public Address to;
    public double distance;
    public String pathCoordinates;
    public List<Double> bbox;

    public Path(Address from, Address to, boolean getDistance) throws Exception {
        this.from = from;
        this.to = to;
        if (getDistance) {
            getDistance();
        }
    }

    public Path(Address from, Address to) throws Exception {
        this(from, to, true);
    }

    public void getDistance() throws Exception {
            DocumentContext data = OpenRouteService.getDistance(from.coordinates, to.coordinates);
            String jsonpathDuration = "$.features[0].properties.summary.duration";
            String jsonpathCoordinates = "$.features[0].geometry.coordinates";
            String jsonpathBbox = "$.features[0].bbox";
            this.distance = data.read(jsonpathDuration);
            this.pathCoordinates = data.read(jsonpathCoordinates).toString();
            this.bbox = data.read(jsonpathBbox);
    }
}
