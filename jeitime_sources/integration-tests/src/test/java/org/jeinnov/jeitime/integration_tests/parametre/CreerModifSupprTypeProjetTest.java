package org.jeinnov.jeitime.integration_tests.parametre;

import org.jeinnov.jeitime.integration_tests.AbstractIntegrationCase;


public class CreerModifSupprTypeProjetTest  extends AbstractIntegrationCase {

	public void test_Creer_Modif_Suppr_Type_Projet() throws Exception {
                //Connection
		selenium.open("/jeitime-ui/jeitime/pg/pages/Accueil");
		selenium.type("main_projetBean_nomProjet", "admin");
		selenium.type("j_password", "admin");
		selenium.click("//input[@value='Connexion']");
		selenium.waitForPageToLoad("30000");
		//Creation d'un type de projet
		selenium.click("link=Paramétrage");
		selenium.click("link=Gestion des Types de Projets");
		selenium.waitForPageToLoad("30000");
		selenium.type("create_typeProjetBean_nomTypeProjet", "type_projet_test");
		selenium.click("//input[@value='Créer']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("type_projet_test"));
                //Modification du type de projet
		selenium.click("link=type_projet_test");
		selenium.waitForPageToLoad("30000");
		selenium.type("update_typeProjetBean_selected_nomTypePro", "type_projet_test2");
		selenium.click("//input[@value='Modifier']");
		selenium.waitForPageToLoad("30000");
                //Suppression du type de projet
		verifyTrue(selenium.isTextPresent("type_projet_test2"));
		selenium.click("link=supprimer");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Deconnexion");
		selenium.waitForPageToLoad("30000");
	}
}
