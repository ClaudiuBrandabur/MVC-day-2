package ro.teamnet.zth.appl.service;

import ro.teamnet.zth.appl.domain.Location;

import java.util.List;

/**
 * Created by Oana.Mihai on 7/15/2016.
 */
public interface LocationService {
    List<Location> findAll();

    Location findOne(Long locationId);
}
