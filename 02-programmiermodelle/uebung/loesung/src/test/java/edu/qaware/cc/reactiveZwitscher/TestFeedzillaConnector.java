package edu.qaware.cc.reactiveZwitscher;

import edu.qaware.cc.reactiveZwitscher.connectors.feedzilla.FeedzillaConnector;
import edu.qaware.cc.reactiveZwitscher.connectors.feedzilla.NewsCategory;
import java.util.List;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import org.junit.Test;

public class TestFeedzillaConnector {

    @Test
    public void testConnector() {
        FeedzillaConnector feedzilla = new FeedzillaConnector();
         List<String> result = 
                 feedzilla.getNewsFor("Obama", NewsCategory.TOP_NEWS);
         assertThat(result, not(empty()));        
    }
}
