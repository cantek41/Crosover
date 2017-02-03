package dal.entities;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by Cantekin on 1.2.2017.
 */
public abstract class _BaseEntity implements Serializable {
  @DatabaseField(generatedId = true)
  protected long Id;

  public long getId() {
    return Id;
  }

  public void setId(long id) {
    Id = id;
  }
}
