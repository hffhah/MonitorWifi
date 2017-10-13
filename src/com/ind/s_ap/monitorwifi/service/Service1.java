package com.ind.s_ap.monitorwifi.service;

import com.ind.s_ap.monitorwifi.utils.CommUtils;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

public class Service1 extends Service {
	private final String startService = "com.lzg.strongservice.service.Service2";

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				startService2();
				break;

			default:
				break;
			}

		};
	};

	private StrongService startS2 = new StrongService.Stub() {
		@Override
		public void stopService() throws RemoteException {
			Intent i = new Intent(getBaseContext(), Service2.class);
			getBaseContext().stopService(i);
		}

		@Override
		public void startService() throws RemoteException {
			Intent i = new Intent(getBaseContext(), Service2.class);
			getBaseContext().startService(i);
		}
	};

	@Override
	public void onTrimMemory(int level) {
		startService2();
	}

	@Override
	public void onCreate() {
		// Toast.makeText(Service1.this, "Service1������...",
		// Toast.LENGTH_SHORT).show();
		startService2();

		new Thread() {
			public void run() {
				while (true) {
					boolean isRun = CommUtils.isServiceWork(Service1.this, startService);
					if (!isRun) {
						Message msg = Message.obtain();
						msg.what = 1;
						handler.sendMessage(msg);
					}
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
	}

	private void startService2() {
		boolean isRun = CommUtils.isServiceWork(Service1.this, startService);
		if (isRun == false) {
			try {
				startS2.startService();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return (IBinder) startS2;
	}
}
