package utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

public class TestingEnvironment {
    private TestingEnvironment() {
    }

   public static ISettingsFile getCurrentEnvironment(String pathFile) {
        return new JsonSettingsFile(pathFile);
    }
}
