package org.jeinnov.jeitime.integration_tests.story;

import org.jeinnov.jeitime.integration_tests.AbstractIntegrationCase;

public class CreerModifSupprProjetTacheTest extends AbstractIntegrationCase {

	public void test_Creer_Modif_Suppr_ProjetTache() throws Exception {
                //Connection
		selenium.open("/jeitime-ui/jeitime/pg/pages/Accueil");
		selenium.type("main_projetBean_nomProjet", "admin");
		selenium.type("j_password", "admin");
		selenium.click("//input[@value='Connexion']");
		selenium.waitForPageToLoad("30000");
		//Creation d'un projet et de son type
		selenium.click("link=Gestion des Types de Projets");
		selenium.waitForPageToLoad("30000");
		selenium.type("create_typeProjetBean_nomTypeProjet", "type_projet_test");
		selenium.click("//input[@value='Créer']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Création Projet");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_projetBean_nomProjet", "projet_test");
		selenium.type("main_projetBean_dateDebu", "05/07/2010");
		selenium.click("//input[@value='Créer le nouveau projet']");
		selenium.waitForPageToLoad("30000");
                //Creation d'un type de tache et de son groupe
		selenium.click("link=Gestion des Groupes de Tâches");
		selenium.waitForPageToLoad("30000");
		selenium.type("create_typeTacheBean_nomTypTach", "groupe_tache");
		selenium.click("//input[@value='Créer']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Paramétrage");
		selenium.click("link=Gestion des Types de Tâches");
		selenium.waitForPageToLoad("30000");
		selenium.type("create_nomtacheBean_nomTache", "type_tache");
		selenium.select("create_nomtacheBean_idTypeTache", "label=groupe_tache");
		selenium.click("//input[@value='Créer']");
		selenium.waitForPageToLoad("30000");
                //Associer la tache au projet
		selenium.click("link=Consultation Projet");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=projet_test");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Tâches Associées");
		selenium.waitForPageToLoad("30000");
		selenium.select("listTache_tacheBean_idType", "label=groupe_tache");
		selenium.waitForPageToLoad("30000");
		selenium.click("//input[@value='Ajouter une tâche']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("groupe_tache"));
		verifyTrue(selenium.isTextPresent("type_tache"));
		selenium.click("link=supprimer");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Pas de données"));
                //Supprimer le projet et son type
		selenium.click("link=Consultation Projet");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Supprimer");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Paramétrage");
		selenium.click("link=Gestion des Types de Projets");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=supprimer");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
                //Supprimer le type de tache et son groupe
		selenium.click("link=Gestion des Types de Tâches");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=supprimer");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Paramétrage");
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
