package pl.gdynia.ctm.restctm.entity;

import org.springframework.stereotype.Component;
import pl.gdynia.ctm.restctm.exception.ArrayRequestException;

import java.util.LinkedList;
import java.util.List;

@Component
public class ArraysRepository {

    private final List<ArrayWrapper> arrays;

    public ArraysRepository() {
        this.arrays = new LinkedList<>();
    }

    public void add(ArrayWrapper arr) {
        arrays.add(arr);
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
