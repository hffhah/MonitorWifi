package com.ind.s_ap.monitorwifi.broadcast;

import com.ind.s_ap.monitorwifi.WifiAdmin;
import com.ind.s_ap.monitorwifi.app.MyApp;
import com.ind.s_ap.monitorwifi.utils.CommUtils;
import com.ind.s_ap.monitorwifi.utils.CustomDialog;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

/**
 * @ClassName:WifiBroadCast
 * @Description: wifi广播监听类
 * @author:zihaozhu
 * @date:2017年10月10日 下午5:20:07
 */
public class WifiBroadCast extends BroadcastReceiver {
	// private final String TAG = this.getClass().getSimpleName();

	private Context mContext;
	private static int objectNum;
	private static WifiAdmin mWifiAdmin;

	public WifiBroadCast() {
		super();
		objectNum++; // 每接收广播一次，生成一个对象。
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mWifiAdmin = new WifiAdmin(mContext);

			switch (msg.what) {
			case 0: // 未连接
				if (msg.arg1 == 1) {
					showDlg();
				}

				break;
			case 1: // 已连接
				// if (msg.arg2 == 1) { // 管理员已登录
				// Toast.makeText(mContext, "wifi管理员已经登录..." +
				// mWifiAdmin.getWifiInfo(), Toast.LENGTH_SHORT).show();

				// } else {
				// Toast.makeText(mContext, "wifi网络状态不稳定..." +
				// mWifiAdmin.getWifiInfo(), Toast.LENGTH_SHORT).show();
				// }

				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onReceive(Context context, Intent intent) {
		this.mContext = context;

		if (objectNum > 1) {
			return;
		}

		if (isWifiDisable()) {
			return;
		}

		Message message = new Message();
		MyApp app = MyApp.getInstance();
		if (app.isWifiConnOK()) {
			message.what = 1; // 已经连接
			if (app.isWifiAdminLogined()) { // 管理员已经登录过
				message.arg2 = 1; // 1=登录;0=未登录
			}
		} else {
			message.what = 0; // 未连接
			message.arg1 = 1; // 代表触发次数
		}

		this.handler.sendMessage(message);

	}

	private boolean isWifiDisable() {
		boolean flag = false;
		WifiAdmin mWifiAdmin = new WifiAdmin(mContext);
		if (null != mWifiAdmin) {
			String ssid = mWifiAdmin.getSSID();
			if (ssid.trim() == "0x")
				flag = true;
		}

		return flag;
	}

	private void showDlg() {
		final CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
		builder.setMessage("MonitorWifi Message");
		builder.setTitle("输入Wifi管理员密码");

		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				String userName = builder.getUserName().getText().toString();
				String psd = builder.getPsd().getText().toString();

				if (!CommUtils.checkUser(userName, psd)) {
					mWifiAdmin.disconnectWifi();
				} else {
					MyApp.getInstance().setWifiConnOK(true);
					MyApp.getInstance().setWifiAdminLogined(true);
				}

				objectNum = 0; // 操作按钮后再清楚这个对象数,预防长时间不操作也会弹出wifi登录框

				dialog.dismiss();
			}
		});

		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				mWifiAdmin.disconnectWifi();
				objectNum = 0;
				dialog.dismiss();

			}
		});

		CustomDialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);

		dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		dialog.show();
	}
}