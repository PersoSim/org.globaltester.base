package org.globaltester.core.ui;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.junit.Test;

public class GtCoreUiTest {
public static String gtMainWindowTitle = "GlobalTester";
	
	private SWTWorkbenchBot bot = new SWTWorkbenchBot();

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
	public void testAboutDialog() throws Exception {
		bot.waitUntil(Conditions.shellIsActive(gtMainWindowTitle));
		bot.menu("Help").menu("About ").click();
		
		String aboutShellTitle = "About ";
		bot.waitUntil(Conditions.shellIsActive(aboutShellTitle));
		
		SWTBotShell aboutDlgShell = bot.shell(aboutShellTitle);
		assertNotNull(aboutDlgShell);
		bot.waitUntil(Conditions.shellIsActive(aboutShellTitle),5000,500);
		bot.sleep(500);
		bot.captureScreenshot("screenshots"+File.separator+"AboutDialog.png");
		bot.sleep(500);
		
		
		SWTBotButton okBtnBot = bot.button("OK");
		assertNotNull(okBtnBot);
		okBtnBot.click();
		bot.waitUntil(Conditions.shellCloses(aboutDlgShell));
	}
}