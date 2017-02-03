package dal.repositories;

import com.j256.ormlite.stmt.QueryBuilder;

import java.util.List;

/**
 * Created by Cantekin on 2.2.2017.
 */

public interface IRepository<TEntity> {
  void insert(TEntity entity);
  void insertBatch(final List<TEntity> entity);
  void remove(TEntity entity);
  List<TEntity> getAll();
  TEntity getById(long Id);
  TEntity getFirst();
  QueryBuilder<TEntity,Long> getQuery();
}
