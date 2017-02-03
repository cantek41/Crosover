package dal.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Cantekin on 1.2.2017.
 */
@DatabaseTable(tableName = "Purchase")
public class PurchaseEntity extends _BaseEntity {
  @DatabaseField(foreign = true)
  private ProductEntity product;

  @DatabaseField(canBeNull = false)
  private int cost;

  @DatabaseField(dataType = DataType.DATE_STRING,format = "yyyy-MM-dd HH:mm:ss")
  private Date date;


  @DatabaseField(foreign = true)
  private UserEntity user;

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

  public int getCost() {
    return cost;
  }

  public void setCost(int offer) {
    this.cost = offer;
  }

  public ProductEntity getProduct() {
    return product;
  }

  public void setProduct(ProductEntity product) {
    this.product = product;
  }
}
