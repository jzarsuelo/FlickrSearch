package com.jzarsuelo.flickrsearch.app.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by JanPaolo on 5/16/2016.
 *
 * Helper method to check the internet connectivity status of the device
 */
public class ConnectivityStatusChecker {
    Context mContext;

    public ConnectivityStatusChecker(Context context) {
        mContext = context;
    }

    public boolean isConnected() {
        ConnectivityManager connectivity =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)  {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }
}
