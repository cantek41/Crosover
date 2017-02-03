package myfragments;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import dal.entities.BidEntity;
import dal.entities.ProductEntity;
import cantekin.crosover.MyPreference;
import cantekin.crosover.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellingFragment extends MyFragment {

  private static final String TAG = "SellingFragment";
  private ListAdapter listAdapter;
  private ListView list;
  private List<ProductEntity> productEntityList;
  private Date currentDate = new Date();

  public SellingFragment() {
    LayoutId = R.layout.fragment_selling;
  }

  @Override
  protected void initFragment() {
    super.initFragment();
    changeTitle("Selling");
    list = (ListView) getActivity().findViewById(R.id.sellingList);
    getProducts();
    setList();
  }

  private void setList() {
    if (productEntityList == null)
      return;
    listAdapter = new ListAdapter
      (getContext(), R.layout.row_selling, productEntityList);
    list.setAdapter(listAdapter);
  }

  private void getProducts() {
    try {
      productEntityList = getUnitOfWork().getProductRepo().getQuery().where().ge("endDate", currentDate).query();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private class ListAdapter extends ArrayAdapter<ProductEntity> {
    public ListAdapter(Context context, int resource, List<ProductEntity> objects) {
      super(context, resource, objects);
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
      View v = convertView;
      if (v == null) {
        LayoutInflater vi;
        vi = LayoutInflater.from(getContext());
        v = vi.inflate(R.layout.row_selling, null);
      }
      final ProductEntity o = getItem(position);
      if (o != null) {
        BidEntity bid = null;
        ImageView image = (ImageView) v.findViewById(R.id.imageView2);
        TextView name = (TextView) v.findViewById(R.id.name);
        TextView endDate = (TextView) v.findViewById(R.id.endDate);
        TextView price = (TextView) v.findViewById(R.id.price);
        TextView bigPrice = (TextView) v.findViewById(R.id.bigPrice);
        final EditText offer = (EditText) v.findViewById(R.id.offer);
        Button submit = (Button) v.findViewById(R.id.submit);
        name.setText(o.getName());
        endDate.setText("End Date:" + o.getEndDateFormated());
        price.setText("Starting Price:" + String.valueOf(o.getMinPrice()));
        if (currentDate.getTime() > o.getEndDate().getTime()) {
          submit.setEnabled(false);
          offer.setEnabled(false);
        }
        try {
          QueryBuilder<BidEntity, Long> qb = getUnitOfWork().getBidRepo().getQuery();
          qb.where().eq("product_id", o.getId());
          qb.orderBy("offer", false);
          bid = qb.queryForFirst();
          if (bid != null) {
            bigPrice.setText("Active Price:" + String.valueOf(bid.getOffer()));
          } else {
            bid = new BidEntity();
            bid.setOffer(o.getMinPrice());
            bigPrice.setText("Active Price:" + String.valueOf(bid.getOffer()));

          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
        if (o.getImage() != null)
          image.setImageBitmap(BitmapFactory.decodeByteArray(o.getImage(), 0, o.getImage().length));

        final BidEntity finalBid = bid;
        submit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            if (offer.getText() == null)
              return;
            if (Integer.valueOf(offer.getText().toString()) <= o.getMinPrice()) {
              showMessage("The bid must be bigger than the starting price !");
              return;
            } else if (Integer.valueOf(offer.getText().toString()) <= finalBid.getOffer()) {
              showMessage("The bid must be bigger than the Active price !");
              return;
            }
            BidEntity bid = null;
            try {
              bid = getUnitOfWork().getBidRepo().getQuery().where().eq("user_id", MyPreference.getCurrentUser(getContext())).and().eq("product_id", o.getId()).queryForFirst();
            } catch (SQLException e) {
              e.printStackTrace();
            }
            if (bid == null) {
              Log.d(TAG, "new bid");
              bid = new BidEntity();
              bid.setProduct(o);
              bid.setUser(getUnitOfWork().getUserRepo().getById(MyPreference.getCurrentUser(getContext())));
            }
            bid.setDate(new Date());
            bid.setOffer(Integer.valueOf(offer.getText().toString()));
            getUnitOfWork().getBidRepo().insert(bid);
            showMessage("Offer added!");
          }
        });
      }
      return v;
    }
  }
}
