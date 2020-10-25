package com.reactlibrary;
//import printbigIMG.zpBigImg;
import zpSDK.zpSDK.*;

import java.io.IOException;
import java.lang.String;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
//import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JbxPrinter   {
    public static final String LOG_TAG = "BleManager--Ysb";
    private static BluetoothDevice myDevice;
    public static BluetoothAdapter myBluetoothAdapter;
    private static BluetoothSocket mySocket = null;
    public String SelectedBDAddress;
    public static EditText inputText;
    public static String BStr = "gbk";
    //StatusBox statusBox;
    String cpclString = "! 0 200 200 80 1"+"\n"+
            "PAGE-WIDTH 574"+"\n"+
            "T 24 0 200 10 "+"UROVO优博讯"+"\n"+
            "PRINT"+"\n";

    public boolean myconnect(String address) {
        if (address == "") {
            return false;
        } else {
            myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (myBluetoothAdapter == null) {
                return false;
            } else {
                myDevice = myBluetoothAdapter.getRemoteDevice(address);
                if (myDevice == null) {
                    Log.d(LOG_TAG,"good");
                    return false;
                } else {
                    Log.d(LOG_TAG,"error");
                    return true;
                }
            }
        }
    }



}

