package de.qaware.cloudcomputing.bigdata;

import de.qaware.cloudcomputing.bigdata.ignite.IgniteConfigurationProvider;
import de.qaware.cloudcomputing.bigdata.util.FileUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.affinity.AffinityUuid;
import org.apache.ignite.configuration.IgniteConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class WordCountStreaming {

    static {
        System.getProperties().setProperty("java.net.preferIPv4Stack", "true");
    }

    private static final String FILENAME = "ulysses.txt";
    private static final String SEPARATOR_CHAR = "\\s+";

    public static void main(String[] args) throws IOException {
        IgniteConfiguration igniteConfiguration = IgniteConfigurationProvider.getIgniteConfiguration();

        // Starting the node
        Ignite ignite = Ignition.start(igniteConfiguration);

        IgniteCache<AffinityUuid, String> streamCache = ignite.getOrCreateCache(IgniteConfigurationProvider.getCacheConfiguration());

        try (IgniteDataStreamer<AffinityUuid, String> streamer = ignite.dataStreamer(streamCache.getName())) {
            while (true) {
                processFile(streamer);
            }
        }
    }

    private static void processFile(IgniteDataStreamer<AffinityUuid, String> streamer) {
        InputStream inputStream = FileUtil.readFileFromResourcesAsStream(FILENAME);

        LineIterator lineIterator = IOUtils.lineIterator(inputStream, StandardCharsets.UTF_8);

        while (lineIterator.hasNext()) {
            String line = lineIterator.nextLine();
            processLine(streamer, line);
        }
    }

    private static void processLine(IgniteDataStreamer<AffinityUuid, String> streamer, String line) {
        // TODO: Wörter ins Cluster streamen
        // Hinweis: Nehmen Sie als Key AffinityUuid(word) zu Hilfe.
    }

}
