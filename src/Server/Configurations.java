package Server;

import java.util.Properties;

public class Configurations {

    private Properties configFile;
    private static Configurations instance=new Configurations();

    private Configurations() {
        configFile = new Properties();
        try {
            configFile.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
        } catch (Exception eta) {
            eta.printStackTrace();
        }
    }
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
