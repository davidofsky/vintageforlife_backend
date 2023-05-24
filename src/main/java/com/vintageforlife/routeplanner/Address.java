package com.vintageforlife.routeplanner;

import java.util.ArrayList;
import java.util.List;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.vintageforlife.routeplanner.helpers.OpenRouteService;

public class Address {
    String address;
    List<String> coordinates;
    List<Path> paths = new ArrayList<Path>();

    public Address(String address) throws Exception {
        this.address = address;
        coordinates = OpenRouteService.getCoordinates(address);
    }

    // Fetch or create path to address
    public Path getPath(Address address) throws Exception {
        if (address == this)
            return null;
        Path path = null;

        // get Path from paths list
        for (int i = 0; i < paths.size(); i++) {
            if (paths.get(i).to == address)
                path = paths.get(i);
        }

        if (path == null) { // if path doesn't exists in paths list: create new path
            path = new Path(this, address);
            paths.add(0, path);
        }
        return path;
    }

    // Return the path to the closest address in the list
    public Path getShortestPath(List<Address> addressList) throws Exception {
        Path shortest = null;
        for (int i = 0; i < addressList.size(); i++) {

            Address address = addressList.get(i);


            if (address == this)
                continue;

            Path path = this.getPath(address);

            if (shortest == null || path.distance < shortest.distance) {
                shortest = path;
            }
        }
        return shortest;
    }

    public static List<Address> parseJson(String json) {
        List<Address> addresses = new ArrayList<Address>();
        DocumentContext jsonBody = JsonPath.parse(json);
        List<String> addressList = jsonBody.read("$['addresses'][*]");
        for (int i = 0; i < addressList.size(); i++) {
            try {
                String addr = jsonBody.read("$['addresses'][" + i + "]");
                addresses.add(new Address(addr));
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return addresses;
    }
}
