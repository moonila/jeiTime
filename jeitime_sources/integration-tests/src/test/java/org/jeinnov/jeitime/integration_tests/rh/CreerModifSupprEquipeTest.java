package org.jeinnov.jeitime.integration_tests.rh;

import org.jeinnov.jeitime.integration_tests.AbstractIntegrationCase;

public class CreerModifSupprEquipeTest extends AbstractIntegrationCase {

	public void test_Creer_Modif_Suppr_Equipe() throws Exception {
                //Connection
		selenium.open("/jeitime-ui/jeitime/pg/pages/Accueil");
		selenium.type("main_projetBean_nomProjet", "admin");
		selenium.type("j_password", "admin");
		selenium.click("//input[@value='Connexion']");
		selenium.waitForPageToLoad("30000");
		//Creation d'une equipe
		selenium.click("link=Gestion des Equipes");
		selenium.waitForPageToLoad("30000");
		selenium.type("create_equipeBean_nomEquipe", "equipe_test");
		selenium.type("create_equipeBean_fonctionEquipe", "fction_test");
		selenium.click("//input[@value='Créer']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("equipe_test"));
		verifyTrue(selenium.isTextPresent("fction_test"));
		selenium.click("link=equipe_test");
		selenium.waitForPageToLoad("30000");
		selenium.type("update_equipeBean_selected_nomEquip", "equipe_test2");
                //Modification de l'equipe
		selenium.click("//input[@value='Modifier']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("equipe_test2"));
                //Suppression de l'equipe
		selenium.click("link=supprimer");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Deconnexion");
		selenium.waitForPageToLoad("30000");
	}
}
