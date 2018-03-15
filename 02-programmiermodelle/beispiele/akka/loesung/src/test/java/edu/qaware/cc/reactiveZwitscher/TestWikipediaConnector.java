package edu.qaware.cc.reactiveZwitscher;

import edu.qaware.cc.reactiveZwitscher.connectors.wikipedia.WikipediaConnector;
import java.util.List;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.empty;

public class TestWikipediaConnector {
 
    @Test
     public void testConnector(){
         WikipediaConnector connector = new WikipediaConnector();
         List<String> result = connector.getArticleTitlesFor("Java");
         assertThat(result, not(empty()));
     }
}