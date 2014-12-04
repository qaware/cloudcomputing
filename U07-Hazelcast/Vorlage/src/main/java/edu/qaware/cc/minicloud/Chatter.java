package edu.qaware.cc.minicloud;

import com.hazelcast.core.IList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Chatter {
    
    //TODO: Hazelcast-Liste erzeugen und in statischem Feld ablegen
    //Siehe: http://docs.hazelcast.org/docs/latest/manual/html/list.html
    private static IList<String> messages = null;
        
    public static void main(String[] args) throws IOException {
        
        //Gleich zum Start die komplette Liste der bisherigen Nachrichten ausgeben
        printMessageHistory();
        
        //TODO: Diese Klasse als ItemListener an die IList messages hängen.
        //      Sobald ein neuer Eintrag in die Liste kommt, wird dieser und die
        //      gesamte Liste ausgegeben.
        //Siehe: http://docs.hazelcast.org/docs/2.0/javadoc/com/hazelcast/core/IList.html
        
        String msg;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        do {
            msg = in.readLine();
            messages.add(msg);
        } while (msg != "exit");
    }

    private static void printMessageHistory(){
        System.out.println("Alle Nachrichten bisher:");
        for (String msg : messages){
            System.out.println("- " + msg);
        }       
    }
      
}