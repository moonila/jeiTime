package org.jeinnov.jeitime.integration_tests;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.jeinnov.jeitime.ws.DatabaseManager;

import com.thoughtworks.selenium.SeleneseTestCase;

public class AbstractIntegrationCase extends SeleneseTestCase {
    
    public void setUp() throws Exception {
        
        URL wsdlURL = new URL("http://localhost:8080/jeitime-ui/ws/DatabaseManagerService?wsdl");
        QName SERVICE_NAME = new QName("http://ws.jeitime.jeinnov.org/",
                "DatabaseManagerImplService");
        Service service = Service.create(wsdlURL, SERVICE_NAME);
        DatabaseManager client = service.getPort(DatabaseManager.class);
        client.updateDatabaseSchema();
        client.initUserAndRole();
        setUp("http://localhost:8080/", "*firefox");
        
        //Thread.sleep(10000);
    }
    
    @Override
    public void tearDown() throws Exception {
        // Recreate database
    	 URL wsdlURL = new URL("http://localhost:8080/jeitime-ui/ws/DatabaseManagerService?wsdl");
         QName SERVICE_NAME = new QName("http://ws.jeitime.jeinnov.org/",
                 "DatabaseManagerImplService");
        Service service = Service.create(wsdlURL, SERVICE_NAME);
         DatabaseManager client = service.getPort(DatabaseManager.class);
        client.dropDatabaseSchema();

        super.tearDown();
    }

}
