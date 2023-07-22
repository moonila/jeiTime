package org.jeinnov.jeitime.integration_tests.parametre;

import org.jeinnov.jeitime.integration_tests.AbstractIntegrationCase;

public class CreerModifSupprDomaineTest  extends AbstractIntegrationCase {


	public void test_Creer_Modif_Suppr_Domaine() throws Exception {
                //Connection
		selenium.open("/jeitime-ui/jeitime/pg/pages/Accueil");
		selenium.type("main_projetBean_nomProjet", "admin");
		selenium.type("j_password", "admin");
		selenium.click("//input[@value='Connexion']");
		selenium.waitForPageToLoad("30000");
		//Creation d'un domaine
		selenium.click("link=Gestion des Domaines");
		selenium.waitForPageToLoad("30000");
		selenium.type("create_domaineBean_nomDom", "dom_test");
		selenium.click("//input[@value='Créer']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("dom_test"));
                //Modification du domaine
		selenium.click("link=dom_test");
		selenium.waitForPageToLoad("30000");
		selenium.type("update_domaineBean_selected_nomDomaine", "dom_test2");
		selenium.click("//input[@value='Modifier']");
		selenium.waitForPageToLoad("30000");
                //Suppression du domaine
		verifyTrue(selenium.isTextPresent("dom_test2"));
		selenium.click("link=supprimer");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Deconnexion");
		selenium.waitForPageToLoad("30000");
	}
}
