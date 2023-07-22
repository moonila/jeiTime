package org.jeinnov.jeitime.integration_tests.rh;

import org.jeinnov.jeitime.integration_tests.AbstractIntegrationCase;


public class CreerModifSupprCollegeTest extends AbstractIntegrationCase {

	public void test_Creer_Modif_Suppr_College() throws Exception {
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
		selenium.click("//input[@value='Créer']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("college_test"));
		selenium.click("link=college_test");
		selenium.waitForPageToLoad("30000");
                //Modification du college
		selenium.type("updateCollege_collegeBean_nomCollegeUI", "college_test2");
		selenium.click("//input[@value='Modifier']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("college_test2"));
                //Suppression du college
		selenium.click("link=supprimer");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Oui");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Deconnexion");
		selenium.waitForPageToLoad("30000");
	}
}
