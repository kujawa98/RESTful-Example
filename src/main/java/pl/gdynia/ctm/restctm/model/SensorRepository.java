package pl.gdynia.ctm.restctm.model;

import org.springframework.stereotype.Component;
import pl.gdynia.ctm.restctm.exception.SensorRequestException;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SensorRepository {
    private final HashMap<Integer, Sensor> sensors = new HashMap<>();

    public static AtomicInteger CURRENT_ID = new AtomicInteger(0);

    private SensorRepository() {}

    public HashMap<Integer, Sensor> getSensors() {
        return sensors;
    }

    public Sensor addSensor() {
        var sensor = new Sensor(CURRENT_ID.get());
        sensors.put(CURRENT_ID.getAndIncrement(), sensor);
        return sensor;
    }

    public Sensor getSensor(int id) {
        if (sensors.containsKey(id)) {
            return sensors.get(id);
        }
        throw new SensorRequestException("There's no sensor with given id");
    }

    public Sensor deleteSensor(int id) {
        if (sensors.containsKey(id)) {
            return sensors.remove(id);
        }
        throw new SensorRequestException("There's no sensor with given id");
    }
}
