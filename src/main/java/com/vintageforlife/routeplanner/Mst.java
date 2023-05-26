package com.vintageforlife.routeplanner;

import java.util.ArrayList;
import java.util.List;

public class Mst {

    /*
     * Generate mst: list of path
     * 
     * 1. Create list of paths (initially goes from null -> first address)
     * 2. Create list of visited addresses (initially holds the first address)
     * 3. Create list of unvisited addresses (the rest)
     * 
     * 4. Do the following until all addresses have been visited:
     * 1. Get shortest path to an unvisited addresses from any visited address
     * 2. mark the next address as visited
     * 3. add the path to the list of paths
     * 
     * -- close any gaps so that starting points from paths are together --
     */
    
    public static List<Path> generate(List<Address> addresses) throws Exception {
        List<Path> mst = new ArrayList<Path>();
        mst.add(new Path(null, addresses.get(0), false));

        List<Address> visited = new ArrayList<Address>();
        visited.add(addresses.get(0));
        List<Address> unvisited = new ArrayList<>(addresses.subList(1, addresses.size()));

        while (unvisited.size() > 0) {
            Path shortest = null;
            for (int i = 0; i < visited.size(); i++) {
                Path newPath = visited.get(i).getShortestPath(unvisited);
                if (shortest == null || newPath.distance < shortest.distance) {
                    shortest = newPath;
                }
            }
            visited.add(shortest.to);
            unvisited.remove(shortest.to);

            int indexNr = 0;
            for (int i = 0; i < mst.size(); i++) {
                indexNr++;
                if (mst.get(i).to == shortest.from) {
                    break;
                }
            }
            mst.add(indexNr, shortest);
        }

        return mst;
    }

}