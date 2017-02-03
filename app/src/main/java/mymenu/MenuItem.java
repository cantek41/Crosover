package mymenu;

import android.support.v4.app.Fragment;

/**
 * Created by Cantekin on 1.2.2017.
 */
public class MenuItem {
  private int icon;
  private String name;

  public Fragment getPage() {
    return page;
  }

  public void setPage(Fragment page) {
    this.page = page;
  }

  public int getIcon() {
    return icon;
  }

  public void setIcon(int icon) {
    this.icon = icon;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private Fragment page;

}
