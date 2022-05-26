package pl.gdynia.ctm.restctm.controller;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gdynia.ctm.restctm.model.Sensor;
import pl.gdynia.ctm.restctm.model.SensorRepository;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class SensorController {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorController(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @GetMapping(value = "/sensor", produces = "application/vnd.ctm.v1+json")
    public ResponseEntity<CollectionModel<Map.Entry<Integer, Sensor>>> getSensorsV1() {
        var collectionModel = getSensorsCollectionModel();
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping(value = "/sensor", produces = "application/vnd.ctm.v2+json")
    public ResponseEntity<CollectionModel<Sensor>> getSensorsV2() {
        var sensors = new LinkedList<>(sensorRepository.getSensors().values());
        var collectionModel = CollectionModel.of(sensors, getSensorsLink());
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @GetMapping("/sensor/{id}")
    public ResponseEntity<EntityModel<Sensor>> getSensor(@PathVariable String id) {
        var sensor = sensorRepository.getSensor(Integer.parseInt(id));
        var entityModel = EntityModel.of(sensor, getSensorsLink());
        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @PostMapping("/sensor")
    public ResponseEntity<EntityModel<Sensor>> postSensor() {
        var sensor = sensorRepository.addSensor();
        var entityModel = EntityModel.of(sensor, getSensorsLink());
        var id = String.valueOf(sensor.getId());
        sensor.add(getSensorLink(id));
        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
    }

    @DeleteMapping("/sensor/{id}")
    public ResponseEntity<CollectionModel<Map.Entry<Integer, Sensor>>> deleteSensor(@PathVariable String id) {
        var newId = Integer.parseInt(id);
        sensorRepository.deleteSensor(newId);
        var collectionModel = getSensorsCollectionModel();
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @DeleteMapping("/sensor")
    public ResponseEntity<CollectionModel<Map.Entry<Integer, Sensor>>> deleteSensors() {
        sensorRepository.getSensors().clear();
        SensorRepository.CURRENT_ID = new AtomicInteger(0);
        var collectionModel = getSensorsCollectionModel();
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    private Link getSensorsLink() {
        return linkTo(SensorController.class).slash("sensor").withSelfRel();
    }

    private Link getSensorLink(String id) {
        return linkTo(methodOn(SensorController.class).getSensor(id)).withRel(String.format("Sensor_%s", id));
    }

    private CollectionModel<Map.Entry<Integer, Sensor>> getSensorsCollectionModel() {
        var entries = sensorRepository.getSensors().entrySet();
        return CollectionModel.of(entries, getSensorsLink());
    }
}