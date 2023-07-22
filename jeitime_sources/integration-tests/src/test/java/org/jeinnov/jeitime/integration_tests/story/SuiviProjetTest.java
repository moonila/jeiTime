package org.jeinnov.jeitime.integration_tests.story;

import org.jeinnov.jeitime.integration_tests.AbstractIntegrationCase;

public class SuiviProjetTest extends AbstractIntegrationCase {

	public void test_SuiviProjet() throws Exception {
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
		selenium.click("link=Consultation Projet");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Gestion de projet");
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
		selenium.click("link=Gestion des Types de Tâches");
		selenium.waitForPageToLoad("30000");
		selenium.type("create_nomtacheBean_nomTache", "type_tache");
		selenium.select("create_nomtacheBean_idTypeTache", "label=groupe_tache");
		selenium.click("//input[@value='Créer']");
		selenium.waitForPageToLoad("30000");
                //Creation d'une equipe et d'un collaborateur associe
		selenium.click("link=Gestion des Equipes");
		selenium.waitForPageToLoad("30000");
		selenium.type("create_equipeBean_nomEquipe", "equipe_test");
		selenium.click("//input[@value='Créer']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Création Utilisateur");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_collaborateurBean_login", "login");
		selenium.type("main_collaborateurBean_password", "pass");
		selenium.type("main_collaborateurBean_confirmPass", "pass");
		selenium.type("main_collaborateurBean_nomColl", "nom_coll");
		selenium.type("main_collaborateurBean_prenomColl", "prenom_coll");
		selenium.select("main_collaborateurBean_idEqu", "label=equipe_test");
		selenium.click("//input[@value='Envoyer']");
		selenium.waitForPageToLoad("30000");
                //Associer la tache au projet
		selenium.click("link=Gestion de projet");
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
                //Affecter une ressource au projet
		selenium.click("link=Planification");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Affecter de nouvelles ressources");
		selenium.waitForPageToLoad("30000");
		selenium.click("selection");
		selenium.click("link=Valider");
		selenium.waitForPageToLoad("30000");
		selenium.select("main_affecterBean_idEq", "label=equipe_test");
		selenium.waitForPageToLoad("30000");
		selenium.click("selection");
		selenium.click("link=Valider");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Valider");
		selenium.waitForPageToLoad("30000");
                //Saisir des heures pour le collaborateur
		selenium.click("link=Des Collaborateurs");
		selenium.click("link=Saisir, Modifier les heures des collaborateurs");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=prenom_coll nom_coll");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_saisieHeureBean_date", "05/07/2010");
//		selenium.type("main_saisieHeureBean_nbHeure", "5");
//		selenium.click("//input[@value='Valider']");
//		selenium.waitForPageToLoad("30000");
		selenium.select("main_saisieHeureBean_idProjet", "label=projet_test");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_saisieHeureBean_nbHeure", "5");
		selenium.click("//input[@value='Valider']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
                //Suivre le projet
		selenium.click("link=Des Projets");
		selenium.click("link=Suivi Mensuel");
		selenium.waitForPageToLoad("30000");
		selenium.select("//div[@id='osuit-PageContainer']/div/div/form/div[1]/div[1]/div/select", "label=Juillet");
		selenium.type("//div[@id='osuit-PageContainer']/div/div/form/div[1]/div[2]/div/input", "2010");
		selenium.click("//input[@value='Soumettre']");
		selenium.waitForPageToLoad("30000");
		selenium.click("selection");
		selenium.click("link=Afficher le tableau récapitulatif");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("5,00"));
		selenium.click("link=Des Projets");
		selenium.click("link=Suivi par période");
		selenium.waitForPageToLoad("30000");
		selenium.type("//div[@id='osuit-PageContainer']/div/div/form/div[1]/div[1]/div/input", "05/07/2010");
		selenium.click("//input[@value='Envoyer']");
		selenium.waitForPageToLoad("30000");
		selenium.type("//div[@id='osuit-PageContainer']/div/div/form/div[1]/div[2]/div/input", "06/07/2010");
		selenium.click("//input[@value='Envoyer']");
		selenium.waitForPageToLoad("30000");
		selenium.click("selection");
		selenium.click("link=Afficher les projet sélectionnés");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("5,00"));
		selenium.click("link=Des Projets");
		selenium.click("link=Par Type de Projet et par période");
		selenium.waitForPageToLoad("30000");
		selenium.type("//div[@id='osuit-PageContainer']/div/div/form/div[1]/div[1]/div/input", "05/07/2010");
		selenium.type("//div[@id='osuit-PageContainer']/div/div/form/div[1]/div[2]/div/input", "06/07/2010");
		selenium.click("//input[@value='Envoyer']");
		selenium.waitForPageToLoad("30000");
		selenium.click("selection");
		selenium.click("link=Afficher le tableau récapitulatif");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("5,00"));
                //Effacer les heures saisies
		selenium.click("link=Saisir, Modifier les heures des collaborateurs");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=prenom_coll nom_coll");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_saisieHeureBean_date", "05/07/2010");
		selenium.select("main_saisieHeureBean_idProjet", "label=projet_test");
		selenium.waitForPageToLoad("30000");
		selenium.click("//input[@value='Valider']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=5.0");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_saisieHeureBean_nbHeure", "0");
		selenium.click("//input[@value='Modifier']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("0.0"));
                //Supprimer le collaborateur et son equipe
		selenium.click("link=Gestion des Equipes");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Consulter profil Utilisateur");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=supprimer");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Gestion des Equipes");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=supprimer");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
                //Supprimer le projet et son type
		selenium.click("link=Gestion de projet");
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
