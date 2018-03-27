package com.example.alexey.poiskbs2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn;
    TextView tx_v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx_v = (TextView) findViewById(R.id.editText3);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        TelephonyManager telephonyManager =
                (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        List<CellInfo> cellInfos = telephonyManager.getAllCellInfo();


        int n = cellInfos.size(); // размер списка

        String msg = "BS count " + String.valueOf(n)+"\n";

        int i = 0;
        while (n>0) {
            //Log.d("tst");
            msg += " N[" + String.valueOf(i) + "] is ";
            CellInfo c = cellInfos.get(i); //

            i++;
            n--;
            int level = 0;
            if (c instanceof CellInfoCdma) {
                level = ((CellInfoCdma) c).getCellSignalStrength().getLevel();
                CellIdentityCdma ci = ((CellInfoCdma) c).getCellIdentity();

                msg += "CDMA\t"+ci.toString()+"\t\n";
            }
            if (c instanceof CellInfoGsm) {
                level = ((CellInfoGsm) c).getCellSignalStrength().getLevel();
                CellIdentityGsm ci = ((CellInfoGsm) c).getCellIdentity();
                msg += "GSM\t"+ci.toString()+"\t\n";
            }
            if (c instanceof CellInfoLte) {
                level = ((CellInfoLte) c).getCellSignalStrength().getLevel();
                CellIdentityLte ci = ((CellInfoLte) c).getCellIdentity();
                msg += "LTE\t"+ci.toString()+"\n";
            }
            if (c instanceof CellInfoWcdma) {
                level = ((CellInfoWcdma) c).getCellSignalStrength().getLevel();
                CellIdentityWcdma ci = ((CellInfoWcdma) c).getCellIdentity();
                msg += "WCDMA\t"+ci.toString()+"\n";
            }
            msg += " signal strength is " + String.valueOf(level) + "\n\n";


            tx_v.setText(msg);
        }
    }
}
