package org.jeinnov.jeitime.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface DatabaseManager {
	
	@WebMethod(operationName = "updateDatabaseSchema")
	public void updateDatabaseSchema() throws Exception;
	
	@WebMethod(operationName = "dropDatabaseSchema")
	public void dropDatabaseSchema() throws Exception;
	
	@WebMethod(operationName = "initUserAndRole")
	public void initUserAndRole();
}
