/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.qaware.cc.reactiveZwitscher;

import edu.qaware.cc.reactiveZwitscher.connectors.nytimes.NYTimesConnector;
import java.util.List;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import org.junit.Test;

/**
 *
 * @author f.lautenschlager
 */
public class NYTimesConnectorTest {
    
      /**
     * Test of getArticleTitlesFor method, of class NYTimesConnector.
     */
    @Test
    public void testGetArticleTitlesFor() {
        NYTimesConnector instance = new NYTimesConnector();
        List<String> result = instance.getArticleTitlesFor("nsa");
        assertThat(result, not(empty()));        
    }
    
}
