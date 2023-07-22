package org.jeinnov.jeitime.integration_tests.story;

import org.jeinnov.jeitime.integration_tests.AbstractIntegrationCase;

public class SuiviProjetParTypeProjetTest extends AbstractIntegrationCase{

	public void testSuiviProjetParTypeProjet() throws Exception {
		
		//Connection
		selenium.open("/jeitime-ui/jeitime/pg/pages/Accueil");
		selenium.type("main_projetBean_nomProjet", "admin");
		selenium.type("j_password", "admin");
		selenium.click("//input[@value='Connexion']");
		selenium.waitForPageToLoad("30000");
		
		//Création du type de projet 
		selenium.click("link=Gestion des Types de Projets");
		selenium.waitForPageToLoad("30000");
		selenium.type("create_typeProjetBean_nomTypeProjet", "Recherche");
		selenium.click("//input[@value='Créer']");
		selenium.waitForPageToLoad("30000");
		
		//Création Des groupes de taches et des tâches 
		selenium.click("link=Gestion des Groupes de Tâches");
		selenium.waitForPageToLoad("30000");
		selenium.type("create_typeTacheBean_nomTypTach", "groupe1");
		selenium.click("//input[@value='Créer']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=groupe1");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Ajouter un Type de Tâche");
		selenium.waitForPageToLoad("30000");
		selenium.type("listTTache_typeTacheBean_nomTache", "analyse");
		selenium.click("//input[@value='Ajouter une tâche']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Ajouter un Type de Tâche");
		selenium.waitForPageToLoad("30000");
		selenium.type("listTTache_typeTacheBean_nomTache", "conception");
		selenium.click("//input[@value='Ajouter une tâche']");
		selenium.waitForPageToLoad("30000");
		
		//Création d'un projet
		selenium.click("link=Création Projet");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_projetBean_nomProjet", "test");
		selenium.type("main_projetBean_dateDebu", "01/01/2011");
		selenium.click("//input[@value='Créer le nouveau projet']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=test");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Tâches Associées");
		selenium.waitForPageToLoad("30000");
		selenium.select("listTache_tacheBean_idType", "label=groupe1");
		selenium.waitForPageToLoad("30000");
		selenium.click("listTache_tacheBean_eligible");
		selenium.click("//input[@value='Ajouter une tâche']");
		selenium.waitForPageToLoad("30000");
		selenium.select("listTache_tacheBean_idType", "label=groupe1");
		selenium.waitForPageToLoad("30000");
		selenium.select("listTache_tacheBean_idNom", "label=conception");
		selenium.click("//input[@value='Ajouter une tâche']");
		selenium.waitForPageToLoad("30000");
		
		//Création d'un collaborateur
		selenium.click("link=Création Utilisateur");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_collaborateurBean_login", "login");
		selenium.type("main_collaborateurBean_password", "login");
		selenium.type("main_collaborateurBean_confirmPass", "login");
		selenium.type("main_collaborateurBean_nomColl", "lala");
		selenium.type("main_collaborateurBean_prenomColl", "lala");
		selenium.click("//input[@value='Envoyer']");
		selenium.waitForPageToLoad("30000");
		
		//Affectation du nouveau collaborateur avec le projet créé 
		selenium.click("link=Affecter une Ressource");
		selenium.waitForPageToLoad("30000");
		selenium.click("//input[@value='Suivant']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Tout sélectionner");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Valider");
		selenium.waitForPageToLoad("30000");
		selenium.select("main_affecterBean_idEq", "label=--Tous les Collaborateurs--");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Tout sélectionner");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Valider");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Valider");
		selenium.waitForPageToLoad("30000");
		
		//Saisie des heures pour le collaborateur sur les tâches du projet
		selenium.click("link=Saisir, Modifier les heures des collaborateurs");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=lala lala");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_saisieHeureBean_date", "19/01/2011");
		selenium.select("main_saisieHeureBean_idProjet", "label=test");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_saisieHeureBean_nbHeure", "2");
		selenium.click("//input[@value='Valider']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_saisieHeureBean_date", "20/01/2011");
		selenium.select("main_saisieHeureBean_idProjet", "label=test");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_saisieHeureBean_nbHeure", "5");
		selenium.click("//input[@value='Valider']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("5.0"));
		verifyTrue(selenium.isTextPresent("2.0"));
		verifyTrue(selenium.isTextPresent("7.0"));
		
		//Modification d'une saisie d'heure 
		selenium.click("link=5.0");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_saisieHeureBean_nbHeure", "8");
		selenium.click("//input[@value='Modifier']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("8.0"));
		verifyTrue(selenium.isTextPresent("2.0"));
		verifyTrue(selenium.isTextPresent("10.0"));
		selenium.select("main_saisieHeureBean_idProjet", "label=test");
		selenium.waitForPageToLoad("30000");
		selenium.select("main_saisieHeureBean_idTache", "label=conception");
		selenium.type("main_saisieHeureBean_date", "19/01/2011");
		selenium.type("main_saisieHeureBean_nbHeure", "7");
		selenium.click("//input[@value='Valider']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("7.0"));
		verifyTrue(selenium.isTextPresent("10.0"));
		verifyTrue(selenium.isTextPresent("17.0"));
		
		//Consultation du tableau récapitulatif des types de projet
		selenium.click("link=Par Type de Projet et par période");
		selenium.waitForPageToLoad("30000");
		selenium.type("//div[@id='osuit-PageContainer']/div/div/form/div[1]/div[1]/div/input", "01/01/2011");
		selenium.type("//div[@id='osuit-PageContainer']/div/div/form/div[1]/div[2]/div/input", "31/09/2011");
		selenium.click("//input[@value='Envoyer']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Tout sélectionner");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Afficher le tableau récapitulatif");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("10,00"));
		verifyTrue(selenium.isTextPresent("10,00"));
		verifyTrue(selenium.isTextPresent("7,00"));
		verifyTrue(selenium.isTextPresent("7,00"));
		verifyTrue(selenium.isTextPresent("17,00"));
		verifyTrue(selenium.isTextPresent("17,00"));
		verifyTrue(selenium.isTextPresent("17,00"));
		
		//Fin du test
		selenium.click("link=Deconnexion");
		selenium.waitForPageToLoad("30000");
	}
}
