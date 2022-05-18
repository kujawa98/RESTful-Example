package pl.gdynia.ctm.restctm.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.gdynia.ctm.restctm.entity.ArrayWrapper;
import pl.gdynia.ctm.restctm.factory.SortStrategyFactory;


@Service
public class SortService {

    private final SortStrategyFactory sortStrategyFactory;


    @Autowired
    public SortService(SortStrategyFactory factory) {
        this.sortStrategyFactory = factory;
    }


    public void sortInput(String strategy, ArrayWrapper input) {
        sortStrategyFactory.getSortStrategy(strategy).sort(input);
    }
}
