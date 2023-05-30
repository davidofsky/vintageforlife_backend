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

    // create path and calc distance
    public Path(Address from, Address to) throws Exception {
        this(from, to, true);
    }

    public Path(Address from, Address to, boolean getDistance) throws Exception {
        this.from = from;
        this.to = to;
        if (getDistance) {
            getDistance();
        }
    }


    public void getDistance() throws Exception {
            DocumentContext data = OpenRouteService.getDistance(from.coordinates, to.coordinates);
            String jsonpathDuration = "$.features[0].properties.summary.duration"; // retrieve duration
            String jsonpathCoordinates = "$.features[0].geometry.coordinates"; // retrieve coordinates
            String jsonpathBbox = "$.features[0].bbox"; // retrieve bbox

            // set attributes
            this.distance = data.read(jsonpathDuration);
            this.pathCoordinates = data.read(jsonpathCoordinates).toString();
            this.bbox = data.read(jsonpathBbox);
    }
}
