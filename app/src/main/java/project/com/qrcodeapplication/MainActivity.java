package project.com.qrcodeapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity //implements ZXingScannerView.ResultHandler
{
    private ZXingScannerView mScannerView;
    ImageView qrcode_image;
    String QRcode;
    EditText name_text, phNumber_text, payment_text;
    public final static int WIDTH = 500, HEIGHT = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qrcode_image = (ImageView) findViewById(R.id.qrcode_image);
        name_text = (EditText) findViewById(R.id.name);
        phNumber_text = (EditText) findViewById(R.id.phNumber);
        payment_text = (EditText) findViewById(R.id.payment);
    }

    public void ScanQRCode(View view)
    {
        startActivity(new Intent(this, ScanActivity.class));
    }

    public void CreateQRCode(View view)
    {
        if (name_text.getText().toString().isEmpty() || phNumber_text.getText().toString().isEmpty() || payment_text.getText().toString().isEmpty())
        {
            Snackbar snackbar = Snackbar.make(view, "All fields are necessary", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else
        {
            if (qrcode_image.getVisibility() == View.GONE)
                qrcode_image.setVisibility(View.VISIBLE);
            else
                qrcode_image.setVisibility(View.GONE);
            Thread t = new Thread(new Runnable()
            {
                public void run()
                {
                    QRcode = "QR CODE" + "\nName : " + name_text.getText().toString()
                            + "\nPhone Number : " + phNumber_text.getText().toString()
                            + "\nPayment : " + payment_text.getText().toString();
                    try
                    {
                        synchronized (this)
                        {
                            wait(2000);
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    try
                                    {
                                        qrcode_image.setImageBitmap(encode(QRcode));
                                    } catch (WriterException e) {e.printStackTrace();}
                                }
                            });
                        }
                    } catch (InterruptedException e) {e.printStackTrace();}
                }
            });
            t.start();
        }
    }

    Bitmap encode(String string) throws WriterException
    {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(string, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, null);
        }
        catch (IllegalArgumentException e) {return null;}
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? ContextCompat.getColor(getApplicationContext(), R.color.colorBlack):ContextCompat.getColor(getApplicationContext(), R.color.colorWhite);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 500, 0, 0, width, height);
        return bitmap;
    }
}
