package cantekin.crosover;

import android.support.test.InstrumentationRegistry;
import android.test.InstrumentationTestRunner;
import android.util.Log;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dal.entities.BidEntity;
import dal.entities.ProductEntity;
import dal.entities.PurchaseEntity;
import dal.entities.UserEntity;
import dal.UnitOfWork;

/**
 * Created by Cantekin on 2.2.2017.
 */
public class RepositoryTest extends InstrumentationTestRunner {
  UnitOfWork work;
  private String TAG = "RepositoryTest";

  @Before
  public void setUp() throws Exception {
    Log.d(TAG, "setUp");
    work = new UnitOfWork(InstrumentationRegistry.getInstrumentation().getTargetContext());
  }

  @After
  public void tearDown() throws Exception {

  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void insertPurchase() throws Exception {
    ProductEntity productEntity = work.getProductRepo().getFirst();
    PurchaseEntity purchaseEntity = new PurchaseEntity();
    purchaseEntity.setCost(150);
    purchaseEntity.setUser(work.getUserRepo().getFirst());
    purchaseEntity.setDate(productEntity.getEndDate());
    purchaseEntity.setProduct(productEntity);
    work.getPurchaseRepo().insert(purchaseEntity);
    PurchaseEntity p = work.getPurchaseRepo().getById(purchaseEntity.getId());
    Assert.assertNotNull(p);
  }

  @Test
  public void insertProduct() throws Exception {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    String dateInString = "22-01-2017 10:20:56";
    Date date = sdf.parse(dateInString);
    ProductEntity productEntity = new ProductEntity("testItem", date, 99, true, null);
    work.getProductRepo().insert(productEntity);
    ProductEntity check = work.getProductRepo().getById(productEntity.getId());
    Assert.assertNotNull(check);
  }

  @Test
  public void insertBid() throws Exception {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    String dateInString = "22-01-2017 10:20:56";
    Date date = sdf.parse(dateInString);
    ProductEntity productEntity = new ProductEntity("testItemBid", date, 199, true, null);
    work.getProductRepo().insert(productEntity);
    BidEntity bidEntity =new BidEntity();
    bidEntity.setProduct(productEntity);
    bidEntity.setUser(work.getUserRepo().getFirst());
    bidEntity.setDate(productEntity.getEndDate());
    bidEntity.setOffer(201);
    work.getBidRepo().insert(bidEntity);
    BidEntity p = work.getBidRepo().getById(bidEntity.getId());
    Assert.assertNotNull(p);
  }

  @Test
  public void insert() throws Exception {
    UserEntity user = new UserEntity("deneme", "sdsd");
    work.getUserRepo().insert(user);
    UserEntity userCheck = work.getUserRepo().getById(user.getId());
    Assert.assertNotNull(userCheck);
  }

  @Test
  public void insertNull() throws Exception {
    UserEntity user = new UserEntity();
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("tEntity is not null");
    work.getUserRepo().insert(null);
  }

  @Test
  public void insertRequertField() throws Exception {
    UserEntity user = new UserEntity();
    user.setName("sadas");
//    thrown.expect(NullPointerException.class);
//    thrown.expectMessage("tEntity is not null");
    work.getUserRepo().insert(user);
  }

  @Test
  public void insertBatch() throws Exception {
    List<UserEntity> users = new ArrayList<UserEntity>();
    users.add(new UserEntity("deneme1", "sdsd"));
    users.add(new UserEntity("deneme2", "sdsd"));
    work.getUserRepo().insertBatch(users);
    UserEntity userCheck = work.getUserRepo().getById(users.get(0).getId());
    Log.d(TAG, userCheck.getName());
    Assert.assertEquals(userCheck.getId(), users.get(0).getId());
  }

  @Test
  public void remove() throws Exception {
    UserEntity user = new UserEntity("deneme", "sdsd");
    work.getUserRepo().remove(user);
    UserEntity userCheck = work.getUserRepo().getById(user.getId());
    Assert.assertNull(userCheck);
  }

  @Test
  public void getAll() throws Exception {
    List<UserEntity> users = work.getUserRepo().getAll();
    Assert.assertNotNull(users);
  }

  @Test
  public void getFirst() throws Exception {
    UserEntity user = work.getUserRepo().getFirst();
    Assert.assertNotNull(user);
  }

  @Test
  public void getFirstProduct() throws Exception {
    ProductEntity user = work.getProductRepo().getFirst();
    Assert.assertNotNull(user);
  }

  @Test
  public void getQuery() throws Exception {
    UserEntity user = work.getUserRepo().getQuery().queryForFirst();
    Assert.assertNotNull(user);
  }

}
