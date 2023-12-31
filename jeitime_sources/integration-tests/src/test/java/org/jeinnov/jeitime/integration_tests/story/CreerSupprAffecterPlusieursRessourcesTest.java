package org.jeinnov.jeitime.integration_tests.story;

import org.jeinnov.jeitime.integration_tests.AbstractIntegrationCase;

public class CreerSupprAffecterPlusieursRessourcesTest extends AbstractIntegrationCase {

	public void test_Creer_Suppr_AffecterPlusieursRessources() throws Exception {
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
		selenium.click("link=Gestion des Types de Projets");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Création Projet");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_projetBean_nomProjet", "projet_test");
		selenium.type("main_projetBean_dateDebu", "05/07/2010");
		selenium.type("main_projetBean_dateFin", "07/07/2010");
		selenium.click("//input[@value='Créer le nouveau projet']");
		selenium.waitForPageToLoad("30000");
                //Creation de deux type de tache et de leur groupe
		selenium.click("link=Paramétrage");
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
		selenium.type("create_nomtacheBean_nomTache", "type_tache2");
		selenium.select("create_nomtacheBean_idTypeTache", "label=groupe_tache");
		selenium.click("//input[@value='Créer']");
		selenium.waitForPageToLoad("30000");
                //Creation d'une equipe et de deux collaborateurs associes
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
		selenium.click("//input[@value='Envoyer']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Gestion des Utilisateurs");
		selenium.click("link=Création Utilisateur");
		selenium.waitForPageToLoad("30000");
		selenium.type("main_collaborateurBean_login", "login2");
		selenium.type("main_collaborateurBean_password", "pass2");
		selenium.type("main_collaborateurBean_confirmPass", "pass2");
		selenium.type("main_collaborateurBean_nomColl", "nom_coll2");
		selenium.type("main_collaborateurBean_prenomColl", "prenom_coll2");
		selenium.select("main_collaborateurBean_idEqu", "label=equipe_test");
		selenium.click("//input[@value='Envoyer']");
		selenium.waitForPageToLoad("30000");
                //Associer les taches au projet
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
		selenium.select("listTache_tacheBean_idType", "label=groupe_tache");
		selenium.waitForPageToLoad("30000");
		selenium.select("listTache_tacheBean_idNom", "label=type_tache2");
		selenium.click("//input[@value='Ajouter une tâche']");
		selenium.waitForPageToLoad("30000");
                //Affecter deux ressources au projet
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
		selenium.click("link=Gestion de projet");
		selenium.click("link=Consultation Projet");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=projet_test");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Planification");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Affecter de nouvelles ressources");
		selenium.waitForPageToLoad("30000");
		selenium.click("//table[@id='tableTache']/tbody/tr[2]/td[1]/input");
		selenium.click("link=Valider");
		selenium.waitForPageToLoad("30000");
		selenium.select("main_affecterBean_idEq", "label=equipe_test");
		selenium.waitForPageToLoad("30000");
		selenium.click("selection");
		selenium.click("link=Valider");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Valider");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Gestion de projet");
		selenium.click("link=Consultation Projet");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=projet_test");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Planification");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("prenom_coll2 nom_coll2"));
		verifyTrue(selenium.isTextPresent("prenom_coll2 nom_coll2"));
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
                //Supprimer les collaborateurs et leur equipe
		selenium.click("link=Gestion des Utilisateurs");
		selenium.click("link=Consulter profil Utilisateur");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=supprimer");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
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
                //Supprimer le groupe de tache et ses types de tache
		selenium.click("link=Gestion des Types de Tâches");
		selenium.waitForPageToLoad("30000");
		selenium.click("//table[@id='tableTach']/tbody/tr[2]/td[3]/a");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
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
