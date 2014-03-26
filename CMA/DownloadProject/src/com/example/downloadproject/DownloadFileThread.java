package com.example.downloadproject;

import java.net.URL;

import android.content.Context;

public class DownloadFileThread extends Thread {
	
	public static final String TAG = "DOWNLOAD";
	
	// acções a enviar por broadcast
	// DOWNLOAD_UPDATE_ACTION: para enviar updates do progresso do download
	public static final String DOWNLOAD_UPDATE_ACTION = "DOWNLOAD_UPTADATE";
	// DOWNLOAD_CANCELED_ACTION: para notificar que o download foi cancelado
	public static final String DOWNLOAD_CANCELED_ACTION = "DOWNLOAD_CANCELED";

	// KEY do par Key/Value que contém a percentagem de progresso do download
	public static final String PERCENT_EXTRA = "PERCENT_EXTRA";
	// Diferença em % do progresso do download para que seja emitido um broadcast com DOWNLOAD_UPDATE_ACTION
	private final static int PERCENT_DIFF = 1;
	
	private Context c;
	private URL url;
	private boolean cancelled = false;
	
	public DownloadFileThread(Context c, URL url) {
		super();
		this.c = c;
		this.url = url;
	}
}
