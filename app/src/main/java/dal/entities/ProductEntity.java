package dal.entities;

import android.util.Log;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Cantekin on 1.2.2017.
 */
@DatabaseTable(tableName = "Product")
public class ProductEntity extends _BaseEntity {
  public ProductEntity() {
  }

  /**
   * @param name
   * @param endDate
   * @param minPrice
   * @param status
   * @param image
   */
  public ProductEntity(String name, Date endDate, int minPrice, boolean status, byte[] image) {
    setName(name);
    setEndDate(endDate);
    setMinPrice(minPrice);
    setImage(image);
    setStatus(status);
  }

  @DatabaseField(canBeNull = false)
  private String name;
  @DatabaseField(canBeNull = false)
  private int minPrice;

  @DatabaseField(dataType = DataType.BYTE_ARRAY)
  private byte[] image;

  @DatabaseField(dataType = DataType.DATE_STRING,format = "yyyy-MM-dd HH:mm:ss")
  private Date endDate;

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  @DatabaseField
  private boolean status;

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    if (image != null)
      this.image = image;
  }

  public int getMinPrice() {
    return minPrice;
  }

  public void setMinPrice(int minPrice) {
    this.minPrice = minPrice;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getEndDate() {
    return endDate;
  }
  public String getEndDateFormated() {
    SimpleDateFormat dateFormat = new SimpleDateFormat(
      "yyyy-MM-dd", Locale.getDefault());
    Log.i("",dateFormat.format(endDate));
    return dateFormat.format(endDate);
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
}
