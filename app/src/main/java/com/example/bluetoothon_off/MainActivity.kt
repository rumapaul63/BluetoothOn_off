package com.example.bluetoothon_off

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {

            private val REQUEST_CODE_ENABLE_BT: Int = 1
            private val REQUEST_CODE_DISCOVERABLE_BT: Int = 2

            //bluetooth adapter
            lateinit var bAdapter: BluetoothAdapter

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_main)

                //init bluetooth adapter
                bAdapter = BluetoothAdapter.getDefaultAdapter()
                //check if bluetooth is on/off
                if (bAdapter == null){
                    "Bluetooth is not available".also{
                        findViewById<TextView>(R.id.bluetoothStatusTv).text = it }
                } else{
                    "Bluetooth is available".also{
                        findViewById<TextView>(R.id.bluetoothStatusTv).text = it }
                }
                //set image according to bluetooth status (on/off)
                if (bAdapter.isEnabled){
                    //bluetooth is on
                    findViewById<ImageView>(R.id.bluetoothStatusIv).
                    setImageResource(R.drawable.ic_bluetooth_on)
                } else {
                    //bluetooth is off
                    findViewById<ImageView>(R.id.bluetoothStatusIv).
                    setImageResource(R.drawable.ic_bluetooth_off)
                }
                //turn on bluetooth
                findViewById<Button>(R.id.turnOnBtn).setOnClickListener{
                    if (bAdapter.isEnabled){
                        //already enabled
                        Toast.makeText(this, "Already on", Toast.LENGTH_LONG).show()
                    } else{
                        //turn on bluetooth
                        val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                        startActivityForResult(intent, REQUEST_CODE_ENABLE_BT)
                    }
                }

                //turn off bluetooth
                findViewById<Button>(R.id.turnOffBtn).setOnClickListener{
                    if (!bAdapter.isEnabled){
                        //already disabled
                        Toast.makeText(this, "Already off", Toast.LENGTH_LONG).show()
                    } else{
                        //turn off bluetooth
                        bAdapter.disable()
                        findViewById<ImageView>(R.id.bluetoothStatusIv).
                        setImageResource(R.drawable.ic_bluetooth_off)
                        Toast.makeText(this, "Bluetooth turned off",
                            Toast.LENGTH_LONG).show()
                    }
                }

                //bluetooth discoverable
                findViewById<Button>(R.id.discoverableBtn).setOnClickListener{
                    if (!bAdapter.isDiscovering) {
                        Toast.makeText(this, "Making your device discoverable",
                            Toast.LENGTH_LONG).show()
                        val intent = Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))
                        startActivityForResult(intent, REQUEST_CODE_DISCOVERABLE_BT)
                    }
                }
                //get list of paired devices
                findViewById<Button>(R.id.pairedBtn).setOnClickListener {
                    if (bAdapter.isEnabled){
                        findViewById<TextView>(R.id.pairedTv).text = "Paired Devices"
                        //get list of paired devices
                        val devices = bAdapter.bondedDevices
                        for (device in devices){
                            findViewById<TextView>(R.id.pairedTv).
                            append("\nDevice: $device.name, $device")
                        }
                    } else {

                        Toast.makeText(this, "Turn on Bluetooth first",
                            Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                when (requestCode) {
                    REQUEST_CODE_ENABLE_BT ->
                        if (resultCode == Activity.RESULT_OK){
                            findViewById<ImageView>(R.id.bluetoothStatusIv).
                            setImageResource(R.drawable.ic_bluetooth_on)
                            Toast.makeText(this, "Bluetooth is on",
                                Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Could not turn on bluetooth",
                                Toast.LENGTH_LONG).show()
                        }
                }
                super.onActivityResult(requestCode, resultCode, data)
            }
        }

