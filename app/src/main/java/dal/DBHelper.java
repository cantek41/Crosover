package dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dal.entities.BidEntity;
import dal.entities.ProductEntity;
import dal.entities.PurchaseEntity;
import dal.entities.UserEntity;
import cantekin.crosover.R;


/**
 * Created by Cantekin on 1.2.2017.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {
  private static final String DATABASE_NAME = "crosover.db";
  private static final int DATABASE_VERSION = 1;
  private static final String TAG = "DBHelper";
  private  Context context;

  public DBHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    this.context=context;
  }


  @Override
  public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
    for (Class c : getTableClass()) {
      try {
        TableUtils.createTable(connectionSource, c);
      } catch (SQLException e) {
        Log.e(TAG, e.getMessage());
      }
    }
    DbSeed();
  }

  private void DbSeed() {
    UserEntity botUser = new UserEntity("can", UserEntity.generateHash("1234"));
    try {
      getDao(UserEntity.class).create(botUser);
      List<ProductEntity> products = new ArrayList<ProductEntity>();
      byte[] image = convertStreamToByteArray(context.getResources().openRawResource(R.raw.image));
      SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
      String dateInString = "22-02-2017 10:20:56";
      Date date = sdf.parse(dateInString);
      products.add(new ProductEntity("item1", date, 150, true,image ));
      products.add(new ProductEntity("item2", date, 100, true, image));
      products.add(new ProductEntity("item3", date, 50, true, image));
      for (ProductEntity item : products) {
        getDao(ProductEntity.class).create(item);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
  public static byte[] convertStreamToByteArray(InputStream drawable) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    byte[] buff = new byte[10240];
    int i = Integer.MAX_VALUE;
    while ((i = drawable.read(buff, 0, buff.length)) > 0) {
      baos.write(buff, 0, i);
    }
    return baos.toByteArray();
  }

  @Override
  public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
    Log.i(TAG, "onUpgrade");
    for (Class c : getTableClass()) {
      try {
        TableUtils.dropTable(connectionSource, c, true);
      } catch (SQLException e) {
        Log.e(TAG, e.getMessage());
      }
    }
    onCreate(database, connectionSource);
  }

  protected Class<?>[] getTableClass() {
    return new Class<?>[]{UserEntity.class, ProductEntity.class, BidEntity.class, PurchaseEntity.class};
  }

  /**
   * generic dao
   *
   * @param tClass
   * @param <D>
   * @param <T>
   * @return
   * @throws SQLException
   */
  public <D, T> D genericDao(Class<T> tClass) throws SQLException {
    return (D) getDao(tClass);
  }

}
