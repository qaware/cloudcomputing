package edu.qaware.cc.reactive;

import edu.qaware.cc.reactive.connectors.openlibrary.OpenLibraryConnector;
import org.junit.Test;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;

public class OpenLibraryConnectorTest {

    @Test
    public void testFetchBooksWithTitle(){
        OpenLibraryConnector connector = new OpenLibraryConnector();
        List<String> result = connector.getBooksWithTitleContaining("Java Programming");
        assertThat(result, not(empty()));
    }
}
