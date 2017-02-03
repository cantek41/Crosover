package myfragments;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import dal.entities.ProductEntity;
import cantekin.crosover.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends MyFragment {
  private static final int REQUEST_CAMERA = 101;
  ProductEntity productEntity = new ProductEntity();
  Button btnSubmit;
  ImageButton btnImage;
  DatePicker dtEndDate;
  public ProductFragment() {
    LayoutId = R.layout.fragment_product;
  }

  @Override
  protected void initFragment() {
    super.initFragment();
    changeTitle("Add Product");
    dtEndDate = (DatePicker) getActivity().findViewById(R.id.dtEndDate);
    setCurrentDate();
    btnSubmit = (Button) getActivity().findViewById(R.id.submitProduct);
    btnSubmit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        addProduct();
      }
    });
    btnImage = (ImageButton) getActivity().findViewById(R.id.imageButton);
    btnImage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        takeImage();
      }
    });
  }

  public void setCurrentDate() {
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    dtEndDate.init(year, month, day, null);
  }


  private void takeImage() {
    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
      this.requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
      showMessage("takeImage");
      return;
    }
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    startActivityForResult(intent, REQUEST_CAMERA);
  }

  private void addProduct() {
    EditText edtName = (EditText) getActivity().findViewById(R.id.edtProductName);
    EditText edtStartPrice = (EditText) getActivity().findViewById(R.id.edtStartPrice);

    if (edtName.getText().length() == 0 || edtStartPrice.getText().length() == 0 || productEntity.getImage() == null) {
      showMessage("name,picture and price not null");
      return;
    }
    productEntity.setName(edtName.getText().toString());
    productEntity.setMinPrice(Integer.parseInt(edtStartPrice.getText().toString()));
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.YEAR, dtEndDate.getYear());
    cal.set(Calendar.MONTH, dtEndDate.getMonth());
    cal.set(Calendar.DAY_OF_MONTH,dtEndDate.getDayOfMonth());
    productEntity.setEndDate(cal.getTime());
    getUnitOfWork().getProductRepo().insert(productEntity);
    showMessage("Success");
    getActivity().getSupportFragmentManager().popBackStack();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CAMERA) {
      if (resultCode == Activity.RESULT_OK) {
        Bitmap bmp = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        productEntity.setImage(byteArray);
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
          byteArray.length);
        btnImage.setImageBitmap(bitmap);
      }
    }
  }
}
