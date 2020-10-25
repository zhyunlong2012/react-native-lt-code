package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;


import android.util.Log;

import android.content.Intent;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import android.widget.Toast;

import zpSDK.zpSDK.*;
public class LtCodeModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public LtCodeModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }
    public String SelectedBDAddress;
    String BStr = "gbk";
    //StatusBox statusBox;
    String cpclString = "! 0 200 200 80 1"+"\n"+
            "PAGE-WIDTH 574"+"\n"+
            "T 24 0 200 10 "+"UROVO优博讯"+"\n"+
            "PRINT"+"\n";
    public static String ErrorMessage="No Error";
    private static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static BluetoothAdapter myBluetoothAdapter;
    private static BluetoothDevice myDevice;
    private static BluetoothSocket mySocket = null;
    private static OutputStream myOutStream = null;
    private static InputStream myInStream = null;

    @Override
    public String getName() {
        return "LtCode";
    }

    @ReactMethod
    public void sampleMethod(String stringArgument, int numberArgument, Callback callback) {
        // TODO: Implement some actually useful functionality
        callback.invoke("Received numberArgument: " + numberArgument + " stringArgument: " + stringArgument);
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String scanCode(String code) {
        System.out.println("解密");
        EncryptWS ei = new EncryptWS();
        String en = ei.decrypt(code);
        System.out.println(en);
        return en;
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean getConnectys(String address) {
        Log.d("log macid",address);
        if (address == "") {
            return false;
        } else {
            myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (myBluetoothAdapter == null) {
                return false;
            } else {
                myDevice = myBluetoothAdapter.getRemoteDevice(address);
                if (myDevice == null) {
                    return false;
                } else {
                    return this.SPPOpen(myBluetoothAdapter, myDevice);
                }
            }
        }
    }

//    重新修改后的SPPOpen
    private boolean SPPOpen(BluetoothAdapter BluetoothAdapter, BluetoothDevice btDevice) {
        Log.d("a自己写的ssop", "SPPOpen");
        myBluetoothAdapter = BluetoothAdapter;
        myDevice = btDevice;
        if (!myBluetoothAdapter.isEnabled()) {
            return false;
        } else {
            myBluetoothAdapter.cancelDiscovery();

            try {
                mySocket = myDevice.createInsecureRfcommSocketToServiceRecord(this.SPP_UUID);
//                mySocket = myDevice.createRfcommSocketToServiceRecord(this.SPP_UUID);
            } catch (IOException var11) {
                Log.d("mySPPOpenErr", "1");
                var11.printStackTrace();
                return false;
            }

            try {
                mySocket.connect();
            } catch (IOException var10) {
                Log.d("mySPPOpenErr", "2");
                return false;
            }

            try {
                myOutStream = mySocket.getOutputStream();
            } catch (IOException var9) {
                Log.d("mySPPOpenErr", "3");
                try {
                    mySocket.close();
                } catch (IOException var5) {
                    var5.printStackTrace();
                }

                return false;
            }

            try {
                myInStream = mySocket.getInputStream();
            } catch (IOException var8) {
                Log.d("mySPPOpenErr", "4");
                try {
                    mySocket.close();
                } catch (IOException var6) {
                    var6.printStackTrace();
                }

                return false;
            }

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var7) {
                Log.d("mySPPOpenErr", "5");
            }

            Log.d("a", "SPPOpen OK");
            return true;
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean getConnect(String Macid) {
        Log.d("log macid",Macid);
        BtSPPYbx  zpSDK = new BtSPPYbx(reactContext);
        boolean res =  zpSDK.connect(Macid);
        System.out.println("module sppopen 结果" + res);
        return res;
    }


    @ReactMethod
    public void disConnect() {
        Log.d("接口","关闭蓝牙。。。");
        BtSPPYbx  zpSDK = new BtSPPYbx(reactContext);
//        zpBluetoothPrinter zpSDK=new zpBluetoothPrinter(reactContext);
//        try {
//            mySocket.close();
//        } catch (IOException var2) {
//        }
        zpSDK.disconnect();
    }

    @ReactMethod
    public void printLtLabel(String SelectedBDAddress,String products,String num,String user,String qrcodeCntent,String desc,String customer){
        System.out.println(SelectedBDAddress);
        System.out.println(products);
        System.out.println(num);
        System.out.println(user);
        System.out.println(qrcodeCntent);
        zpBluetoothPrinter zpSDK=new zpBluetoothPrinter(reactContext);
        if(!ListBluetoothDevice()){
            Log.d("SDKDemo","sdk ListBluetoothDevice 列表为空");
        }
        SDKFeed(SelectedBDAddress);
        if(!zpSDK.connect(SelectedBDAddress))
        {
            Log.d("错误","关闭蓝牙。。。");
            return;
        }else{
            Log.d("检测通过","蓝牙connect");
        }

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mydata = formatter.format(date);
//        String  newpro = "产品:" + products +"数量:" + num;
//        String  newpro = "产品:" + products;
//         String  newnum = "数量:" + num;
        zpSDK.pageSetup(800,1000);
        zpSDK.drawText(530,20,products,7,3,1,false,true);
        zpSDK.drawText(370,20,num,7,3,1,false,true);
        zpSDK.drawText(230,280,mydata,5,3,0,false,false);
        zpSDK.drawText(150,280,user,4,3,0,false,false);
        zpSDK.drawText(150,450,desc,4,3,0,false,false);
        zpSDK.drawText(80,280,customer,4,3,0,false,false);
        zpSDK.drawQrCode(40,20,qrcodeCntent,3,8,10);
        zpSDK.print(0,1);
        zpSDK.disconnect();
    }

    @ReactMethod
    public void SDKDemo(String SelectedBDAddress,String input){
        BtSPPYbx  zpSDK = new BtSPPYbx(reactContext);
//        zpBluetoothPrinter zpSDK=new zpBluetoothPrinter(reactContext);
        if(!ListBluetoothDevice()){
            Log.d("SDKDemo","sdk ListBluetoothDevice 列表为空");
        }
        Log.d("测试APP内打印内容",input);
        Log.d("测试APP内打印内容BDAddress",SelectedBDAddress);
        SDKFeed(SelectedBDAddress);

//        if(!this.getConnectys(SelectedBDAddress))
//        {
//            Log.d("错误","关闭蓝牙。。。");
//            return;
//        }else{
//            Log.d("检测通过","蓝牙connect");
//        }

         if(!zpSDK.connect(SelectedBDAddress))
         {
             Log.d("错误","关闭蓝牙。。。");
             return;
         }else{
             Log.d("检测通过","蓝牙connect");
         }
        zpSDK.pageSetup(574,100);
       zpSDK.drawText(20,10,"长春市一号信息技术服务有限公司",2,0,0,false,false);
        zpSDK.drawText(20,50,input,3,0,0,false,true);
//        zpSDK.drawQrCode(20,100,input,0,8,10);
        zpSDK.print(0,0);
//        this.disConnect();
         zpSDK.disconnect();
    }

    @ReactMethod
    public void sendText(String SelectedBDAddress,String input){
        BtSPPYbx  zpSDK = new BtSPPYbx(reactContext);
//        zpBluetoothPrinter zpSDK=new zpBluetoothPrinter(reactContext);
//        if(!this.getConnectys(SelectedBDAddress))
        if(!zpSDK.connect(SelectedBDAddress))
        {
            Log.d("错误","关闭蓝牙。。。");
            return;
        }
        try {
            zpSDK.Write(new byte[]{0x1B,0x74, (byte) 0xff});
            zpSDK.Write(input.getBytes(BStr));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        zpSDK.Write(new byte[]{0x0d,0x0a});
        zpSDK.disconnect();
    }

    @ReactMethod
    public void CPCLDemo(String SelectedBDAddress){
        Log.d("信息","CPCLDEMO");
        BtSPPYbx  zpSDK = new BtSPPYbx(reactContext);
//        zpBluetoothPrinter zpSDK=new zpBluetoothPrinter(reactContext);
        if(!BtSPP.ConnectSPP(SelectedBDAddress))
        {
            Log.d("BtSPP错误","关闭蓝牙。。。");
            return;
        }

        try {
            BtSPP.SPPWrite(cpclString.getBytes(BStr));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        BtSPP.SPPWrite(new byte[]{0x0d,0x0a});
        BtSPP.SPPClose();
    }

//    返回匹配的蓝牙mac地址
    @ReactMethod
    public void connetBluetoothDevice(Callback callback)
    {
        WritableArray map = Arguments.createArray();
        Set<BluetoothDevice> pairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        for(BluetoothDevice device : pairedDevices){
            WritableMap advertising = Arguments.createMap();
            advertising.putString("name", device.getName());
            advertising.putString("id", device.getAddress()); // mac address

            map.pushMap(advertising);

        }
        System.out.println(map);
        callback.invoke(map);

    }

//    返回蓝牙设备状态
    @ReactMethod(isBlockingSynchronousMethod = true)
    public String myBtState()
    {
        String res = "";
        if((myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter())==null)
        {
            Log.d("BtSPP错误","没有蓝牙设备");
            res = "没有蓝牙设备";
            return res;
        }else{
            Log.d("设备信息","有蓝牙设备");
            res = "蓝牙设备正常,";
        }

        if(!myBluetoothAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            Log.d("蓝牙信息","蓝牙不可用");
            res = res + "蓝牙未开启";
        }else{
            Log.d("蓝牙信息","蓝牙可用");
            res = res + "蓝牙已开启";
            Log.d("res内容",res);
        }
        Log.d("res内容2",res);
        return res;
    }




    @ReactMethod
    public boolean ListBluetoothDevice()
    {
        if((myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter())==null)
        {
            Log.d("BtSPP错误","没有蓝牙设备");
            return false;
        }else{
            Log.d("设备信息","有蓝牙设备");
        }

        if(!myBluetoothAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            Log.d("蓝牙信息","蓝牙不可用");
//            startActivityForResult(enableBtIntent, 2);
        }else{
            Log.d("蓝牙信息","蓝牙可用");
        }

        Set<BluetoothDevice> pairedDevices = myBluetoothAdapter.getBondedDevices();
        //如果有配对的设备
        if(pairedDevices.size() > 0){
            ArrayList<String> mArrayAdapter = new ArrayList<>();
            for(BluetoothDevice device : pairedDevices){
                //通过array adapter在列表中添加设备名称和地址
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                Log.i("bluetooth",device.getName() + "\n" + device.getAddress());
            }
        }else{
            Log.d("连接信息","无配对蓝牙设备");
//            Toast.makeText("暂无已配对设备",Toast.LENGTH_SHORT).show();
        }
//        SelectedBDAddress = "C0:03:84:26:49:73";
//        String pairedDevices = myBluetoothAdapter.getBondedDevices();
        return true;
    }



    public void SDKFeed(String SelectedBDAddress){
        Log.d("蓝牙信息","SPP连接蓝牙");

        BtSPPYbx  zpSDK = new BtSPPYbx(reactContext);

//        if(!this.getConnectys(SelectedBDAddress))
//        if(!BtSPP.ConnectSPP(SelectedBDAddress))
//        {
//            Log.d("错误信息","连接蓝牙设备失败");
//            return;
//        }
//        BtSPP.SPPWrite(new byte[]{0x0d,0x0a});
//        BtSPP.SPPClose();

        if(!zpSDK.connect(SelectedBDAddress))
        {
            Log.d("错误信息","连接蓝牙设备失败");
            return;
        }
        zpSDK.Write(new byte[]{0x0d,0x0a});
        zpSDK.disconnect();
    }

}
