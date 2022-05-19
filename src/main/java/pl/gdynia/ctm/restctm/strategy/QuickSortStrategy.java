package pl.gdynia.ctm.restctm.strategy;


import org.springframework.util.Assert;
import pl.gdynia.ctm.restctm.model.ArrayWrapper;

public class QuickSortStrategy implements SortStrategy {
    private int partition(int array[], int low, int high) {
        int pivot = array[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }

        }
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return (i + 1);
    }

    private void quickSort(int array[], int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);
            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);
        }
    }

    @Override
    public void sort(ArrayWrapper input) {
        Assert.notNull(input, "Input cannot be null");
        int[] tab = input.getArr();
        quickSort(tab, 0, tab.length - 1);
    }
}
