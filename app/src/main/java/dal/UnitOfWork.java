package dal;

import android.content.Context;

import dal.entities.BidEntity;
import dal.entities.ProductEntity;
import dal.entities.PurchaseEntity;
import dal.entities.UserEntity;
import dal.repositories.IRepository;
import dal.repositories.Repository;

/**
 * Created by Cantekin on 2.2.2017.
 */

public class UnitOfWork  {
  private Context context;


  private IRepository<UserEntity> userRepo;
  private IRepository<ProductEntity> productRepo;
  private IRepository<BidEntity> bidRepo;
  private IRepository<PurchaseEntity> purchaseRepo;

  public UnitOfWork(Context context) {
    this.context = context;
  }

  public IRepository<UserEntity> getUserRepo() {
     if (this.userRepo == null)
      this.userRepo= new Repository<UserEntity>(context, UserEntity.class);
    return userRepo;
  }

  public IRepository<BidEntity> getBidRepo() {
    if (this.bidRepo == null)
      this.bidRepo= new Repository<BidEntity>(context, BidEntity.class);
    return bidRepo;
  }

  public IRepository<ProductEntity> getProductRepo() {
    if (this.productRepo == null)
      this.productRepo= new Repository<ProductEntity>(context, ProductEntity.class);
    return productRepo;
  }

  public IRepository<PurchaseEntity> getPurchaseRepo() {
    if (this.purchaseRepo == null)
      this.purchaseRepo= new Repository<PurchaseEntity>(context, PurchaseEntity.class);
    return purchaseRepo;
  }
}
