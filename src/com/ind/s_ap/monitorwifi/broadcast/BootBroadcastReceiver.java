package com.ind.s_ap.monitorwifi.broadcast;

import com.ind.s_ap.monitorwifi.MainActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @ClassName:BootBroadcastReceiver
 * @Description: 开机自启
 * @author:zihaozhu
 * @date:2016年10月10日 下午5:20:07
 */
public class BootBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent mBootIntent = new Intent(context, MainActivity.class);
		mBootIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(mBootIntent);
	}
}
