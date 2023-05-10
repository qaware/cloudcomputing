package de.qaware.cloudcomputing.bigdata;

import de.qaware.cloudcomputing.bigdata.adapter.WordCountSplitAdapter;
import de.qaware.cloudcomputing.bigdata.ignite.IgniteConfigurationProvider;
import de.qaware.cloudcomputing.bigdata.util.FileUtil;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WordCountMapReduce {

    static {
        System.getProperties().setProperty("java.net.preferIPv4Stack", "true");
    }

    private static final int WORD_LENGTH_MIN = 5;

    private static final String FILENAME = "ulysses.txt";

    public static void main(String[] args) throws IgniteException {
        IgniteConfiguration igniteConfiguration = IgniteConfigurationProvider.getIgniteConfiguration();

        // Starting the node
        Ignite ignite = Ignition.start(igniteConfiguration);

        String book = FileUtil.readFileFromResources(FILENAME);

        WordCountSplitAdapter wordCountSplitAdapter = new WordCountSplitAdapter();

        Map<String, Integer> taskResult = ignite.compute().execute(wordCountSplitAdapter, book);
        Map<String, Integer> sortedTaskResult = sortMapByValue(taskResult);

        for (Map.Entry<String, Integer> entry : sortedTaskResult.entrySet()) {
            System.out.format("| %-20s | %-5s |%n", entry.getKey(), entry.getValue());
        }

        // Disconnect from the cluster.
        ignite.close();
    }

    private static Map<String, Integer> sortMapByValue(Map<String, Integer> map) {
        return map.entrySet().stream()
                .filter(word -> word.getKey().length() > WORD_LENGTH_MIN)
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(30)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
