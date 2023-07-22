package org.jeinnov.jeitime.integration_tests.projet;

import org.jeinnov.jeitime.integration_tests.AbstractIntegrationCase;

public class CreerModifSupprProjetDomaineTest extends AbstractIntegrationCase {

	public void test_Creer_Modif_Suppr_ProjetDomaine() throws Exception {
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
                //Creation d'un domaine
		selenium.click("link=Gestion des Domaines");
		selenium.waitForPageToLoad("30000");
		selenium.type("create_domaineBean_nomDom", "dom_test");
		selenium.click("//input[@value='Créer']");
		selenium.waitForPageToLoad("30000");
                //Associer le domaine au projet
		selenium.click("link=Gestion de projet");
		selenium.click("link=Consultation Projet");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=projet_test");
		selenium.waitForPageToLoad("30000");
		selenium.select("main_projetBean_idDom", "label=dom_test");
		selenium.click("//input[@value='Modifier le Projet']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Paramétrage");
		selenium.click("link=Gestion des Domaines");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("supprimer"));
                //Supprimer le projet et son type
		selenium.click("link=Gestion de projet");
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
                 //Supprimer le domaine
		selenium.click("link=Gestion des Domaines");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=supprimer");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Deconnexion");
		selenium.waitForPageToLoad("30000");
	}
}
