package dal.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Cantekin on 1.2.2017.
 */
@DatabaseTable(tableName = "Bid")
public class BidEntity extends _BaseEntity {
  @DatabaseField(foreign = true)
  private ProductEntity product;

  @DatabaseField(foreign = true)
  private UserEntity user;

  @DatabaseField(canBeNull = false)
  private int offer;

  @DatabaseField(dataType = DataType.DATE_STRING,format = "yyyy-MM-dd HH:mm:ss")
  private Date date;

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public int getOffer() {
    return offer;
  }

  public void setOffer(int offer) {
    this.offer = offer;
  }

  public ProductEntity getProduct() {
    return product;
  }

  public void setProduct(ProductEntity product) {
    this.product = product;
  }
}
