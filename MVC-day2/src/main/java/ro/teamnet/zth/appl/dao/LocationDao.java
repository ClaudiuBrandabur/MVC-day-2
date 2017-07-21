package ro.teamnet.zth.appl.dao;


import ro.teamnet.zth.api.em.EntityManager;
import ro.teamnet.zth.api.em.EntityManagerImpl;
import ro.teamnet.zth.appl.domain.Location;

import java.util.List;

public class LocationDao {

    EntityManager entityManager = new EntityManagerImpl();

    public Location insertLocation(Location location) {
        return (Location) entityManager.insert(location);
    }

    public Location updateLocation(Location location) {
        return entityManager.update(location);
    }

    public void deleteLocation(Location location) {
        entityManager.delete(location);
    }

    public List<Location> getAllLocations() {
        return entityManager.findAll(Location.class);
    }

    public Location getLocationById(Long id) {
        return entityManager.findById(Location.class, id);
    }
}
