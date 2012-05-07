package org.globaltester.core.ui;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Test;

public class GtUiTestOpenLog {
public static String gtMainWindowTitle = "GlobalTester";
	
	private static SWTWorkbenchBot bot = new SWTWorkbenchBot();

	/**
	 * Test the presence of the main window and create a screenshot
	 * @throws Exception
	 */
	@Test
	public void testApplicationWindow() throws Exception {
		bot.waitUntil(Conditions.shellIsActive(gtMainWindowTitle));
		bot.captureScreenshot("screenshots"+File.separator+"MainWindow.png");
		assertNotNull(bot.shell(gtMainWindowTitle));
	}
	
	@Test
	public void testAboutBoxAndCloseWelcome() throws Exception {
		bot.waitUntil(Conditions.shellIsActive(gtMainWindowTitle));
		bot.menu("Help").menu("About GlobalTester RCP").click();
		bot.button("OK").click();
		
		SWTBotView activeView = bot.activeView();
		// close the welcome page, if it is shown
		if ("Welcome".equals(activeView.getTitle())) {
			bot.viewByTitle("Welcome").close();
		}
	
	}
	
	@Test
	public void openNewCardConfigWizard() {
		bot.activeView();
		
		String cardConfigName = "Mustermann Erika";
		bot.menu("File").menu("New").click();

		SWTBotShell newDialogBot = bot.shell("New");
		SWTBotTree newDialogTree = newDialogBot.bot().tree();

		newDialogTree.expandNode("GlobalTester", true).select("CardConfiguration");

		bot.button("Next >").click();
		bot.text().setText(cardConfigName);
		
		bot.button("Finish").click();
		
		IProject createdProject = ResourcesPlugin.getWorkspace().getRoot()
		.getProject(cardConfigName);
		
		assertTrue("Project was not created", createdProject.exists());

	}
	
	@Test
	public void addTestSpecification() {

		String ICAOTestSpecification = "GlobalTester Sample ICAO TestSpecification";
		
		bot.menu("File").menu("Import...").click();

		SWTBotShell newDialogBot = bot.shell("Import");
		SWTBotTree newDialogTree = newDialogBot.bot().tree();

		newDialogTree.expandNode("GlobalTester", true).select("Import TestSpecification from Plugin");

		bot.button("Next >").click();
		
		bot.button("Finish").click();
		
		IProject createdProject = ResourcesPlugin.getWorkspace().getRoot()
		.getProject(ICAOTestSpecification);
		
		assertTrue("Project was not created", createdProject.exists());

	}
	
	@Test
	public void runTestcase() {
		
        SWTBotView packageExplorer = getPackageExplorer();
        SWTBotTree tree = packageExplorer.bot().tree();
        packageExplorer.show();
        tree.select("GlobalTester Sample ICAO TestSpecification");
        tree.expandNode("GlobalTester Sample ICAO TestSpecification",true).getNode("TestCases").getNode("Logical Data Structure Tests").getNode("Tests for DG1").select("LDS_B_01.xml");
        
      //PRINT LIST OF ALL VISIBLE VIEWS TO CONSOLE
//        ArrayList<SWTBotView> viewsList;
//        viewsList = new ArrayList<SWTBotView>(bot.views());
//        if (viewsList.isEmpty()){
//        	System.out.println("\n\n>There is no view<\n\n");
//        } else {
//        	System.out.println("There are " + viewsList.size() + " views visible:");
//        }
//        for (int i = 0; i < viewsList.size(); i++) {
//        	System.out.println(viewsList.get(i).getTitle());
//        }


	}
	
//    private boolean isProjectCreated(String name) {
//        try {
//            SWTBotView packageExplorer = getPackageExplorer();
//            SWTBotTree tree = packageExplorer.bot().tree();
//            tree.getTreeItem(name);
//            return true;
//        } catch (WidgetNotFoundException e) {
//            return false;
//        }
//    }
    
    private static SWTBotView getPackageExplorer() {
        SWTBotView view = bot.viewByTitle("GlobalTester Navigator");
        return view;
    }

}