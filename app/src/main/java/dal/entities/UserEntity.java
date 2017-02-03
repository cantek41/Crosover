package dal.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Cantekin on 1.2.2017.
 */
@DatabaseTable(tableName = "User")
public class UserEntity extends _BaseEntity {
  @DatabaseField(canBeNull = false)
  private String name;
  @DatabaseField(canBeNull = false)
  private String password;

  public UserEntity() {
  }

  public UserEntity(String name, String password) {
    setName(name);
    setPassword(password);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public static String generateHash(String data) {
    byte[] originalBytes = data.getBytes();
    MessageDigest md5 = null;
    try {
      md5 = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    byte[] encodedBytes = md5.digest(originalBytes);
    String hash = new BigInteger(1, encodedBytes).toString(16);
    return hash.toLowerCase();
  }
}
