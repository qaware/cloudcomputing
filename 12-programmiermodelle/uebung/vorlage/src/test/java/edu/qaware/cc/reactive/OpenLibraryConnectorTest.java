package edu.qaware.cc.reactive;

import edu.qaware.cc.reactive.connectors.openlibrary.OpenLibraryConnector;
import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class OpenLibraryConnectorTest {

    @Test
    public void testFetchBooksWithTitle() {
        OpenLibraryConnector connector = new OpenLibraryConnector();
        Set<String> result = connector.getBooksWithTitleContaining("Java Programming");

        assertThat(result).isNotEmpty();
    }
}
