package org.globaltester.core.ui;

import static org.junit.Assert.assertNotNull;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.junit.Test;

public class GtCoreUiTest {

	private SWTWorkbenchBot bot = new SWTWorkbenchBot();

	@Test
	public void testApplicationWindow() throws Exception {
		Thread.sleep(5000);
		bot.captureScreenshot("MainWindow.png");
		assertNotNull(bot.shell("GlobalTester"));
	}
}