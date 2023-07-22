package org.jeinnov.jeitime.integration_tests.rh;

import org.jeinnov.jeitime.integration_tests.AbstractIntegrationCase;

public class CreerModifSupprCollaborateurTest extends AbstractIntegrationCase {

	public void test_Creer_Modif_Suppr_Collaborateur() throws Exception {
                //Connection
		selenium.open("/jeitime-ui/jeitime/pg/pages/Accueil");
		selenium.type("main_projetBean_nomProjet", "admin");
		selenium.type("j_password", "admin");
		selenium.click("//input[@value='Connexion']");
		selenium.waitForPageToLoad("30000");
		//Creation d'un collaborateur
		selenium.click("link=Gestion des Utilisateurs");
		selenium.click("link=Création Utilisateur");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_collaborateurBean_login", "login");
		selenium.type("main_collaborateurBean_password", "pass");
		selenium.type("main_collaborateurBean_confirmPass", "pass");
		selenium.type("main_collaborateurBean_nomColl", "nom_coll");
		selenium.type("main_collaborateurBean_prenomColl", "prenom_coll");
		selenium.type("main_collaborateurBean_strNbHeureLundi", "5");
		selenium.type("main_collaborateurBean_strNbHeureMercredi", "2");
		selenium.type("main_collaborateurBean_strNbHeureAnnuel", "10");
		selenium.type("main_collaborateurBean_strNbHeureMens", "100");
		selenium.type("main_collaborateurBean_salaireColl", "80");
		selenium.type("main_collaborateurBean_chargeColl", "5");
		selenium.click("//input[@value='Envoyer']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("prenom_coll nom_coll"));
		verifyTrue(selenium.isTextPresent("login"));
		verifyTrue(selenium.isTextPresent("Collaborateur"));
		verifyTrue(selenium.isTextPresent("Temps Plein"));
                //Modification du collaborateur
		selenium.click("link=prenom_coll nom_coll");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Modifier");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_collaborateurBean_nomColl", "nom_coll2");
		selenium.click("//input[@value='Sauvegarder']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Consulter profil Utilisateur");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("prenom_coll nom_coll2"));
                //Suppression du collaborateur
		selenium.click("link=supprimer");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Deconnexion");
		selenium.waitForPageToLoad("30000");
	}
}
