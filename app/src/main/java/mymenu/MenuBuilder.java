package mymenu;

import android.content.Context;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

import cantekin.crosover.MainActivity;
import myfragments.BidListFragment;
import myfragments.LoginFragment;
import myfragments.ProductFragment;
import myfragments.PurchaseFragment;
import myfragments.SellingFragment;


/**
 * Created by Cantekin on 30.1.2017.
 */
public class MenuBuilder {

  public static void build(final Context context, Menu menu) {
    for (final MenuItem item : getMenuModel()) {
      menu.add(item.getName()).setIcon(context.getResources().getDrawable(item.getIcon()))
        .setOnMenuItemClickListener(new android.view.MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(android.view.MenuItem menuItem) {
          ((MainActivity)context).menuItemClick(item.getPage());
          return true;
        }
      });
    }
  }
  private static List<MenuItem> getMenuModel() {
    List<MenuItem> items = new ArrayList<MenuItem>();
    MenuItem item = new MenuItem();
    item.setName("Selling");
    item.setIcon(android.R.drawable.ic_media_play);
    item.setPage(new SellingFragment());
    items.add(item);
    item = new MenuItem();
    item.setName("AddProduct");
    item.setIcon(android.R.drawable.ic_menu_add);
    item.setPage(new ProductFragment());
    items.add(item);
    item = new MenuItem();
    item.setName("Bid List");
    item.setIcon(android.R.drawable.ic_menu_more);
    item.setPage(new BidListFragment());
    items.add(item);
    item = new MenuItem();
    item.setName("Purchase");
    item.setIcon(android.R.drawable.ic_menu_gallery);
    item.setPage(new PurchaseFragment());
    items.add(item);
    item = new MenuItem();
    item.setName("Exit");
    item.setIcon(android.R.drawable.ic_media_ff);
    item.setPage(new LoginFragment());
    items.add(item);
    return items;
  }
}
