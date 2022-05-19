package pl.gdynia.ctm.restctm.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pl.gdynia.ctm.restctm.model.ArrayWrapper;
import pl.gdynia.ctm.restctm.factory.SortStrategyFactory;


@Service
public class SortService {

    private final SortStrategyFactory sortStrategyFactory;


    @Autowired
    public SortService(SortStrategyFactory factory) {
        this.sortStrategyFactory = factory;
    }


    public void sortInput(String strategy, ArrayWrapper input) {
        Assert.hasText(strategy,"You must specify strategy");
        Assert.notNull(input,"Input cannot be null");
        sortStrategyFactory.getSortStrategy(strategy).sort(input);
    }
}
