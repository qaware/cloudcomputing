package edu.qaware.cc.minicloud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Chatter {
    
        
    public static void main(String[] args) throws IOException {
        
        //TODO-1: Passende Hazelcast-Datenstruktur initialisieren
        
        //TODO-2: Alle bisherigen Nachrichten ausgeben    
        
        //TODO-3: Die Klasse Chatter als Listener an der Hazelcast-Datenstruktur
        //        regisrieren, damit sie bei neuen Nachrichten (= Änderungen an der Datenstruktur)
        //        informiert wird und diese dann auf der Kommandozeile ausgibt.
        
        String msg;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        do {
            msg = in.readLine();
            //TODO-4: Nachricht (msg) zur Datenstruktur hinzufügen (senden)
        } while (msg != "!e");
    }
      
}