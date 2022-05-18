package pl.gdynia.ctm.restctm.factory;

import org.springframework.stereotype.Component;
import pl.gdynia.ctm.restctm.strategy.BubbleSortStrategy;
import pl.gdynia.ctm.restctm.strategy.QuickSortStrategy;
import pl.gdynia.ctm.restctm.strategy.SortStrategy;

import java.util.HashMap;
import java.util.Map;
@Component
public class SortStrategyFactory {
    private final Map<String, SortStrategy> strategyMap;

    public SortStrategyFactory() {
        strategyMap = new HashMap<>();
        strategyMap.put("bubble", new BubbleSortStrategy());
        strategyMap.put("quick", new QuickSortStrategy());
    }

    public SortStrategy getSortStrategy(String strategy) {
        return strategyMap.get(strategy);
    }
    //TODO: enum zamiast stringa?
}
