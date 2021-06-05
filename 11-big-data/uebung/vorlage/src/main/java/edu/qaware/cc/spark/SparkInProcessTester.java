package edu.qaware.cc.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

/**
 * A simple spark calculation executed in process
 *
 * @author f.lautenschlager
 */
public class SparkInProcessTester {

    private static final Logger LOGGER = LoggerFactory.getLogger(SparkInProcessTester.class);

    /**
     * Just to ensure that sparks works ;-)
     *
     * @param args ignored
     * @throws IOException if bad things happen
     */
    public static void main(String[] args) throws IOException {
        SparkConf conf = new SparkConf()
                .setAppName("Cloud Computing")
                .setMaster("local[4]");
        JavaSparkContext jsc = new JavaSparkContext(conf);

        JavaRDD<Integer> rdd = jsc.parallelize(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

        rdd.foreach((VoidFunction<Integer>) integer -> LOGGER.info("Let's start {}", integer));

        LOGGER.info("Type any key to stop");
        System.in.read();
    }
}
