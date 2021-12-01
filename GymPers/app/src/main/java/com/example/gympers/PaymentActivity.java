package com.example.gympers;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;

public class PaymentActivity extends AppCompatActivity {

    CardForm cardForm;
    Button buy;
    AlertDialog.Builder alertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        cardForm = findViewById(R.id.card_form);
        buy = findViewById(R.id.buy);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("СМС будет отправленно на ваш номер")
                .setup(PaymentActivity.this);
        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardForm.isValid()){
                    alertBuilder = new AlertDialog.Builder(PaymentActivity.this);
                    alertBuilder.setTitle("Confirm before purchase");
                    alertBuilder.setMessage("Card number " + cardForm.getCardNumber() + "\n" +
                            "Phone number " + cardForm.getMobileNumber() + "\n" +
                            "Postal code " + cardForm.getPostalCode() + "\n" +
                            "Card CVV " + cardForm.getCvv());

                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Toast.makeText(PaymentActivity.this, "Спасибо за покупку", Toast.LENGTH_SHORT).show();
                        }
                    });

                    alertBuilder.setNegativeButton("Failed", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();
                } else {
                    Toast.makeText(PaymentActivity.this, "Please Complete the form", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}