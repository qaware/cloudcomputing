package de.qaware.cloudcomputing.bigdata.ignite;

import org.apache.ignite.cache.affinity.AffinityUuid;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;

import javax.cache.configuration.FactoryBuilder;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class IgniteConfigurationProvider {

    private static final List<String> CLUSTER_NODES = List.of("127.0.0.1:47500..47509");
    private static final String CACHENAME = "wordCount";
    private static final int CACHE_SLIDING_WINDOW_SECONDS = 1;

    private IgniteConfigurationProvider() {
    }

    public static IgniteConfiguration getIgniteConfiguration() {
        // Preparing IgniteConfiguration using Java APIs
        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();

        // The node will be started as a client node.
        igniteConfiguration.setClientMode(true);

        // Classes of custom Java logic will be transferred over the wire from this app.
        igniteConfiguration.setPeerClassLoadingEnabled(true);

        TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
        ipFinder.setAddresses(CLUSTER_NODES);
        igniteConfiguration.setDiscoverySpi(new TcpDiscoverySpi().setIpFinder(ipFinder));

        return igniteConfiguration;
    }

    public static CacheConfiguration<AffinityUuid, String> getCacheConfiguration() {
        CacheConfiguration<AffinityUuid, String> configuration = new CacheConfiguration<>(CACHENAME);

        configuration.setIndexedTypes(AffinityUuid.class, String.class);
        configuration.setExpiryPolicyFactory(FactoryBuilder.factoryOf(new CreatedExpiryPolicy(new Duration(TimeUnit.SECONDS, CACHE_SLIDING_WINDOW_SECONDS))));

        return configuration;
    }

}
