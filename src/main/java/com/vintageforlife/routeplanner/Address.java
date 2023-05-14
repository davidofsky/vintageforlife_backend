package com.vintageforlife.routeplanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.vintageforlife.routeplanner.helpers.OpenRouteService;

public class Address {
    public String street;
    public String number;
    public String city;
    public String postal;
    String address;
    List<String> coordinates;

    public Address(String street, String number, String city, String postal) throws Exception {
        String address = street + " " + number + ", " + city;
        if (postal.trim() != "")
            address += ", " + postal;
        this.address = address;
        coordinates = OpenRouteService.getCoordinates(address);
    }

    public static List<Address> parseJson(String json) {
        List<Address> addresses = new ArrayList<Address>();
        DocumentContext jsonBody = JsonPath.parse(json);
        List<String> addressList = jsonBody.read("$['addresses'][*]");
        for (int i = 0; i < addressList.size(); i++) {
            try {
                String street = jsonBody.read("$['addresses'][" + i + "]['street']");
                String number = jsonBody.read("$['addresses'][" + i + "]['number']");
                String city = jsonBody.read("$['addresses'][" + i + "]['city']");
                String postal = jsonBody.read("$['addresses'][" + i + "]['postal']");
                addresses.add(new Address(street, number, city, postal));
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return addresses;
    }
}
