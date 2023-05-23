package com.vintageforlife.routeplanner;

import java.util.ArrayList;
import java.util.List;

public class Route {

    public List<Address> addresses = new ArrayList<Address>();
    public List<Address> route = new ArrayList<Address>();

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
        route.add(addresses.get(0));
        List<Address> unvisited = new ArrayList<Address>(addresses);
        unvisited.remove(addresses.get(0));
        while (route.size() < addresses.size()) {
            Address address = route.get(route.size() - 1);
            Path nextPath = address.getShortestPath(unvisited);
            route.add(nextPath.to);
            unvisited.remove(nextPath.to);
        }
    }

    @Override
    public String toString() {
        String returnValue = "[";
        for (int i = 0; i < route.size(); i++) {
            returnValue += route.get(i).toString() + ",";
        }
        // returnValue += route.get(0).toString();
        returnValue += "]";
        return returnValue;
    }

    public void ChristofidesRoute() throws Exception {
        List<Path> mst = Mst.generate(addresses);
        for (int i = 0; i < mst.size(); i++) {
            route.add(mst.get(i).to);
        }

        route.add(route.get(0));
    }

}