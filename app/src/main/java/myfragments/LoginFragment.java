package myfragments;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.SQLException;

import dal.entities.UserEntity;
import cantekin.crosover.MyPreference;
import cantekin.crosover.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends MyFragment {

  EditText userName;
  EditText password;

  public LoginFragment() {
    LayoutId = R.layout.fragment_login;
  }

  @Override
  protected void initFragment() {
    //super.initFragment();
    changeTitle("Login");
    userClear();
    userName = (EditText) getActivity().findViewById(R.id.edtUserName);
    password = (EditText) getActivity().findViewById(R.id.edtPassword);
    Button btnLogin = (Button) getActivity().findViewById(R.id.btnLogin);
    btnLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        login();
        dataClear();
      }
    });
    Button btnRegister = (Button) getActivity().findViewById(R.id.btnRegister);
    btnRegister.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        register();
        dataClear();
      }
    });
  }

  private void dataClear() {
    userName.setText("");
    password.setText("");
  }

  private void register() {
    if (isEmtyData())
      return;
    UserEntity checkUserName = null;
    try {
      checkUserName = getUnitOfWork().getUserRepo().getQuery().where().eq("name", userName.getText().toString()).queryForFirst();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    if (checkUserName != null) {
      showMessage("UserName is used differnt Customer");
      return;
    }
    UserEntity user = new UserEntity();
    user.setName(userName.getText().toString());
    user.setPassword(UserEntity.generateHash(password.getText().toString()));
    getUnitOfWork().getUserRepo().insert(user);
    MyPreference.setCurrentUser(getContext(), user);
    getActivity().getSupportFragmentManager().popBackStack();
  }



  private void login() {
    if (isEmtyData())
      return;

    UserEntity checkUserName = null;
    try {
      checkUserName = getUnitOfWork().getUserRepo().getQuery().where().eq("name", userName.getText().toString()).and().eq("password", UserEntity.generateHash(password.getText().toString())).queryForFirst();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    if (checkUserName == null) {
      showMessage("UserName or Password are not true");
      return;
    }

    MyPreference.setCurrentUser(getContext(), checkUserName);

    getActivity().getSupportFragmentManager().popBackStack();
  }

  private boolean isEmtyData() {
    if (userName.getText().length() == 0 || password.getText().length() == 0) {
      showMessage("UserName and Password are not null!");
      return true;
    }
    return false;
  }


  private void userClear() {
    MyPreference.setCurrentUser(getContext());
  }


}
