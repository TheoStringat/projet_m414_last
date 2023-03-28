package iut.project;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import iut.project.R;

public class MessageActivity extends AppCompatActivity {
    private EditText phone;
    private TextView apercu;
    private Button envoyer;
    private President president = null;
    private String numTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        phone = findViewById(R.id.editTextPhone);
        envoyer = findViewById(R.id.button);
        apercu = findViewById(R.id.apercu);

        if (getIntent().hasExtra("president")) {
            president = getIntent().getParcelableExtra("president");
        }
        String message = getString(R.string.message) + " : " + getString(R.string.debutMessage) + " " + president.getNom() + getString(R.string.finMessage);
        apercu.setText(message);
        envoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

    }

    public void sendMessage() {
        numTel = phone.getText().toString();
        String message = getString(R.string.debutMessage) + " " + president.getNom() + getString(R.string.finMessage);

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(numTel, null, message, null, null);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        0);
            }
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.SEND_SMS)) {
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    0);
        }
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        numTel = phone.getText().toString();
        String message = getString(R.string.debutMessage) + " " + president.getNom() + getString(R.string.finMessage);
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(numTel, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }
}