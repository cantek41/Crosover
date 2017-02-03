package myfragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

import dal.entities.PurchaseEntity;
import cantekin.crosover.MyPreference;
import cantekin.crosover.R;

public class PurchaseFragment extends MyFragment {
  private static final String TAG = "PurchaseFragment";
  private PurchaseFragment.ListAdapter listAdapter;
  private ListView list;
  private List<PurchaseEntity> purchaseEntityList;

  public PurchaseFragment() {
    LayoutId = R.layout.fragment_purchase;
  }

  @Override
  protected void initFragment() {
    super.initFragment();
    changeTitle("Purchase");
    list = (ListView) getActivity().findViewById(R.id.purchaseList);
    getProducts();
    setList();
  }

  private void setList() {
    if (purchaseEntityList == null)
      return;
    listAdapter = new PurchaseFragment.ListAdapter
      (getContext(), R.layout.row_purchase, purchaseEntityList);
    list.setAdapter(listAdapter);
  }

  private void getProducts() {
    try {
      purchaseEntityList = getUnitOfWork().getPurchaseRepo().getQuery().where().eq("user_id", MyPreference.getCurrentUser(getContext())).query();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private class ListAdapter extends ArrayAdapter<PurchaseEntity> {
    public ListAdapter(Context context, int resource, List<PurchaseEntity> objects) {
      super(context, resource, objects);
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
      View v = convertView;
      if (v == null) {
        LayoutInflater vi;
        vi = LayoutInflater.from(getContext());
        v = vi.inflate(R.layout.row_purchase, null);
      }
      final PurchaseEntity o = getItem(position);
      if (o != null) {
        o.setProduct(getUnitOfWork().getProductRepo().getById(o.getProduct().getId()));
        ImageView image = (ImageView) v.findViewById(R.id.imageView2);
        TextView name = (TextView) v.findViewById(R.id.name);
        TextView price = (TextView) v.findViewById(R.id.price);
        TextView myOffer = (TextView) v.findViewById(R.id.txtoffer);
        name.setText(o.getProduct().getName());
        price.setText("Starting Price:" + String.valueOf(o.getProduct().getMinPrice()));
        myOffer.setText("Cost:" + String.valueOf(o.getCost()));
        if (o.getProduct().getImage() != null)
          image.setImageBitmap(BitmapFactory.decodeByteArray(o.getProduct().getImage(), 0, o.getProduct().getImage().length));
      }
      return v;
    }
  }
}
