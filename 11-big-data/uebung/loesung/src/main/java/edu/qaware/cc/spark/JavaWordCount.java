package edu.qaware.cc.spark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * A word count example implemented with plain Java 8 features
 *
 * @author f.lautenschlager
 */
public class JavaWordCount {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaWordCount.class);

    /**
     * TODO: Implement the extraction of words from a given line
     */
    private static final Function<String, Stream<String>> WORDS_EXTRACTOR =
            s -> Arrays.stream(s.trim().split(" "));
    /**
     * TODO: Implement the mapping of words
     */
    private static final Function<String, AbstractMap.SimpleEntry<String, Long>> WORDS_MAPPER =
            word -> new AbstractMap.SimpleEntry<>(word, 1L);

    /**
     * TODO: Implement the reduce step of the mapped words
     */
    private static final Function<AbstractMap.SimpleEntry<String, Long>, String> WORDS_REDUCER =
            AbstractMap.SimpleEntry::getKey;

    /**
     * Runs the word count example with plain java 8 features.
     *
     * @param args ignored
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, URISyntaxException {

        URI uri = JavaWordCount.class.getResource("/largeText.txt").toURI();

        long start = System.currentTimeMillis();

        //Collect it to a map Executes the whole computation!
        Map<String, Long> result = Files.lines(Paths.get(uri))
                .flatMap(WORDS_EXTRACTOR)
                .map(WORDS_MAPPER)
                .collect(groupingBy(WORDS_REDUCER, counting()));

        long end = System.currentTimeMillis();

        LOGGER.info("================================================================");
        LOGGER.info("Counted {} words in {} ms", result.keySet().size(), end - start);
        LOGGER.info("================================================================");
    }


}