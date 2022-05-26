package pl.gdynia.ctm.restctm.model;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

public class Sensor extends RepresentationModel {
    private final int id;

    public Sensor(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public RepresentationModel add(Link link) {
        return super.add(link);
    }
}
