package com.example.myapplication.ui.admin.product;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.RetrofitClient;
import com.example.myapplication.data.requests.BrandRequest;
import com.example.myapplication.data.responses.BrandResponse;
import com.example.myapplication.data.responses.ProductCreateResposne;
import com.example.myapplication.data.responses.ProductResponse;
import com.example.myapplication.data.responses.ResponseMessage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminProductFragment extends Fragment {
    private static List<ProductResponse> products;
    private static AdminProductAdapter productAdapter;

    private ImageView selectedImage;
    private TextView tvName;
    private TextView tvPrice;
    private TextView tvQuantity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_product, container, false);
        RecyclerView rvProduct = view.findViewById(R.id.product_admin_list);
        products = new ArrayList<>();
        productAdapter = new AdminProductAdapter(products);
        rvProduct.setAdapter(productAdapter);
        rvProduct.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getProducts();

        Button btnCreate = view.findViewById(R.id.product_btn_create);
        btnCreate.setOnClickListener(view1 -> {
            LayoutInflater inflater = LayoutInflater.from(view.getContext());
            final View createView = inflater.inflate(R.layout.product_create_dialog, null);

            this.tvName = (TextView) createView.findViewById(R.id.product_dialog_name);
            this.tvPrice = (TextView) createView.findViewById(R.id.product_dialog_price);
            this.tvQuantity = (TextView) createView.findViewById(R.id.product_dialog_quantity);
            this.selectedImage = (ImageView) createView.findViewById(R.id.product_dialog_selectedImage);
//            try {
//                InputStream in = new java.net.URL("https://res.cloudinary.com/docbzd7l8/image/upload/v1654743570/select-512_zul7om.webp").openStream();
//                Bitmap selectedImageBitmap = BitmapFactory.decodeStream(in);
//                Log.d("Sad", selectedImageBitmap.toString());
//                this.selectedImage.setImageBitmap(selectedImageBitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            Button btn_select_image = (Button) createView.findViewById(R.id.product_dialog_btnSelectImage);
            btn_select_image.setOnClickListener(view2 -> {
                imageChooser();
            });

            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setView(createView);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Tạo", (dialog, which) -> {
                try {
                    //get image
                    BitmapDrawable drawable = (BitmapDrawable) this.selectedImage.getDrawable();
                    if(drawable == null)
                        return;

                    Bitmap bitmap = drawable.getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//                    byte[] byteArray = byteArrayOutputStream.toByteArray();
//                    String image_byte=String.valueOf(byteArray);

                    //convert bitmap to file
                    File filesDir = getContext().getFilesDir();
                    File imageFile = new File(filesDir, "image" + ".jpg");
                    OutputStream os;
                    try {
                        os = new FileOutputStream(imageFile);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                        os.flush();
                        os.close();
                    } catch (Exception e) {
                        Log.e("Error", "Error writing bitmap", e);
                    }

                    String name = this.tvName.getText().toString();
                    String price = this.tvPrice.getText().toString();
                    String quantity = this.tvQuantity.getText().toString();

                    createProduct(imageFile, name, price, quantity);
                }
                catch (Exception exception){
                    Log.d("ERROR: " , exception.getMessage());
                    return;
                }
            });

            alertDialog.show();
        });
    }

    private void createProduct(File imageFile, String name, String price, String quantity) {
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", imageFile.getName(), RequestBody.create(MediaType.parse("image/*"), imageFile));
        MultipartBody.Part namePart = MultipartBody.Part.createFormData("name", name);
        MultipartBody.Part pricePart = MultipartBody.Part.createFormData("price", price);
        MultipartBody.Part quantityPart = MultipartBody.Part.createFormData("quantity", quantity);

        Call<ProductCreateResposne> call = RetrofitClient.getProductService().Create(filePart, namePart, pricePart, quantityPart);

        call.enqueue(new Callback<ProductCreateResposne>() {
            @Override
            public void onResponse(Call<ProductCreateResposne> call, Response<ProductCreateResposne> response) {

                if (response.isSuccessful()) {
                    Log.d("onResponse", response.body().getMessage());
                    getProducts();
                }
            }
            @Override
            public void onFailure(Call<ProductCreateResposne> call, Throwable t) {
                Log.d("RequestError", "sadas");
            }
        });
    }

    public static void deleteProduct(String id, int position){
        Call<ResponseMessage> call = RetrofitClient.getProductService().Delete(id);
        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if (response.isSuccessful()) {
                    products.remove(position);
                    productAdapter.notifyDataSetChanged();
                } else {
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
            }
        });
    }

    public void getProducts() {
        Call<List<ProductResponse>> call = RetrofitClient.getProductService().GetAllProducts();

        call.enqueue(new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
                if (response.isSuccessful()) {
                    products.clear();
                    products.addAll(response.body());
                    productAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                Log.d("PRODUCT_ERROR", t.getLocalizedMessage());
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void imageChooser()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null
                            && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap;
                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    getContext().getContentResolver(),
                                    selectedImageUri);

                            this.selectedImage.setImageBitmap(
                                    selectedImageBitmap);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}