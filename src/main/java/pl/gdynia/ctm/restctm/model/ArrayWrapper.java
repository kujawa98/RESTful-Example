package pl.gdynia.ctm.restctm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.util.Assert;


@AllArgsConstructor
@Getter
public class ArrayWrapper extends RepresentationModel {

    private final int[] arr;
    private final int id;

    @Override
    public RepresentationModel add(Link link) {
        Assert.notNull(link, "Link cannot be null");
        return super.add(link);
    }

}
