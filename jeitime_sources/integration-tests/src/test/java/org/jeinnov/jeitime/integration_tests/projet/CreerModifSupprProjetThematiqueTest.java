package org.jeinnov.jeitime.integration_tests.projet;

import org.jeinnov.jeitime.integration_tests.AbstractIntegrationCase;


public class CreerModifSupprProjetThematiqueTest extends AbstractIntegrationCase {

	public void test_Creer_Modif_Suppr_ProjetThematique() throws Exception {
                //Connection
		selenium.open("/jeitime-ui/jeitime/pg/pages/Accueil");
		selenium.type("main_projetBean_nomProjet", "admin");
		selenium.type("j_password", "admin");
		selenium.click("//input[@value='Connexion']");
		selenium.waitForPageToLoad("30000");
		//Creation d'un projet et de son type
		selenium.click("link=Paramétrage");
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
                //Creation d'une thematique
		selenium.click("link=Paramétrage");
		selenium.click("link=Gestion des Thématiques");
		selenium.waitForPageToLoad("30000");
		selenium.type("create_themaBean_nomThema", "test_th");
		selenium.click("//input[@value='Créer']");
		selenium.waitForPageToLoad("30000");
                //Associer la thematique au projet
		selenium.click("link=Consultation Projet");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=projet_test");
		selenium.waitForPageToLoad("30000");
		selenium.select("main_projetBean_idThema", "label=test_th");
		selenium.click("//input[@value='Modifier le Projet']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Gestion des Thématiques");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("supprimer"));
                //Supprimer le projet et son type
		selenium.click("link=Consultation Projet");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Supprimer");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Gestion des Types de Projets");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=supprimer");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
                //Supprimer la thematique
		selenium.click("link=Gestion des Thématiques");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=supprimer");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Deconnexion");
		selenium.waitForPageToLoad("30000");
	}
}
