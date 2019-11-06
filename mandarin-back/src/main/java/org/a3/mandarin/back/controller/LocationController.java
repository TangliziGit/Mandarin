package org.a3.mandarin.back.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.a3.mandarin.back.exception.ApiNotFoundException;
import org.a3.mandarin.back.model.RESTfulResponse;
import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.dao.repository.LocationQueryRepository;
import org.a3.mandarin.common.entity.Location;
import org.a3.mandarin.common.entity.QLocation;
import org.a3.mandarin.common.enums.PermissionType;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class LocationController {

    @Resource
    private LocationQueryRepository locationQueryRepository;

    @GetMapping("/location")
    @Transactional
    public ResponseEntity<RESTfulResponse<List<Location>>> getLocationList(){
        List<Location> locations = locationQueryRepository.findAll();

        RESTfulResponse<List<Location>> response = RESTfulResponse.ok();
        response.setData(locations);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/location/{id}")
    @Transactional
    public ResponseEntity<RESTfulResponse<Location>> getLocation(@PathVariable Integer id) {
        Location location = locationQueryRepository.findById(id).orElse(null);

        if (null == location)
            throw new ApiNotFoundException("no such location");

        RESTfulResponse<Location> response = RESTfulResponse.ok();
        response.setData(location);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/location")
    @Transactional
    @Permission({PermissionType.LIBRARIAN})
    public ResponseEntity<RESTfulResponse> addLocation(@RequestParam Integer floor,
                                                       @RequestParam Integer shelf) {
        QLocation qLocation = QLocation.location;
        BooleanExpression expression = qLocation.floor.eq(floor).and(qLocation.shelf.eq(shelf));
        long locationCount = locationQueryRepository.count(expression);

        if (locationCount != 0)
            throw new ApiNotFoundException("such location exists");

        Location location = new Location(null, floor, shelf);
        locationQueryRepository.save(location);
        return ResponseEntity.ok(RESTfulResponse.ok());
    }

    @PutMapping("/location/{id}")
    @Transactional
    @Permission({PermissionType.LIBRARIAN})
    public ResponseEntity<RESTfulResponse> updateLocation(@PathVariable Integer id,
                                                          @RequestParam Integer floor,
                                                          @RequestParam Integer shelf) {
        Location location = locationQueryRepository.findById(id).orElse(null);

        if (null == location)
            throw new ApiNotFoundException("no such location");

        QLocation qLocation = QLocation.location;
        BooleanExpression expression = qLocation.floor.eq(floor).and(qLocation.shelf.eq(shelf));
        long locationCount = locationQueryRepository.count(expression);

        if (locationCount != 0)
            throw new ApiNotFoundException("such location exists");

        location.setFloor(floor);
        location.setShelf(shelf);
        locationQueryRepository.save(location);

        return ResponseEntity.ok(RESTfulResponse.ok());
    }

    @DeleteMapping("/location/{id}")
    @Transactional
    @Permission({PermissionType.LIBRARIAN})
    public ResponseEntity<RESTfulResponse> deleteLocation(@PathVariable Integer id){
        locationQueryRepository.deleteById(id);
        return ResponseEntity.ok(RESTfulResponse.ok());
    }
}

