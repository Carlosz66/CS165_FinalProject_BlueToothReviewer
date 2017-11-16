package com.example.carlos.finalproject;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class ShareActivity extends AppCompatActivity {
    private static final String TAG = "ShareActivity";
    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    //bluetooth
    private BluetoothAdapter mBtAdapter;//for getting the devices
    private BluetoothAdapter mBluetoothAdapter = null;//for communication

    private Set<BluetoothDevice> pairedDevices;

    //String buffer for outgoing messages
    private StringBuffer mOutStringBuffer;

    // Member object for the chat services
    private BluetoothChatService mChatService = null;

    //UI interface
    Button shareButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        viewSetup();
        blueToothSetUp();
    }


    private void viewSetup(){
        shareButton =  findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doDiscovery();
            }
        });
    }



    private void blueToothSetUp(){
        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        // Get the local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();// for communication
        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            this.finish();
        }
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();// for getting the devices


        // Get a set of currently paired devices
        pairedDevices= new HashSet<>();

        //setupChat();
        //checkBonded();

    }






    private void doDiscovery() {
        pairedDevices.clear();
        Log.d(TAG, "doDiscovery()");
        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        // Indicate scanning in the title
        setProgressBarIndeterminateVisibility(true);
        setTitle(R.string.scanning);


        // If we're already discovering, stop it
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }

        // Request discover from BluetoothAdapter
        mBtAdapter.startDiscovery();
    }


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    pairedDevices.add(device);
                    Log.d(TAG,device.getName() + "\n" + device.getAddress());

                }

                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setProgressBarIndeterminateVisibility(false);
                setTitle(R.string.select_device);
                Log.d(TAG,"Device Search End");

                Set<BluetoothDevice> tmp = mBtAdapter.getBondedDevices();

                for(BluetoothDevice device :tmp) {
                    pairedDevices.add(device);
                    Log.d(TAG,"Bonded Device:"+device.getName()+device.getAddress());
                }
                //pairedDevices=mBtAdapter.getBondedDevices();
                checkBonded();
            }
        }
    };

    //to check every device and share it with then; first time do this checking, calling this
    private void checkBonded(){
        this.unregisterReceiver(mReceiver);
        BluetoothDevice tmpDevice=null;
        for (BluetoothDevice device : pairedDevices) {
            /*if(device.getAddress().compareTo("44:80:EB:A6:2F:E0")==0){
                Log.d(TAG,"Find the phone: "+device.getName());
                connectDevice(device.getAddress(),false);
                break;
            }*/
            Log.d(TAG,"Find the phone: "+device.getName());
            connectDevice(device.getAddress(),false);
            tmpDevice=device;
            break;
        }
        if(tmpDevice!=null)
            pairedDevices.remove(tmpDevice);
    }

    //to check every device and share it with then; after first time do this checking, calling this
    private void secondCheckBonded(){
        BluetoothDevice tmpDevice=null;
        for (BluetoothDevice device : pairedDevices) {
            /*if(device.getAddress().compareTo("44:80:EB:A6:2F:E0")==0){
                Log.d(TAG,"Find the phone: "+device.getName());
                connectDevice(device.getAddress(),false);
                break;
            }*/
            Log.d(TAG,"Find the phone: "+device.getName());
            connectDevice(device.getAddress(),false);
            tmpDevice=device;
            break;
        }
        if(tmpDevice!=null)
            pairedDevices.remove(tmpDevice);
    }


    /**
     * Set up the UI and background operations for chat.
     */
    private void setupChat() {
        Log.d(TAG, "setupChat()");

        //sendMessage(message);

        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothChatService(this, mHandler);
        mChatService.start();

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

    /**
     * The Handler that gets information back from the BluetoothChatService
     */
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            Log.d(TAG, "connected!!!!!!!!!!!!!!!!!!!!!!");
                            //Toast.makeText(getApplicationContext(), "STATE_CONNECTED", Toast.LENGTH_LONG).show();
                            doThings();
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            //Toast.makeText(getApplicationContext(), "STATE_CONNECTING", Toast.LENGTH_LONG).show();
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:

                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    Log.d(TAG,writeMessage);
                    Toast.makeText(getApplicationContext(), "write "+writeMessage, Toast.LENGTH_LONG).show();
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    Log.d(TAG,readMessage);
                    Toast.makeText(getApplicationContext(), "read "+readMessage, Toast.LENGTH_LONG).show();
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    String mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    if (null != this) {
                        Toast.makeText(getApplicationContext(), "Connected to "
                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Constants.MESSAGE_TOAST:
                    if (null != this) {
                        if(msg.getData().getString(Constants.TOAST).compareTo("Unable to connect device")==0){
                            secondCheckBonded();
                            Log.d(TAG,"unable to,,,,,,try to connect others");
                        }
                        Toast.makeText(getApplicationContext(), msg.getData().getString(Constants.TOAST),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    private void doThings(){
        Log.d(TAG, "writing!!!!!!!!!!!!!!!!!!!!!!");
        sendMessage("hello world");
    }

    /**
     * Sends a message.
     *
     * @param message A string of text to send.
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            //Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            Log.d(TAG,"not connected!");
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
        }
    }


    private void connectDevice(String address, boolean secure) {
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mChatService.connect(device, secure);
    }

    @Override
    public void onStart() {
        super.onStart();
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else if (mChatService == null) {
            setupChat();
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);

        if (mChatService != null) {
            mChatService.stop();
        }
    }
}
