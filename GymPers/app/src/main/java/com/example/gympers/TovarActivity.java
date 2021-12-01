package com.example.gympers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gympers.models.BrandsCategory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class TovarActivity extends AppCompatActivity {

    TextView quantity;
    int totalQuantity = 1;
    int totalPrice = 0;
    ImageView detailedImg;
    TextView name, description, price;
    Button addToCart;
    ImageView addItem, removeItem;
    BrandsCategory brandsCategory = null;



    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tovar);

        final Object object = getIntent().getSerializableExtra("detailed");
        if (object instanceof BrandsCategory){
            brandsCategory = (BrandsCategory) object;
        }

        quantity = findViewById(R.id.quantity);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        detailedImg = findViewById(R.id.detailed_img);
        name = findViewById(R.id.detailed_name);
        description = findViewById(R.id.detailed_description);
        addToCart = findViewById(R.id.add_to_cart);
        addItem = findViewById(R.id.plus_item);
        removeItem = findViewById(R.id.remove_item);
        price = findViewById(R.id.price);


        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedToCart();
            }
        });

        if (brandsCategory != null){
            Glide.with(getApplicationContext()).load(brandsCategory.getImg_url()).into(detailedImg);
            description.setText(brandsCategory.getDescription());
            price.setText(brandsCategory.getPrice());
            name.setText(brandsCategory.getName());



        }

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuantity < 10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuantity > 1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });

    }

    private void addedToCart() {
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();

        cartMap.put("productName", brandsCategory.getName());
        cartMap.put("productPrice", brandsCategory.getPrice());
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("totalQuantity", quantity.getText().toString());

        firestore.collection("AddtoCart").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(TovarActivity.this, "Добавленно в корзину", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

    }
}