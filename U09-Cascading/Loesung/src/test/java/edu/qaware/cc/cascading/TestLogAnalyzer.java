package edu.qaware.cc.cascading;

import cascading.PlatformTestCase;
import cascading.flow.Flow;
import cascading.flow.FlowDef;
import cascading.operation.Insert;
import cascading.operation.aggregator.Count;
import cascading.operation.aggregator.First;
import cascading.operation.regex.RegexParser;
import cascading.pipe.Each;
import cascading.pipe.Every;
import cascading.pipe.GroupBy;
import cascading.pipe.Pipe;
import cascading.pipe.assembly.SumBy;
import cascading.tap.Tap;
import cascading.tuple.Fields;
import org.junit.Test;

public class TestLogAnalyzer extends PlatformTestCase {

    //Feld-Definitionen
    public static final Fields IP = new Fields("IP", String.class);
    public static final Fields TIME = new Fields("TIME", String.class);
    public static final Fields METHOD = new Fields("METHOD", String.class);
    public static final Fields EVENT = new Fields("EVENT", String.class);
    public static final Fields STATUS = new Fields("STATUS", Integer.class);
    public static final Fields SIZE = new Fields("SIZE", Integer.class);
    public static final Fields COUNT = new Fields("count", Integer.class);
    public static final Fields LINE = new Fields("line", String.class);
    public static final Fields LOG_ENTRY = Fields.join(IP, TIME, METHOD, EVENT, STATUS, SIZE);

    //Pfade
    public static final String IN_PATH = TestLogAnalyzer.class.getResource("/sample.log").getPath();
    public static final String OUT_PATH_STATUS_COUNT = "log_analysis_status_count.txt";
    public static final String OUT_PATH_SIZE = "log_analysis_size.txt";

    @Test
    public void testStatusCodeAnalysis(){
        // Log-Eintrag parsen
        String logRegex = "^([^ ]*) +[^ ]* +[^ ]* +\\[([^]]*)\\] +\\\"([^ ]*) ([^ ]*) [^ ]*\\\" ([^ ]*) ([^ ]*).*$";
        RegexParser parser = new RegexParser(LOG_ENTRY, logRegex);
        Pipe pipe = new Each("log", LINE, parser);

        // Anzahl der Status-Codes ermitteln
        pipe = new GroupBy(pipe, STATUS);
        pipe = new Every(pipe, Fields.GROUP, new Count(COUNT), Fields.ALL);		
	pipe = new GroupBy(pipe, COUNT, COUNT, true);

        //Flow definieren und ausführen
        Tap inTap = getPlatform().getTextFile(IN_PATH);
        Tap statusOutTap = getPlatform().getTextFile(Fields.join(STATUS, COUNT), OUT_PATH_STATUS_COUNT);
        FlowDef flowDef = FlowDef.flowDef()
                .addSource(pipe, inTap)
                .addTailSink(pipe, statusOutTap);

        getPlatform().getFlowConnector().connect(flowDef).complete();
    }
    
    @Test
    public void testIpTransferVolumeAnalysis(){
        // Log-Eintrag parsen
        Fields logFields = Fields.join(IP, TIME, METHOD, EVENT, STATUS, SIZE);
        String logRegex = "^([^ ]*) +[^ ]* +[^ ]* +\\[([^]]*)\\] +\\\"([^ ]*) ([^ ]*) [^ ]*\\\" ([^ ]*) ([^ ]*).*$";
        RegexParser parser = new RegexParser(logFields, logRegex);
        Pipe pipe = new Each("log", LINE, parser);

        // Ausgehendes Transfervolumen pro IP-Adresse ermitteln
        pipe = new SumBy("sizeAnalysis", pipe, IP, SIZE, SIZE, Integer.class);
        
        // Nach Transfervolumen sortieren
        pipe = new Each(pipe, Fields.ALL, new Insert(new Fields("syntGroup"), 1), Fields.ALL);
        pipe = new GroupBy("sort", pipe, new Fields("syntGroup"), SIZE, true);        
        pipe = new Every(pipe, Fields.join(IP, SIZE), new First(Fields.ARGS, 5), Fields.RESULTS);

        //Flow definieren und ausführen
        Tap inTap = getPlatform().getTextFile(IN_PATH);
        Tap statusOutTap = getPlatform().getTextFile(Fields.join(IP, SIZE), OUT_PATH_SIZE);
        FlowDef flowDef = FlowDef.flowDef()
                .addSource(pipe, inTap)
                .addTailSink(pipe, statusOutTap);

        Flow flow = getPlatform().getFlowConnector().connect(flowDef);
        flow.writeDOT("flow.dot");
        flow.complete();
    }
    
}