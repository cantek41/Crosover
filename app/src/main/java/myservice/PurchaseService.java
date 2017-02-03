package myservice;

import android.app.IntentService;
import android.content.Intent;

import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import dal.entities.BidEntity;
import dal.entities.ProductEntity;
import dal.entities.PurchaseEntity;
import dal.UnitOfWork;
import cantekin.crosover.MyPreference;


public class PurchaseService extends IntentService {
  private static final String serviceName = "PurchaseService";
  private UnitOfWork unitOfWork;

  public PurchaseService() {
    super(serviceName);
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    long userId = MyPreference.getCurrentUser(getApplicationContext());
    List<ProductEntity> productEntityList = null;
    BidEntity bidEntity = null;
    try {
      productEntityList = getUnitOfWork().getProductRepo().getQuery().where().lt("endDate", new Date()).and().eq("status", true).query();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    QueryBuilder<BidEntity, Long> qb;
    PurchaseEntity purchaseEntity;
    for (ProductEntity product : productEntityList) {
      try {
        qb = getUnitOfWork().getBidRepo().getQuery();
        qb.where().eq("product_id", product.getId());
        qb.orderBy("offer", false);
        bidEntity = qb.queryForFirst();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      if(bidEntity!=null) {
        purchaseEntity = new PurchaseEntity();
        purchaseEntity.setCost(bidEntity.getOffer());
        purchaseEntity.setUser(bidEntity.getUser());
        purchaseEntity.setDate(product.getEndDate());
        purchaseEntity.setProduct(bidEntity.getProduct());
        getUnitOfWork().getPurchaseRepo().insert(purchaseEntity);
        product.setStatus(false);
        getUnitOfWork().getProductRepo().insert(product);
        getUnitOfWork().getBidRepo().remove(bidEntity);
      }
    }

    callBootUser();
  }

  private void callBootUser() {
    List<ProductEntity> productEntityList = null;

    try {
      productEntityList = getUnitOfWork().getProductRepo().getQuery().where().ge("endDate", new Date()).and().eq("status", true).query();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Random r = new Random();
    int i = r.nextInt(productEntityList.size());
    BidEntity bidEntity = new BidEntity();
    bidEntity.setProduct(productEntityList.get(i));
    bidEntity.setUser(getUnitOfWork().getUserRepo().getFirst());
    bidEntity.setDate(new Date());
    bidEntity.setOffer(productEntityList.get(i).getMinPrice() + 100);
    getUnitOfWork().getBidRepo().insert(bidEntity);
  }

  public UnitOfWork getUnitOfWork() {
    if (this.unitOfWork == null)
      this.unitOfWork = new UnitOfWork(this);
    return this.unitOfWork;
  }
}
