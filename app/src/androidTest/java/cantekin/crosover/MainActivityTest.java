package cantekin.crosover;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dal.UnitOfWork;


/**
 * Created by Cantekin on 2.2.2017.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
  @Rule
  public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
    MainActivity.class);

  private MainActivity mainActivity;



  @Before
  public void setActivity() {
    mainActivity = mActivityRule.getActivity();
  }

  @Test
  public void testMainActivity() {
    Assert.assertNotNull(mainActivity);
  }

  @Test
  public void getUnitOfWork() throws Exception {
    UnitOfWork u = mainActivity.getUnitOfWork();
    Assert.assertNotNull(u);
  }

}
