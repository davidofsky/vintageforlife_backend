package com.vintageforlife.routeplanner;

import com.vintageforlife.routeplanner.helpers.OpenRouteService;

public class Path {
    public Address from;
    public Address to;
    public double distance;

    public Path(Address from, Address to, boolean getDistance) throws Exception {
        this.from = from;
        this.to = to;
        if (getDistance) {
            this.distance = OpenRouteService.getDistance(from.coordinates, to.coordinates);
        }
    }

    public Path(Address from, Address to) throws Exception {
        this(from, to, true);
    }
}
