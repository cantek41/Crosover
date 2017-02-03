package myfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dal.UnitOfWork;
import cantekin.crosover.MainActivity;
import cantekin.crosover.MyPreference;
import cantekin.crosover.R;

/**
 * Created by Cantekin on 16.1.2017.
 */
public abstract class MyFragment extends Fragment {
  public View view;
  protected int LayoutId;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(LayoutId, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    this.view = view;
    initFragment();
  }

  protected void initFragment() {
    if (MyPreference.getCurrentUser(getContext()) == 0) {
      ((MainActivity) getActivity()).menuItemClick(new LoginFragment());
      return;
    }
    NavigationView navigationView = (NavigationView)getActivity().findViewById(R.id.nav_view);
    View header=navigationView.getHeaderView(0);
    TextView nvgText=(TextView) header.findViewById(R.id.navigationUserName);
    nvgText.setText(MyPreference.getCurrentUserName(getContext()));
  }

  public UnitOfWork getUnitOfWork() {
    return ((MainActivity) getActivity()).getUnitOfWork();
  }

  public void showMessage(String msg) {
    ((MainActivity) getActivity()).showMessage(msg);
  }

  public void changeTitle(String msg) {
    ((MainActivity) getActivity()).setTitle(msg);
  }


}
