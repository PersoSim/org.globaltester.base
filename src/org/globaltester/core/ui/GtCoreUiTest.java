package org.globaltester.core.ui;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.globaltester.swtbot.Strings;
import org.globaltester.swtbot.uihelper.AboutDialogUiHelper;
import org.globaltester.swtbot.uihelper.GlobalTesterUiHelper;
import org.junit.Before;
import org.junit.Test;

public class GtCoreUiTest {

	@Before
	public void prepare() throws CoreException{
		GlobalTesterUiHelper.init();
	}
	
	/**
	 * Test the presence of the main window and create a screenshot
	 * @throws Exception
	 */
	@Test
	public void testApplicationWindow() throws Exception {
		GlobalTesterUiHelper.captureScreenshot(Strings.FILE_SCREENSHOTS_SUBFOLDER + File.separator + "MainWindow.png");
		assertNotNull(GlobalTesterUiHelper.getBot().shell(Strings.WORKBENCH_TITLE));
	}
	
	@Test
	public void testAboutDialog() throws Exception {
		AboutDialogUiHelper dialog = GlobalTesterUiHelper.openAboutDialog();

		SWTBotShell aboutDlgShell = dialog.getBot().shell(Strings.DIALOG_TITLE_ABOUT);
		assertNotNull(aboutDlgShell);
		dialog.captureScreenshot(Strings.FILE_SCREENSHOTS_SUBFOLDER+File.separator+"AboutDialog.png");
		dialog.close();
	}
}