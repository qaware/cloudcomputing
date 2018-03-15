package edu.qaware.cc.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.util.SystemClock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * A word count example implemented with Apache Spark
 *
 * @author f.lautenschlager
 */
public class SparkWordCount {

    private static final Logger LOGGER = LoggerFactory.getLogger(SparkInProcessTester.class);


    /**
     * TODO: Set the path to the this directory
     */
    private static final String PATH_TO_YOUR_DIRECTORY = "/home/flo/Development/cloud-computing/cloudcomputing/09-big-data/uebung/loesung";
    private static final String PATH_TO_JAR = PATH_TO_YOUR_DIRECTORY + "/spark-lib/user-classes-for-spark.jar";

    /**
     * TODO: Set the path to your local master. Note: localhost does not work.
     */
    private static final String SPARK_MASTER = "spark://flo-ThinkPad-T440p:7077";

    /**
     * The function to extract words from a line
     */
    private static final FlatMapFunction<String, String> WORDS_EXTRACTOR =
            new FlatMapFunction<String, String>() {
                @Override
                public Iterable<String> call(String s) throws Exception {
                    return Arrays.asList(s.split(" "));
                }
            };

    /**
     * The function to map the extracted words into word, integer pairs
     */
    private static final PairFunction<String, String, Integer> WORDS_MAPPER =
            new PairFunction<String, String, Integer>() {
                @Override
                public Tuple2<String, Integer> call(String s) throws Exception {
                    return new Tuple2<String, Integer>(s, 1);
                }
            };

    /**
     * The function to reduce the pairs of word and integer
     */
    private static final Function2<Integer, Integer, Integer> WORDS_REDUCER =
            new Function2<Integer, Integer, Integer>() {
                @Override
                public Integer call(Integer a, Integer b) throws Exception {
                    return a + b;
                }
            };


    /**
     * Remember: run maven install before executing this class
     * <p>
     * Note: Before executing this example, set the correct spark master url and start the cluster!!
     *
     * @param args ignored
     * @throws IOException if bad things happen
     */
    public static void main(String[] args) throws IOException {

        SparkConf conf = new SparkConf()
                .setAppName("Cloud Computing")
                .setMaster(SPARK_MASTER);
        JavaSparkContext jsc = new JavaSparkContext(conf);
        //Required to execute the calculation on each worker
        jsc.addJar(new File(PATH_TO_JAR).getPath());

        //Start the computation below
        long start = System.currentTimeMillis();

        //We read the file from the resources directory
        JavaRDD<String> file = jsc.textFile(SparkWordCount.class.getResource("/largeText.txt").getPath());

        JavaRDD<String> words = file.flatMap(WORDS_EXTRACTOR);
        JavaPairRDD<String, Integer> pairs = words.mapToPair(WORDS_MAPPER);
        JavaPairRDD<String, Integer> counter = pairs.reduceByKey(WORDS_REDUCER);

        //Collect them to a map. Executes the whole computation!
        Map<String, Integer> result = counter.collectAsMap();

        //we are done
        long end = System.currentTimeMillis();


        //Print somt stats
        LOGGER.info("================================================================");
        LOGGER.info("Counted {} words in {} ms", result.keySet().size(), end - start);
        LOGGER.info("================================================================");


        //Just for you to open the job ui
        LOGGER.info("================================================================");
        LOGGER.info("Type any key to stop...");
        LOGGER.info("================================================================");
        System.in.read();
    }
}