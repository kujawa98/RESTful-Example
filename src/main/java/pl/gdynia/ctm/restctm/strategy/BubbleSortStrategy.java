package pl.gdynia.ctm.restctm.strategy;


import org.springframework.util.Assert;
import pl.gdynia.ctm.restctm.model.ArrayWrapper;

public class BubbleSortStrategy implements SortStrategy {
    @Override
    public void sort(ArrayWrapper input) {
        Assert.notNull(input, "Input cannot be null");
        boolean changed = false;
        int[] tab = input.getArr();
        for (int i = 0; i < tab.length; i++) {
            if (changed) {
                return;
            }
            for (int j = 0; j < tab.length - 1; j++) {
                if (tab[j] > tab[j + 1]) {
                    int tmp = tab[j];
                    tab[j] = tab[j + 1];
                    tab[j + 1] = tmp;
                    changed = true;
                }
            }
        }
    }
}
