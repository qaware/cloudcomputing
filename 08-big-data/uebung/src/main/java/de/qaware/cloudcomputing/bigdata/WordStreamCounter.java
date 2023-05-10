package de.qaware.cloudcomputing.bigdata;

import de.qaware.cloudcomputing.bigdata.ignite.IgniteConfigurationProvider;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.affinity.AffinityUuid;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;

import java.util.List;

public class WordStreamCounter {

    static {
        System.getProperties().setProperty("java.net.preferIPv4Stack", "true");
    }

    public static void main(String[] args) throws InterruptedException {
        IgniteConfiguration igniteConfiguration = IgniteConfigurationProvider.getIgniteConfiguration();

        try (Ignite ignite = Ignition.start(igniteConfiguration)) {

            CacheConfiguration<AffinityUuid, String> cfg = IgniteConfigurationProvider.getCacheConfiguration();

            // The cache is configured with sliding window holding 1 second of the streaming data.
            try (IgniteCache<AffinityUuid, String> streamCache = ignite.getOrCreateCache(cfg)) {
                // Select top 10 words.
                SqlFieldsQuery top10Qry = new SqlFieldsQuery(
                        "select _val, count(_val) as cnt from String where length(_val) > 5 group by _val order by cnt desc limit 10",
                        true /*collocated*/
                );

                // Select average, min, and max counts among all the words.
                SqlFieldsQuery statsQry = new SqlFieldsQuery(
                        "select avg(cnt), min(cnt), max(cnt) from (select count(_val) as cnt from String group by _val)");

                // Query top 10 popular numbers every 5 seconds.
                while (true) {
                    // Execute queries.
                    List<List<?>> top10 = streamCache.query(top10Qry).getAll();
                    List<List<?>> stats = streamCache.query(statsQry).getAll();

                    // Print average count.
                    List<?> row = stats.get(0);

                    if (row.get(0) != null)
                        System.out.printf("Query results [avg=%d, min=%d, max=%d]%n",
                                row.get(0), row.get(1), row.get(2));

                    for (List<?> top10entry : top10) {
                        String word = (String) top10entry.get(0);
                        long count = (long) top10entry.get(1);

                        System.out.printf("%s: %s | ", word, count);
                    }
                    System.out.println();

                    Thread.sleep(5000);
                }
            } finally {
                // Distributed cache could be removed from cluster only by #destroyCache() call.
                ignite.destroyCache(cfg.getName());
            }
        }
    }

}
