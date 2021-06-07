package edu.qaware.cc.reactive;

import edu.qaware.cc.reactive.connectors.wikipedia.WikipediaConnector;
import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class WikipediaConnectorTest {
    @Test
    public void testConnector() {
        WikipediaConnector connector = new WikipediaConnector();
        Set<String> result = connector.getArticleTitlesFor("Java");
        assertThat(result).isNotEmpty();
    }
}