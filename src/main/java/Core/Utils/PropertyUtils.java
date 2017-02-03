package Core.Utils;

import org.testng.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by anilv on 2/3/17.
 */
public class PropertyUtils {
    private Properties props = new Properties();

    public PropertyUtils(String path) {
        loadPropertyFile(path);
    }

    public void loadPropertyFile(String propertyFileName) {
        try {
            props.load(new FileInputStream(propertyFileName));
        } catch (IOException e) {
            Assert.fail("Unable to load file!", e);
        }
    }

    public String getProperty(String propertyKey) {
        String propertyValue = props.getProperty(propertyKey.trim());

        if (propertyValue == null || propertyValue.trim().length() == 0) {
            System.out.println("Unable to read property !!");
        }

        return propertyValue;
    }

    public void setProperty(String propertyKey, String value) {
        props.setProperty(propertyKey, value);
    }
}
