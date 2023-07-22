package org.jeinnov.jeitime.integration_tests.parametre;

import org.jeinnov.jeitime.integration_tests.AbstractIntegrationCase;



public class CreerModifSupprClientPartenaireTest extends AbstractIntegrationCase {

	public void test_Creer_Modif_Suppr_ClientPartenaire() throws Exception {
                //Connection
		selenium.open("/jeitime-ui/jeitime/pg/pages/Accueil");
		selenium.type("main_projetBean_nomProjet", "admin");
		selenium.type("j_password", "admin");
		selenium.click("//input[@value='Connexion']");
		selenium.waitForPageToLoad("30000");
		//Creation d'un client
		selenium.click("link=Paramétrage");
		selenium.click("link=Gestion des Clients/Partenaires");
		selenium.waitForPageToLoad("30000");
		selenium.type("create_clientPartBean_nomClientPart", "entreprise_test");
		selenium.type("create_clientPartBean_nomService", "service_test");
		selenium.type("create_clientPartBean_nomContact", "contact_test");
		selenium.type("create_clientPartBean_commentaire", "test");
		selenium.click("//input[@value='Créer']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("entreprise_test"));
		verifyTrue(selenium.isTextPresent("service_test"));
		verifyTrue(selenium.isTextPresent("contact_test"));
		verifyTrue(selenium.isTextPresent("test"));
                //Modification du client
		selenium.click("link=entreprise_test");
		selenium.waitForPageToLoad("30000");
		selenium.type("update_clientPartBean_selected_nomClientPart", "entreprise_test2");
		selenium.click("//input[@value='Modifier']");
		selenium.waitForPageToLoad("30000");
                //Suppression du client
		verifyTrue(selenium.isTextPresent("entreprise_test2"));
		selenium.click("link=supprimer");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Deconnexion");
		selenium.waitForPageToLoad("30000");
	}
}
