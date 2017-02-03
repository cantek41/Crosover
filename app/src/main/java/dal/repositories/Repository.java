package dal.repositories;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import dal.DBHelper;

/**
 * Created by Cantekin on 2.2.2017.
 */

public class Repository<TEntity> implements IRepository<TEntity> {
  private Context context;
  private DBHelper databaseHelper;
  private Dao<TEntity, Long> dao;
  private Class genericType;

  public Repository(Context context, Class<TEntity> classType) {
    this.context = context;
    this.genericType = classType;
    setHelper();
    setDao();
  }

  private void setDao() {
    try {
      dao = databaseHelper.getDao(genericType);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void setHelper() {
    if (databaseHelper == null) {
      databaseHelper = OpenHelperManager.getHelper(context, DBHelper.class);
    }
  }

  @Override
  public void insert(TEntity tEntity) {
    if (tEntity == null)
      throw new NullPointerException("tEntity is not null");
    try {
      dao.createOrUpdate(tEntity);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }



  @Override
  public void insertBatch(final List<TEntity> entity) {
    try {
      dao.callBatchTasks(new Callable<Void>() {
        @Override
        public Void call() throws Exception {
          for (TEntity e : entity) {
            dao.createOrUpdate(e);
          }
          return null;
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void remove(TEntity tEntity) {
    try {
      dao.delete(tEntity);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<TEntity> getAll() {
    try {
      return dao.queryForAll();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public TEntity getById(long id) {
    try {
      return dao.queryForId(id);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public TEntity getFirst() {
    try {
      return dao.queryBuilder().queryForFirst();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public QueryBuilder<TEntity, Long> getQuery() {
    return dao.queryBuilder();
  }


}
