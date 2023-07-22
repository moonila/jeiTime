package org.jeinnov.jeitime.integration_tests.parametre;

import org.jeinnov.jeitime.integration_tests.AbstractIntegrationCase;



public class CreerModifSupprGroupeTachesTest  extends AbstractIntegrationCase {

	public void test_Creer_Modif_Suppr_GroupeTaches() throws Exception {
                //Connection
		selenium.open("/jeitime-ui/jeitime/pg/pages/Accueil");
		selenium.type("main_projetBean_nomProjet", "admin");
		selenium.type("j_password", "admin");
		selenium.click("//input[@value='Connexion']");
		selenium.waitForPageToLoad("30000");
		//Creation d'un groupe de tache
		selenium.click("link=Gestion des Groupes de Tâches");
		selenium.waitForPageToLoad("30000");
		selenium.type("create_typeTacheBean_nomTypTach", "groupe_tache");
		selenium.click("//input[@value='Créer']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("groupe_tache"));
		selenium.click("link=groupe_tache");
		selenium.waitForPageToLoad("30000");
                //Modification du groupe de tache
		selenium.type("update_typeTacheBean_nomTypTach", "groupe_tache2");
		selenium.click("//input[@value='Modifier']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("groupe_tache2"));
		selenium.click("link=groupe_tache2");
		selenium.waitForPageToLoad("30000");
                //creation d'un type de tache associe
		selenium.click("link=Ajouter un Type de Tâche");
		selenium.waitForPageToLoad("30000");
		selenium.type("listTTache_typeTacheBean_nomTache", "test_tache");
		selenium.click("//input[@value='Ajouter une tâche']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("test_tache"));
		selenium.click("//input[@value='Modifier']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("supprimer"));
		selenium.click("link=Paramétrage");
		selenium.click("link=Gestion des Types de Tâches");
		selenium.waitForPageToLoad("30000");
                //Supprimer le type de tache
		verifyTrue(selenium.isTextPresent("test_tache"));
		selenium.click("link=supprimer");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Paramétrage");
		selenium.click("link=Gestion des Groupes de Tâches");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=groupe_tache2");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Pas de données"));
		selenium.click("link=Paramétrage");
                //Supprimer le groupe de tache
		selenium.click("link=Gestion des Groupes de Tâches");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=supprimer");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Deconnexion");
		selenium.waitForPageToLoad("30000");
	}
}
