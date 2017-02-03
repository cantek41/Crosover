package dal;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import dal.entities.BidEntity;
import dal.entities.ProductEntity;
import dal.entities.PurchaseEntity;
import dal.entities.UserEntity;

/**
 * Created by Cantekin on 1.2.2017.
 * The java Util program used to create database configuration script
 */

public class OrmliteDBConfigUtil extends OrmLiteConfigUtil {
  private static final Class<?>[] classes = new Class[] {UserEntity.class, ProductEntity.class, BidEntity.class, PurchaseEntity.class};
  public static void main(String[] args) throws IOException, SQLException, IOException, SQLException {

    String currDirectory = "user.dir";

    String configPath = "/src/main/res/raw/ormlite_config.txt";

    /**
     * Gets the project root directory
     */
    String projectRoot = System.getProperty(currDirectory);

    /**
     * Full configuration path includes the project root path, and the location
     * of the ormlite_config.txt file appended to it
     */
    String fullConfigPath = projectRoot + configPath;

    File configFile = new File(fullConfigPath);

    /**
     * In the a scenario where we run this program serveral times, it will recreate the
     * configuration file each time with the updated configurations.
     */
    if(configFile.exists()) {
      configFile.delete();
      configFile = new File(fullConfigPath);
    }

    /**
     * writeConfigFile is a util method used to write the necessary configurations
     * to the ormlite_config.txt file.
     */
    writeConfigFile(configFile, classes);
  }
}
