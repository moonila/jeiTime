package org.jeinnov.jeitime.integration_tests.rh;

import org.jeinnov.jeitime.integration_tests.AbstractIntegrationCase;

public class CreerModifSupprCollegeCollaborateurTest extends AbstractIntegrationCase {

	public void test_Creer_Modif_Suppr_CollegeCollaborateur() throws Exception {
                //Connection
		selenium.open("/jeitime-ui/jeitime/pg/pages/Accueil");
		selenium.type("main_projetBean_nomProjet", "admin");
		selenium.type("j_password", "admin");
		selenium.click("//input[@value='Connexion']");
		selenium.waitForPageToLoad("30000");
		//Creation d'un college
		selenium.click("link=Répartition du temps de travail");
		selenium.waitForPageToLoad("30000");
		selenium.type("createCollege_collegeBean_nomCollegeUI", "college_test");
		selenium.type("createCollege_collegeBean_nbHeureLun", "5");
		selenium.type("createCollege_collegeBean_nbHeureMerc", "3");
		selenium.type("createCollege_collegeBean_nbHeureJeu", "2");
		selenium.type("createCollege_collegeBean_listSaisie", "0.5/5/3.2/");
		selenium.click("//input[@value='Créer']");
		selenium.waitForPageToLoad("30000");
                //Creation d'un collaborateur associe
		selenium.click("link=Gestion des Utilisateurs");
		selenium.click("link=Consulter profil Utilisateur");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Création Utilisateur");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_collaborateurBean_login", "login");
		selenium.type("main_collaborateurBean_password", "pass");
		selenium.type("main_collaborateurBean_confirmPass", "pass");
		selenium.type("main_collaborateurBean_nomColl", "nom_coll");
		selenium.type("main_collaborateurBean_prenomColl", "prenom_coll");
		selenium.select("main_collaborateurBean_idCollege", "label=college_test");
		selenium.waitForPageToLoad("30000");
		verifyEquals("5.0", selenium.getValue("main_collaborateurBean_strNbHeureLundi"));
		verifyEquals("3.0", selenium.getValue("main_collaborateurBean_strNbHeureMercredi"));
		verifyEquals("2.0", selenium.getValue("main_collaborateurBean_strNbHeureJeudi"));
		selenium.click("//input[@value='Envoyer']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Saisir, Modifier les heures des collaborateurs");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=prenom_coll nom_coll");
		selenium.waitForPageToLoad("30000");
                //Supprimer le college
		selenium.click("link=Répartition du temps de travail");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=supprimer");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Des Collaborateurs");
		selenium.click("link=Saisir, Modifier les heures des collaborateurs");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=prenom_coll nom_coll");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Saisir, Modifier les heures des collaborateurs");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("prenom_coll nom_coll"));
                //Supprimer le collaborateur
		selenium.click("link=Gestion des Utilisateurs");
		selenium.click("link=Consulter profil Utilisateur");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=supprimer");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Deconnexion");
		selenium.waitForPageToLoad("30000");
	}
}
