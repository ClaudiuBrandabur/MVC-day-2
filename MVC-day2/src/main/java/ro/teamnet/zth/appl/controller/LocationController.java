package ro.teamnet.zth.appl.controller;

import ro.teamnet.zth.api.annotations.Z2HController;
import ro.teamnet.zth.api.annotations.Z2HRequestMethod;
import ro.teamnet.zth.api.annotations.Z2HRequestParam;
import ro.teamnet.zth.appl.domain.Location;
import ro.teamnet.zth.appl.service.LocationService;
import ro.teamnet.zth.appl.service.LocationServiceImpl;

import java.util.List;

@Z2HController(urlPath = "/locations")
public class LocationController {

    private LocationService locationService;

    public LocationController(){
        locationService = new LocationServiceImpl();
    }

    @Z2HRequestMethod(urlPath = "/all")
    public List<Location> getAll() {
        return locationService.findAll();
    }

    @Z2HRequestMethod(urlPath = "/one")
    public Location getOne(@Z2HRequestParam(name = "id") Long locationId) {
        return locationService.findOne(locationId);
    }
}
