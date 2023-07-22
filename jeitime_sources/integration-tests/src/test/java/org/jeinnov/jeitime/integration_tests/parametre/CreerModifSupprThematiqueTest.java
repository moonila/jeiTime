package org.jeinnov.jeitime.integration_tests.parametre;

import org.jeinnov.jeitime.integration_tests.AbstractIntegrationCase;



public class CreerModifSupprThematiqueTest  extends AbstractIntegrationCase {

	public void test_Creer_Modif_Suppr_Thematique() throws Exception {
                //Connection
		selenium.open("/jeitime-ui/jeitime/pg/pages/Accueil");
		selenium.type("main_projetBean_nomProjet", "admin");
		selenium.type("j_password", "admin");
		selenium.click("//input[@value='Connexion']");
		selenium.waitForPageToLoad("30000");
		//Creation d'une thematique
		selenium.click("link=Gestion des Thématiques");
		selenium.waitForPageToLoad("30000");
		selenium.type("create_themaBean_nomThema", "test_th");
		selenium.click("//input[@value='Créer']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("test_th"));
                //Modification de la thematique
		selenium.click("link=test_th");
		selenium.waitForPageToLoad("30000");
		selenium.type("update_themaBean_selected_nomThema", "test_th2");
		selenium.click("//input[@value='Modifier']");
		selenium.waitForPageToLoad("30000");
                //Suppression de la thematique
		verifyTrue(selenium.isTextPresent("test_th2"));
		selenium.click("link=supprimer");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Deconnexion");
		selenium.waitForPageToLoad("30000");
	}
}
