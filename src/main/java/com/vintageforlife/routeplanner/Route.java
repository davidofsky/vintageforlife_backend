package com.vintageforlife.routeplanner;

import java.util.ArrayList;
import java.util.List;

public class Route {

    public List<Address> addresses = new ArrayList<Address>();
    public List<Address> route = new ArrayList<Address>();

    public Route(List<Address> addresses) throws Exception {
        this.addresses = addresses;
        NearestNeighborRoute();
    }

    /* NN algorithm
     add first address to route and remove from unvisited list
     do the following until route has all addresses:
        get next shortest path from last index from route   
        add next address to route
        remove next address from unvisited list
    */
    public void NearestNeighborRoute() throws Exception {
        route.add(addresses.get(0)); 
        List<Address> unvisited = new ArrayList<Address>(addresses);
        unvisited.remove(addresses.get(0));
        while(route.size() < addresses.size()) {
            Address address = route.get(route.size()-1);
            Path nextPath = address.getShortestPath(unvisited);
            route.add(nextPath.to);
            unvisited.remove(nextPath.to);
        }
    }

    @Override
    public String toString() {
        String returnValue = "[";
        for (int i = 0; i < route.size(); i++) {
            returnValue+= route.get(i).toString() + ",";
        }
        returnValue += route.get(0).toString();
        returnValue += "]";
        return returnValue;
    }


    // private void GreedyRoute() throws Exception {

    //     List<Address> visitedTo = new ArrayList<Address>();
    //     List<Address> visitedFrom = new ArrayList<Address>();

    //     Path shortest = null;

    //     while (route.size() < addresses.size()-1) {
    //         List<Address> unvisitedFrom = addresses;
    //         unvisitedFrom.removeAll(visitedFrom);
    //         System.out.println(visitedFrom.size() + " " + unvisitedFrom.size());

    //         List<Address> unvisitedTo = addresses;
    //         unvisitedTo.removeAll(visitedTo);

    //             System.out.println();
    //         for (int i = 0; i < unvisitedFrom.size(); i++) {
    //             Address address = unvisitedFrom.get(i);
    //             System.out.println(address.address);

    //             // skip if address already has two vertices
    //             if (visitedFrom.contains(address) && visitedTo.contains(address)) {
    //                 continue;
    //             }

    //             Path path = addresses.get(i).getShortestPath(unvisitedTo);
    //             if (shortest == null || path.distance < shortest.distance) {
    //                 shortest = path;
    //             }
    //         }

    //         route.add(shortest);
    //         visitedFrom.add(shortest.from);
    //         visitedTo.add(shortest.to);
    //     }
    // }
}