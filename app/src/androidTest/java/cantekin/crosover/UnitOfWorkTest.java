package cantekin.crosover;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.InstrumentationTestRunner;

import org.junit.Assert;
import org.junit.Test;

import dal.entities.UserEntity;
import dal.repositories.IRepository;
import dal.UnitOfWork;


/**
 * Created by Cantekin on 2.2.2017.
 */
public class UnitOfWorkTest extends InstrumentationTestRunner {

  @Test
  public void getUserRepo() throws Exception {
    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    IRepository<UserEntity> user = new UnitOfWork(context).getUserRepo();
    Assert.assertNotNull(user);
  }

}
