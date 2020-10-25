package com.reactlibrary;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
public class BtSPP {
    public static String ErrorMessage="No Error";
    private static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static BluetoothAdapter myBluetoothAdapter;
    private static BluetoothDevice myDevice;
    private static BluetoothSocket mySocket = null;
    private static OutputStream myOutStream = null;
    private static InputStream myInStream = null;

    public static boolean ConnectSPP(String BDAddr)
    {
        if(BDAddr==""||BDAddr==null)
        {
            Log.d("ConnectSPP错误","Did not choose printer");
            ErrorMessage="Did not choose printer";
            return false;
        }
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(myBluetoothAdapter==null)
        {
            Log.d("ConnectSPP错误","Sytemerror :BT");
            ErrorMessage="Sytemerror :BT";
            return false;
        }
        myDevice = myBluetoothAdapter.getRemoteDevice(BDAddr);
        if(myDevice==null)
        {
            Log.d("ConnectSPP错误","READ BT error");
            ErrorMessage="READ BT error";
            return false;
        }
        if(!BtSPP.SPPOpen(myBluetoothAdapter, myDevice))
        {
            Log.d("ConnectSPP错误","sspopen错误");
            return false;
        }
        Log.d("spp信息","spp通过");
        return true;
    }
//    public static boolean SPPOpen(BluetoothAdapter bluetoothAdapter, BluetoothDevice btDevice)
//    {
//        boolean error=false;
//        myBluetoothAdapter = bluetoothAdapter;
//        myDevice = btDevice;
//
//        if(!myBluetoothAdapter.isEnabled())
//        {
//            Log.d("SSPOpen信息","BT Adaptor did not power setup");
//            ErrorMessage = "BT Adaptor did not power setup";
//            return false;
//        }
//        myBluetoothAdapter.cancelDiscovery();
//
//        try
//        {
//            //mySocket = myDevice.createRfcommSocketToServiceRecord(SPP_UUID);
//            Method m = myDevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
//            mySocket = (BluetoothSocket) m.invoke(myDevice, 1);
//            Log.d("SSPOpen信息","通过步骤1");
//        }
//        catch (SecurityException e){
//            mySocket = null;
//            Log.d("SSPOpen信息","BT port ERROR");
//            ErrorMessage = "BT port ERROR";
//            return false;
//        }
//        catch (NoSuchMethodException e) {
//            mySocket = null;
//            ErrorMessage = "BT port ERROR";
//            return false;
//        } catch (IllegalArgumentException e) {
//            mySocket = null;
//            ErrorMessage = "BT port ERROR";
//            return false;
//        } catch (IllegalAccessException e) {
//            mySocket = null;
//            ErrorMessage = "BT port ERROR";
//            return false;
//        } catch (InvocationTargetException e) {
//            mySocket = null;
//            ErrorMessage = "BT port ERROR";
//            return false;
//        }
//
//        try
//        {
//            Log.d("SSPOpen信息","开始步骤2");
//            mySocket.connect();
//            Log.d("SSPOpen信息","通过步骤2");
//        }
//        catch (IOException e2)
//        {
//            ErrorMessage = e2.getLocalizedMessage();//"CAN NOT LINK TO printer";
//            mySocket = null;
//            return false;
//        }
//
//        try
//        {
//            myOutStream = mySocket.getOutputStream();
//            Log.d("SSPOpen信息","通过步骤3");
//
//        }
//        catch (IOException e3)
//        {
//            Log.d("SSPOpen信息","步骤3错误");
//            myOutStream = null;
//            error = true;
//        }
//
//        try
//        {
//            myInStream = mySocket.getInputStream();
//            Log.d("SSPOpen信息","通过步骤4");
//        }
//        catch (IOException e3)
//        {
//            myInStream = null;
//            error = true;
//        }
//
//        if(error)
//        {
//            Log.d("SSPOpen信息","错误关闭SSP");
//            SPPClose();
//            return false;
//        }
//
//        return true;
//    }


    public static boolean SPPOpen(BluetoothAdapter BluetoothAdapter, BluetoothDevice btDevice) {
        Log.d("a自己写的ssop", "SPPOpen");
        myBluetoothAdapter = BluetoothAdapter;
        myDevice = btDevice;
        if (!myBluetoothAdapter.isEnabled()) {
            return false;
        } else {
            myBluetoothAdapter.cancelDiscovery();

            try {
                mySocket = myDevice.createInsecureRfcommSocketToServiceRecord(SPP_UUID);
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
    public static boolean SPPClose()
    {
        try {Thread.sleep(1000);} catch (InterruptedException e) {}
        if(myOutStream!=null)
        {
            try{myOutStream.flush();}catch (IOException e1){}
            try{myOutStream.close();}catch (IOException e){}
            myOutStream=null;
        }
        if(myInStream!=null)
        {
            try{myInStream.close();}catch(IOException e){}
            myInStream=null;
        }
        if(mySocket!=null)
        {
            try{mySocket.close();}catch (IOException e){}
            mySocket=null;
        }
        try {Thread.sleep(200);} catch (InterruptedException e) {}
        return true;
    }

    public static boolean SPPWrite(byte[] Data)
    {
        try
        {
            myOutStream.write(Data);
        }
        catch (IOException e)
        {
            ErrorMessage = "Failed to send bluetooth data";
            return false;
        }
        return true;
    }
    public static boolean SPPWrite(byte[] Data,int DataLen)
    {
        try
        {
            myOutStream.write(Data,0,DataLen);
        }
        catch (IOException e)
        {
            ErrorMessage = "Failed to send bluetooth data";
            return false;
        }
        return true;
    }
    public static void SPPFlush()
    {
        int i=0,DataLen=0;
        try
        {
            DataLen = myInStream.available();
        }
        catch (IOException e1)
        {
        }
        for(i=0;i<DataLen;i++)
        {
            try
            {
                myInStream.read();
            }
            catch (IOException e)
            {
            }
        }
    }
    public static boolean SPPRead(byte[] Data,int DataLen)
    {
        return SPPReadTimeout(Data,DataLen,2000);
    }
    public static boolean SPPReadTimeout(byte[] Data,int DataLen,int Timeout)
    {
        int i;
        for(i=0;i<(Timeout/50);i++)
        {
            try
            {
                if(myInStream.available()>=DataLen)
                {
                    try
                    {
                        myInStream.read(Data,0,DataLen);
                        return true;
                    }
                    catch (IOException e)
                    {
                        ErrorMessage = "Failed to read bluetooth data";
                        return false;
                    }
                }
            }
            catch (IOException e)
            {
                ErrorMessage = "Failed to read bluetooth data";
                return false;
            }
            try
            {
                Thread.sleep(50);
            }
            catch (InterruptedException e)
            {
                ErrorMessage = "Failed to read bluetooth data";
                return false;
            }
        }
        ErrorMessage = "Bluetooth read data timeout";
        return false;
    }
}
