package testsuites;

import common.Wait;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import pages.EditorPage;
import pages.LoginPage;
import pages.ThemePage;
import pages.UserHomePage;
import seleniumq.automation.core.runtime.TestNGBase;
import seleniumq.automation.core.test.helper.data_provider.CSVDataProvider;
import java.io.IOException;
import java.util.HashMap;

public class VerifyYolaWebsiteCreation extends TestNGBase {
    public VerifyYolaWebsiteCreation() throws IOException {
        super();
    }

    @Test(dataProvider = "yolaData", dataProviderClass = CSVDataProvider.class)
    public final void tc_verifyEmbeddingYoutubeVideo(String email, String password, String browseBy, String themeName, String websiteName, String contentType, String videoURL) throws InterruptedException {
	    /*
	    Pages
	     */
        LoginPage loginPage = new LoginPage(getThreadLocalDriver().get());
        UserHomePage userHomePage = new UserHomePage(getThreadLocalDriver().get());
        ThemePage themePage = new ThemePage(getThreadLocalDriver().get());
        EditorPage editorPage = new EditorPage(getThreadLocalDriver().get());

        loginPage.login(email, password);
        userHomePage.deleteExistingProjects();
        userHomePage.addNewSite();
        themePage.selectTheme(browseBy, themeName, websiteName);
        editorPage.addContentToPage(contentType, 1, new HashMap<String, String>() {{
            put("url", videoURL);
        }});

        // validate the website is saved
        editorPage.switchToEditorFrame();
        Wait.waitUntilVisibilityOfLocator(getThreadLocalDriver().get(), By.xpath("//*[@class='ws-navbar__right']//button//*[text()='Saved']"), 120);

        // validate youtube video is present in the website
        editorPage.switchToYoutubeVideoFrame();
        getThreadLocalDriver().get().findElement(By.xpath("//a[.='Arch Linux installation guide 2020']"));
    }
}
