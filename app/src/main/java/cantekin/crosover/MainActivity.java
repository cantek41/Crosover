package cantekin.crosover;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import dal.UnitOfWork;
import myfragments.SellingFragment;
import mymenu.MenuBuilder;
import myservice.PurchaseService;

public class MainActivity extends AppCompatActivity {
  public static final int REQUEST_CAMERA = 101;
  public FragmentTransaction fragmentTransaction;
  private final String TAG = "BaseActivity";
  private ProgressDialog mProgressDialog;
  private UnitOfWork unitOfWork;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Intent intent = new Intent(MainActivity.this, PurchaseService.class);
    startService(intent);
    init();

  }

  public void init() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
      this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();
    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    View header=navigationView.getHeaderView(0);
    TextView nvgText=(TextView) header.findViewById(R.id.navigationUserName);
    nvgText.setText(MyPreference.getCurrentUserName(getApplicationContext()));
    Menu m = navigationView.getMenu();
    MenuBuilder.build(this, m);
    fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.add(R.id.content, new SellingFragment());
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  public void menuItemClick(Fragment fragment) {
    fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.replace(R.id.content, fragment);
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();
    ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
  }

  public UnitOfWork getUnitOfWork() {
    if (this.unitOfWork == null)
      this.unitOfWork = new UnitOfWork(this);
    return this.unitOfWork;
  }

  public void showMessage(String msg) {
    Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
  }

  /**
   * All Activity
   * Message Progressbar Open
   *
   * @param msg
   */
  public void showProgress(String msg) {
    if (mProgressDialog != null && mProgressDialog.isShowing())
      dismissProgress();
    mProgressDialog = ProgressDialog.show(this, getResources().getString(
      R.string.app_name), msg);
  }

  /**
   * All Activity
   * ProgressBar Close
   */
  public void dismissProgress() {
    if (mProgressDialog != null) {
      mProgressDialog.dismiss();
      mProgressDialog = null;
    }
  }

}
