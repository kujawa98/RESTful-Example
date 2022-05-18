package pl.gdynia.ctm.restctm.entity;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ArrayWrapper extends RepresentationModel {
    public static AtomicInteger CURRENT_ID = new AtomicInteger(-1);
    private final int[] arr;
    private final int id;

    public ArrayWrapper(int[] arr) {
        this.arr = arr;
        System.out.println(CURRENT_ID);
        this.id = CURRENT_ID.getAndIncrement();
    }

    public int[] getArr() {
        return arr;
    }

    public int getId() {
        return id;
    }

    @Override
    public RepresentationModel add(Link link) {
        Assert.notNull(link, "Link cannot be null");
        return super.add(link);
    }

//    public int[] getCopyOfArray() {
//        return Arrays.copyOf(arr, arr.length);
//    }
}
