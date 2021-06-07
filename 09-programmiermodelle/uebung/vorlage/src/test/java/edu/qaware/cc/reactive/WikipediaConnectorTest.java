package edu.qaware.cc.reactive;

import edu.qaware.cc.reactive.connectors.wikipedia.WikipediaConnector;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WikipediaConnectorTest {
    @Test
    public void testConnector() {
        WikipediaConnector connector = new WikipediaConnector();
        List<String> result = connector.getArticleTitlesFor("Java");
        assertThat(result).isNotEmpty();
    }
}