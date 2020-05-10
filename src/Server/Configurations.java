package Server;

import java.util.Properties;

public class Configurations {

    private Properties configFile;

    private Configurations() {
        configFile = new Properties();
        try {
            configFile.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
        } catch (Exception eta) {
            eta.printStackTrace();
        }
    }

    private static Configurations instance=new Configurations();

    public static Configurations getInstance() {
        return instance;
    }

    private String getValue(String key) {
        return configFile.getProperty(key);
    }

    public static String getProperty(String key) {
        return instance.getValue(key);
    }
}
