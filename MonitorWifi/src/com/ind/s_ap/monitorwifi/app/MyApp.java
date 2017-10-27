package com.ind.s_ap.monitorwifi.app;

import android.app.Application;

public class MyApp extends Application {
	private static MyApp instance;
	private boolean wifiConnOK;
	private boolean wifiAdminLogined;

	public static MyApp getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}

	public boolean isWifiConnOK() {
		return wifiConnOK;
	}

	public void setWifiConnOK(boolean wifiConnOK) {
		this.wifiConnOK = wifiConnOK;
	}

	public boolean isWifiAdminLogined() {
		return wifiAdminLogined;
	}

	public void setWifiAdminLogined(boolean wifiAdminLogined) {
		this.wifiAdminLogined = wifiAdminLogined;
	}

}
