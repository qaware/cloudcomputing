package edu.qaware.cc.cascading;

import cascading.PlatformTestCase;
import cascading.flow.FlowDef;
import cascading.operation.regex.RegexParser;
import cascading.pipe.Each;
import cascading.pipe.Pipe;
import cascading.tap.Tap;
import cascading.tuple.Fields;
import org.junit.Ignore;
import org.junit.Test;


public class TestLogAnalyzerTemplate extends PlatformTestCase {

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

    @Ignore
    @Test
    public void testStatusCodeAnalysis(){

        // (1) Log-Eintrag parsen
        String logRegex = "^([^ ]*) +[^ ]* +[^ ]* +\\[([^]]*)\\] +\\\"([^ ]*) ([^ ]*) [^ ]*\\\" ([^ ]*) ([^ ]*).*$";
        RegexParser parser = new RegexParser(LOG_ENTRY, logRegex);
        Pipe pipe = new Each("log", LINE, parser);

        // (2) Pipe Assembly aufbauen
        // Anzahl der Status-Codes ermitteln: STATUS -> Anzahl(STATUS)
        //TODO

        // (3) Taps definieren
        Tap inTap = getPlatform().getTextFile(IN_PATH);
        Tap statusOutTap = null; //TODO

        // (4) Flow definieren und ausführen
        FlowDef flowDef = null; //TODO
        getPlatform().getFlowConnector().connect(flowDef).complete();

    }

    @Ignore
    @Test
    public void testIpTransferVolumeAnalysis(){
        // (1) Log-Eintrag parsen
        //TODO

        // (2) Pipe Assembly aufbauen
        // (2.1) Ausgehendes Transfervolumen pro IP-Adresse ermitteln (Summe SIZE Feld)
        //TODO
        // (2.2) Nach Transfervolumen sortieren
        //TODO

        // (3) Taps definieren
        //TODO

        // (4) Flow definieren und ausführen
        //TODO
    }

}
