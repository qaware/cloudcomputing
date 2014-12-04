package edu.qaware.cc.minicloud;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import com.hazelcast.core.ItemEvent;
import com.hazelcast.core.ItemListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Ein Kommandozeilen-Chat auf Basis von Hazelcast
 * 
 * @author Josef Adersberger
 */
public class Chatter implements ItemListener<String>{
    
    private static HazelcastInstance hz = Hazelcast.newHazelcastInstance();
    private static IList<String> messages = hz.getList("Chatter");
        
    public static void main(String[] args) throws IOException {
        printMessageHistory();
        messages.addItemListener(new Chatter(), true); 
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
      
    @Override
    public void itemAdded(ItemEvent<String> ie) {
        System.out.println("Neue Nachricht: " + ie.getItem());
        printMessageHistory();
    }

    @Override
    public void itemRemoved(ItemEvent<String> ie) {}
    
}
