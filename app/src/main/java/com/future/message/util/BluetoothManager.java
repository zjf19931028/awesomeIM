package com.future.message.util;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

import androidx.fragment.app.FragmentActivity;

/**
 * Author: JfangZ
 * Email:zhangjingfang@jeejio.com
 * Date: 2021/2/23 14:26
 * Description: 蓝牙管理类
 */
public enum BluetoothManager {
    SINGLETON;
    private BluetoothAdapter mBluetoothAdapter;

    BluetoothManager() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public boolean isSupport() {
        return mBluetoothAdapter != null;
    }

    public boolean isEnable() {
        return isSupport() && mBluetoothAdapter.isEnabled();
    }

    public void openBtAsyn() {
        if (isSupport()) {
            mBluetoothAdapter.enable();
        }
    }

    public void openBtSync(FragmentActivity context) {
        if (isSupport()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            context.startActivityForResult(enableBtIntent, 0);
        }
    }

    public boolean checkGPSIsOpen() {
        LocationManager locationManager = (LocationManager) App.getInstance().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) return false;
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void openGPS(final FragmentActivity context) {
        new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("当前手机扫描蓝牙需要打开定位功能。")
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                context.finish();
                            }
                        })
                .setPositiveButton("前往设置",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                context.startActivityForResult(intent, 0);
                            }
                        })

                .setCancelable(false)
                .show();
    }

    public void scanBt(){
        if (!isEnable())return;
        if (mBluetoothAdapter.isDiscovering()){
            mBluetoothAdapter.cancelDiscovery();
        }
        mBluetoothAdapter.startDiscovery();
    }

    public void cancelScanBt(){
        if (isSupport() && mBluetoothAdapter.isDiscovering()){
            mBluetoothAdapter.cancelDiscovery();
        }
    }

    public void pin(){

    }


}
