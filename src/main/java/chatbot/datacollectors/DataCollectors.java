package chatbot.datacollectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by matthias on 29.06.2015.
 */
@Component
public class DataCollectors {


    private List<DataCollector> collectors;

    @Autowired
    public DataCollectors(List<DataCollector> collectors){
        this.collectors = collectors;
    }

    public void start(){
        for(DataCollector d : collectors){
            d.start();
        }
    }

    public void stop(){
        for(DataCollector d : collectors){
            d.stop();
        }
    }
}
