package com.example.android.justjava2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * we will have a global variable 'quantity' which will store the number of coffees
     */
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is used for calculating the total price
     * @param hasWhippedCream checks whether the user has selected Whipped Cream
     * @param hasChocolate checks whether the user has selected chocolate
     * @return int: the total price
     */
    public int calculatePrice(boolean hasWhippedCream, boolean hasChocolate){
        // suppose whipped cream costs an extra of $2 and chocolate costs an extra of $1
        int price = 5; // price of 1 cup of coffee
        if(hasWhippedCream) price += 2;
        if(hasChocolate) price += 1;
        // at this point we have the net price for 1 cup of coffee
        return price*quantity;
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCream = findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolate = findViewById(R.id.chocolate);
        EditText nameField = findViewById(R.id.name);
        Editable name = nameField.getText();
        boolean hasWhippedCream = whippedCream.isChecked();
        boolean hasChocolate = chocolate.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String message = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        /**
         * Some observations -
         *  1) if i use ACTION_SENDTO , then I should not mention the 'intent.setType()' , also on using ACTION_SENDTO, it "AUTOMATICALLY" opens up the vivo email
         *  2) if i use ACTION_SEND , then I must mention the 'intent.setType()', (otherwise won't work), also, it now asks me which app do i want to use to perform the task
         *      like i can choose b/w gmail, and vivo email, but strangely among those apps even Instagram shows up, wtf?
         */
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");
        String subject = getString(R.string.subject, name);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is used to generate order summary. It takes in price as an input parameter and return a string
     * @param price of the order
     * @param hasWhippedCream tells whether user has selected whipped cream or not
     * @param hasChocolate tells whether user has selected chocolate or not
     * @return text summary
     */
    public String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, Editable name){
       return getString(R.string.order_summary_name, name) + "\n" + getString(R.string.quantity) + ": " +  quantity + "\n" +
                getString(R.string.order_summary_whipped_cream, hasWhippedCream)  + "\n" +
                getString(R.string.order_summary_chocolate, hasChocolate) + "\nTotal: $" + price + "\n" + getString(R.string.thank_you);
    }
    /**
     * This method increments the coffeeCount by 1 and displays the value on the screen
     */
    public void increment(View view) {
        if(quantity < 100)
        display(++quantity);
        else
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();

    }

    /**
     * This method decrements the coffeeCount by 1 and displays the value on the screen
     */
    public void decrement(View view) {
        if(quantity > 1)
        display(--quantity);
        else
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }



}