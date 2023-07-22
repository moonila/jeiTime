package org.jeinnov.jeitime.integration_tests.story;


import org.jeinnov.jeitime.integration_tests.AbstractIntegrationCase;

public class SaisirHeuresConnectionAdminTest extends AbstractIntegrationCase {

	public void test_SaisirHeures_ConnectionAdmin() throws Exception {
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
		selenium.type("main_projetBean_dateFin", "07/07/2010");
		selenium.click("//input[@value='Créer le nouveau projet']");
		selenium.waitForPageToLoad("30000");
                //Creation d'une tache et de son groupe
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
                //Creation d'une equipe
		selenium.click("link=Gestion des Equipes");
		selenium.waitForPageToLoad("30000");
		selenium.type("create_equipeBean_nomEquipe", "equipe_test");
		selenium.click("//input[@value='Créer']");
		selenium.waitForPageToLoad("30000");
                //Creation d'un collaborateur associe a l'equipe
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
                //Associer une ressource au projet
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
                //Ajouter des heures depuis le collaborateur
		selenium.click("link=Saisir, Modifier les heures des collaborateurs");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=prenom_coll nom_coll");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_saisieHeureBean_date", "05/07/2010");
		selenium.select("main_saisieHeureBean_idProjet", "label=projet_test");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_saisieHeureBean_nbHeure", "4");
		selenium.click("//input[@value='Valider']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_saisieHeureBean_date", "07/07/2010");
		selenium.select("main_saisieHeureBean_idProjet", "label=projet_test");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_saisieHeureBean_nbHeure", "2");
		selenium.click("//input[@value='Valider']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
                //Verifier que les heures s'affichent dans les tableaux de bord
		selenium.click("link=Consulter tableau de bord hebdomadaire");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=prenom_coll nom_coll");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_saisieHeureBean_date", "05/07/2010");
		selenium.click("//input[@value='Valider']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Consulter tableau de bord mensuel");
		selenium.waitForPageToLoad("30000");
//		selenium.select("mainTablMens_consultMens_mois", "label=Juillet");
		selenium.select("//div[@id='osuit-PageContainer']/div/div/form/div[1]/div[1]/div/select", "label=Juillet");
		selenium.click("//input[@value='Soumettre']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Saisir, Modifier les heures des collaborateurs");
		selenium.waitForPageToLoad("30000");
                //Effacer les heures saisies
		selenium.click("link=prenom_coll nom_coll");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_saisieHeureBean_date", "05/07/2010");
		selenium.select("main_saisieHeureBean_idProjet", "label=projet_test");
		selenium.waitForPageToLoad("30000");
		selenium.click("//input[@value='Valider']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=4.0");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_saisieHeureBean_nbHeure", "0");
		selenium.click("//input[@value='Modifier']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("2.0"));
		selenium.click("link=2.0");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_saisieHeureBean_nbHeure", "0");
		selenium.click("//input[@value='Modifier']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("0.0"));
                //Supprimer le collaborateur
		selenium.click("link=Gestion des Utilisateurs");
		selenium.click("link=Consulter profil Utilisateur");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=supprimer");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
                //Supprimer l'equipe
		selenium.click("link=Gestion des Equipes");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=supprimer");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
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
                //Supprimer la tache et son groupe
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
