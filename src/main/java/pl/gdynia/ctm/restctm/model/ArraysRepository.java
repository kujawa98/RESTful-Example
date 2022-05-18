package pl.gdynia.ctm.restctm.model;

import org.springframework.stereotype.Component;
import pl.gdynia.ctm.restctm.exception.ArrayRequestException;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ArraysRepository {

    private final List<ArrayWrapper> arrays;
    public static AtomicInteger CURRENT_ID = new AtomicInteger(0);

    public ArraysRepository() {
        this.arrays = new LinkedList<>();
    }

    public ArrayWrapper add(int[] input) {
        var wrapper = new ArrayWrapper(input, CURRENT_ID.getAndIncrement());
        arrays.add(wrapper);
        return wrapper;
    }

    public void delete(int id) {
        boolean isDeleted = arrays.removeIf(arrayWrapper -> arrayWrapper.getId() == id);
        if (!isDeleted) {
            throw new ArrayRequestException("There's no such array with given ID");
        }
    }

    public int[] getArray(int id) {
        return get(id).getArr();
    }

    public ArrayWrapper get(int id) {
        return arrays.stream()
                .filter(arrayWrapper -> arrayWrapper.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ArrayRequestException("There's no such array with given ID"));
    }

    public List<ArrayWrapper> getArrays() {
        return arrays;
    }
}
