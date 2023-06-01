package com.vintageforlife.routeplanner;

import java.util.ArrayList;
import java.util.List;

public class Route {
    public List<Address> addresses = new ArrayList<Address>();
    public List<Path> route = new ArrayList<Path>();
    List<Double> bbox = new ArrayList<Double>();

    public Route(List<Address> addresses) throws Exception {
        this.addresses = addresses;
        ChristofidesRoute();
    }

    /*
     * NN algorithm
     * add first address to route and remove from unvisited list
     * do the following until route has all addresses:
     * get next shortest path from last index from route
     * add next address to route
     * remove next address from unvisited list
     */
    
    public void NearestNeighborRoute() throws Exception {
        route.add(new Path(null, addresses.get(0), false));
        List<Address> unvisited = new ArrayList<Address>(addresses);
        unvisited.remove(addresses.get(0));
        while (route.size() < addresses.size()) {
            Address address = route.get(route.size() - 1).to;
            Path nextPath = address.getShortestPath(unvisited);
            route.add(nextPath);
            unvisited.remove(nextPath.to);
        }
    }


    /*
     * Uses the mst to create the route
     * 
     * Note that the mst is formatted as a list of paths, ordered by path.from 
     * example mst format:
     *   - null -> address1
     *   - address1 -> address2
     *   - address2 -> address3
     *   - address2 -> address4
     *   - address4 -> address5
     * 
     * This means that, in order to create a route, the following must be done:
     *  1. Create a new route (list of paths) filled with the mst
     *  2. Make the route loop by adding the last address to the first path.from and calc the distance
     *  3. Remove backtracking by iterating over the paths and doing the following:
     *      if the 'iterator path' does not start where the last path ended:
     *          replace the iterator path.from with the last path.to
     */
    public void ChristofidesRoute() throws Exception {
        route = Mst.generate(addresses);
        route.get(0).from = route.get(route.size()-1).to;
        route.get(0).getDistance();
        for (int i = 1; i < route.size(); i++) {
            if (route.get(i).from != route.get(i-1).to) {
                Path replaced = route.get(i-1).to.getPath(route.get(i).to);
                route.remove(i);
                route.add(i, replaced);
            }
        }
    }

    // get the biggest size of all the bboxes of paths to create the bbox of the route
    public void setBbox() {
        this.bbox = route.get(0).bbox;
        for (Path path : route) {
            if (path.bbox.get(0) < this.bbox.get(0)) {
                bbox.set(0, path.bbox.get(0));
            }
            if (path.bbox.get(1) < this.bbox.get(1)) {
                bbox.set(1, path.bbox.get(1));
            }
            if (path.bbox.get(2) > this.bbox.get(2)) {
                bbox.set(2, path.bbox.get(2));
            }
            if (path.bbox.get(3) > this.bbox.get(3)) {
                bbox.set(3, path.bbox.get(3));
            }
        }
    }


    @Override
    public String toString() {
        setBbox();
        String returnValue = "{\"bbox\": "+bbox.toString()+", \"route\":[";
        for (int i = 0; i < route.size(); i++) {
            returnValue += "{ \"address\": \"";
            returnValue += route.get(i).to.address + "\",";
            returnValue += "\"path\": " + route.get(i).pathCoordinates;
            returnValue += "}";
            if(i < route.size()-1) {
                returnValue += ",";
            }
        }
        returnValue += "]}";
        return returnValue;
    }
}