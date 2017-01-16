package project.com.qrcodeapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PaymentConfirm extends AppCompatActivity
{
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payement);
        TextView paymentText = (TextView) findViewById(R.id.paymentText);
        password = (EditText) findViewById(R.id.password_text);

        paymentText.setText(getIntent().getStringExtra("RESULT_ID"));
    }

    public void ConfirmTransaction(View view)
    {
        if(password.getText().length() == 6)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Transaction Status")
                    .setMessage("Transaction Successful")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(PaymentConfirm.this, MainActivity.class));
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else
        {
            Snackbar snackbar = Snackbar.make(view, "ENTER CORRECT  PASSWORD", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
}
