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

import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import dal.entities.BidEntity;
import cantekin.crosover.MyPreference;
import cantekin.crosover.R;

public class BidListFragment extends MyFragment {
  private static final String TAG = "BidListFragment";
  private BidListFragment.ListAdapter listAdapter;
  private ListView list;
  private List<BidEntity> bidEntityList;
  private Date currentDate = new Date();

  public BidListFragment() {
    LayoutId = R.layout.fragment_bid_list;
  }

  @Override
  protected void initFragment() {
    super.initFragment();
    changeTitle("My Bid");
    list = (ListView) getActivity().findViewById(R.id.bidList);
    getProducts();
    setList();
  }

  private void setList() {
    if (bidEntityList == null)
      return;
    listAdapter = new BidListFragment.ListAdapter
      (getContext(), R.layout.row_bid, bidEntityList);
    list.setAdapter(listAdapter);
  }

  private void getProducts() {
    try {
      bidEntityList = getUnitOfWork().getBidRepo().getQuery().where().eq("user_id", MyPreference.getCurrentUser(getContext())).query();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private class ListAdapter extends ArrayAdapter<BidEntity> {
    public ListAdapter(Context context, int resource, List<BidEntity> objects) {
      super(context, resource, objects);
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
      View v = convertView;
      if (v == null) {
        LayoutInflater vi;
        vi = LayoutInflater.from(getContext());
        v = vi.inflate(R.layout.row_bid, null);
      }
      final BidEntity o = getItem(position);
      if (o != null) {
        o.setProduct(getUnitOfWork().getProductRepo().getById(o.getProduct().getId()));
        ImageView image = (ImageView) v.findViewById(R.id.imageView2);
        TextView name = (TextView) v.findViewById(R.id.name);
        TextView endDate = (TextView) v.findViewById(R.id.endDate);
        TextView price = (TextView) v.findViewById(R.id.price);
        TextView bigPrice = (TextView) v.findViewById(R.id.bigPrice);
        TextView myOffer = (TextView) v.findViewById(R.id.txtoffer);

        name.setText(o.getProduct().getName());
        endDate.setText("End Date:" + o.getProduct().getEndDateFormated());
        price.setText("Starting Price:" + String.valueOf(o.getProduct().getMinPrice()));
        myOffer.setText("My Offer:" + String.valueOf(o.getOffer()));
        try {
          QueryBuilder<BidEntity, Long> qb = getUnitOfWork().getBidRepo().getQuery();
          qb.where().eq("product_id", o.getProduct().getId());
          qb.orderBy("offer", false);
          BidEntity bid = qb.queryForFirst();
          if (bid != null) {
            bigPrice.setText("Active Price:" + String.valueOf(bid.getOffer()));
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }

        if (o.getProduct().getImage() != null)
          image.setImageBitmap(BitmapFactory.decodeByteArray(o.getProduct().getImage(), 0, o.getProduct().getImage().length));


      }
      return v;
    }
  }
}
