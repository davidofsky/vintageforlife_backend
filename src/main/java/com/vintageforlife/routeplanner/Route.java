package com.vintageforlife.routeplanner;

import java.util.ArrayList;
import java.util.List;

public class Route {

    public List<Address> unvisited = new ArrayList<Address>();
    public List<Address> visitedTo = new ArrayList<Address>();
    public List<Address> visitedFrom = new ArrayList<Address>();

    public List<Path> route = new ArrayList<Path>();

    public Route(List<Address> unvisited) throws Exception {
        this.unvisited = unvisited;
        GreedyRoute();
    }

    private void GreedyRoute() throws Exception {

        Path shortest = null;

       List<Address> visitedAddresses = new ArrayList <Address>();

        while (unvisited.size() != visitedAddresses.size()) {

            for (int i = 0; i < unvisited.size(); i++) {

                if (visitedFrom.contains(unvisited.get(i)))
                    continue; 
System.out.println(unvisited.get(i)); 

                List<Address> sublist = unvisited;
                sublist.removeAll(visitedTo);
System.out.println(sublist.size());

                Path path = unvisited.get(i).getShortestPath(sublist);
                if (shortest == null || path.distance < shortest.distance) {
                    shortest = path;
                }
            }

            
            visitedTo.add(shortest.to); // krijgt mee: aankomst. vertrek en afstand .
            visitedFrom.add(shortest.from);
            route.add(shortest);
System.out.println(shortest.from.address + "->  "  + shortest.to.address); 
            if (visitedTo.contains(shortest.from)){
                visitedAddresses.add(shortest.from);

            }
            if (visitedFrom.contains(shortest.to)){
                visitedAddresses.add(shortest.to);}
        }
    }
}