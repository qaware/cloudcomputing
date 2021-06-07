package edu.qaware.cc.reactive;

import edu.qaware.cc.reactive.connectors.openlibrary.OpenLibraryConnector;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OpenLibraryConnectorTest {

    @Test
    public void testFetchBooksWithTitle() {
        OpenLibraryConnector connector = new OpenLibraryConnector();
        List<String> result = connector.getBooksWithTitleContaining("Java Programming");

        assertThat(result).isNotEmpty();
    }
}
