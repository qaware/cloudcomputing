package edu.qaware.cc.zwitscher.core;

import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.jaxrs.config.DefaultJaxrsScanner;
import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.reader.ClassReaders;
import edu.qaware.cc.zwitscher.api.resources.ZwitscherMessageResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ZwitscherApplication extends Application<ZwitscherConfiguration> {    
    
    public static void main(String[] args) throws Exception {
        new ZwitscherApplication().run(
                new String[]{"server", 
                             "./src/main/resources/zwitscher-config.yml"}); 
    }

    @Override
    /**
     * Prüft die Konfiguration
     */
    public void initialize(Bootstrap<ZwitscherConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets/swagger-ui", "/api-browser", "index.html"));
    }

    @Override
    /**
     * Komponenten registrieren und konfigurieren
     * In unserem Fall: REST Ressourcen registrieren
     */
    public void run(ZwitscherConfiguration t, Environment e) throws Exception {
        e.jersey().register(ZwitscherMessageResource.class);
        addSwagger(t, e);
    }
    
    /**
     * Swagger hinzufügen. Damit wird die API Dokumentation mit zugänglich gemacht.
     * @param e
     */
    private void addSwagger(ZwitscherConfiguration t, Environment e){
         // Swagger Resource und Providers
        e.jersey().register(new ApiListingResourceJSON());
        e.jersey().register(new ApiDeclarationProvider());
        e.jersey().register(new ResourceListingProvider());

        // Swagger Scanner, which finds all the resources for @Api Annotations
        ScannerFactory.setScanner(new DefaultJaxrsScanner());

        // Add the reader, which scans the resources and extracts the resource information
        ClassReaders.setReader(new DefaultJaxrsApiReader());

        // Set the swagger config options
        SwaggerConfig config = ConfigFactory.config();
        config.setApiVersion("1.0.0");
        config.setBasePath("..");       
    }
    
}