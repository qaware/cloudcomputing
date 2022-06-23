package de.qaware.cloudcomputing.bigdata.adapter;

import org.apache.commons.collections4.ListUtils;
import org.apache.ignite.IgniteException;
import org.apache.ignite.compute.ComputeJob;
import org.apache.ignite.compute.ComputeJobAdapter;
import org.apache.ignite.compute.ComputeJobResult;
import org.apache.ignite.compute.ComputeTaskSplitAdapter;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WordCountSplitAdapter extends ComputeTaskSplitAdapter<String, Map<String, Integer>> {

    private static final String SEPARATOR_CHAR = "\\s+";
    private static final int PARTITION_SIZE = 1000;

    protected List<List<String>> partition(List<String> words) {
        return ListUtils.partition(words, PARTITION_SIZE);
    }

    @Override
    protected final Collection<ComputeJob> split(int gridSize, String arg) throws IgniteException {
        List<String> words = Arrays.stream(arg.split(SEPARATOR_CHAR)).collect(Collectors.toList());

        List<List<String>> partitionedWordList = partition(words);

        List<ComputeJob> jobs = new ArrayList<>(partitionedWordList.size());

        for (List<String> list : partitionedWordList) {
            List<String> copy = new ArrayList<>(list);

            ComputeJobAdapter adapter = new ComputeJobAdapter() {
                @Override
                public Object execute() throws IgniteException {
                    // TODO: Map
                    // Hier soll eine Map<String, Integer> mit den einzelnen Wörtern herauskommen.
                }
            };
            jobs.add(adapter);
        }

        return jobs;
    }

    @Override
    public @Nullable Map<String, Integer> reduce(List<ComputeJobResult> results) throws IgniteException {
        Map<String, Integer> counts = new HashMap<>();

        for (ComputeJobResult result : results) {
            Map<String, Integer> resultMap = result.getData();

            // TODO: Reduce-Funktion
        }

        return counts;
    }

}
