package edu.qaware.cc.minicloud;

import com.hazelcast.core.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;

/**
 * Ein Kommandozeilen-Chat auf Basis von Hazelcast
 * 
 * @author Josef Adersberger
 */
public class Chatter implements ItemListener<String>{
    
    private static final String CLUSTER_ID = "Chatter";
        
    public static void main(String[] args) throws IOException {
  
        //Erzeuge eine verteilte Liste als Datenstruktur
        HazelcastInstance hz = Hazelcast.newHazelcastInstance();
        Collection<DistributedObject> cd =  hz.getDistributedObjects();
        for (DistributedObject d : cd) {
            System.out.println("Distributed Object found >>>>" + d.getName());
        }
        IList<String> messages = hz.getList(CLUSTER_ID);
        
        //Initial werden alle bisherigen Nachrichten ausgegeben
        printMessages(messages);
        
        //Es wird ein Message Listener registriert, der über alle Änderungen
        //in der Datenstruktur benachrichtig wird.
        messages.addItemListener(new Chatter(), true); 
        
        //Konsoleneingabe auswerten und neue Nachricht bei Enter absenden.
        //Die Konsoleneingabe wird beendet, sobald "!e" als Kommando eingegben
        //wird.
        String msg;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        do {
            msg = in.readLine();
            messages.add(msg);
        } while (msg != "!e");
    }

    /**
     * Gibt alle bisherigen Nachrichten aus.
     * 
     * @param messages die Liste der Nachrichten
     */
    private static void printMessages(List<String> messages){
        System.out.println("Alle Nachrichten bisher:");
        for (String msg : messages){
            System.out.println("- " + msg);
        }       
    }
      
    @Override
    /**
     * Wird aufgerufen, sobald ein neues Element in die Datenstruktur
     * hinzugefügt wird.
     */
    public void itemAdded(ItemEvent<String> ie) {
        System.out.println("Neue Nachricht: " + ie.getItem());
    }

    @Override
    /**
     * Wird aufgerufen, wenn ein Element aus der Datenstruktur gelöscht wird.
     * Dieses Ereignis wird nicht unterstützt. Die Liste der Nachrichten soll
     * konsistent und unabstreitbar bleiben.
     */
    public void itemRemoved(ItemEvent<String> ie) {
        throw new UnsupportedOperationException();
    }
    
}