package com.juno.broadcastreceiver.demo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class BroadcastReceiverDemoActivity extends Activity implements OnClickListener {
	private final String BROADCAST_MESSAGE = "com.juno.broadcastreceiver.INCREASED_NUMBER";
	private BroadcastReceiver mReceiver = null;
	private int mReiceivedNumber = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ImageButton button = (ImageButton)findViewById(R.id.iv_send_msg);
        button.setOnClickListener(this);
    }
    
    @Override
	public void onResume() {
    	super.onResume();
    	registerReceiver();
	}
    
    @Override
	public void onPause() {
    	super.onPause();
		unregisterReceiver();
	}
    
    @Override
	public void onDestroy() {
    	super.onDestroy();
	}
    
    @Override
	public void onClick(View v) {
		int id = v.getId();
		
		switch (id) {
		case R.id.iv_send_msg:
			Intent intent = new Intent(BROADCAST_MESSAGE);
			intent.putExtra("value", mReiceivedNumber);
			mReiceivedNumber++;
			sendBroadcast(intent);
			break;
		default:
			break;
		}
	}
    
	private void registerReceiver() {
		
		if(mReceiver != null)
			return;

		final IntentFilter theFilter = new IntentFilter();
        theFilter.addAction(BROADCAST_MESSAGE);

        this.mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
            	
				int strNewPostion = intent.getIntExtra("value", 0);
				if (intent.getAction().equals(BROADCAST_MESSAGE)) {
					Toast.makeText(BroadcastReceiverDemoActivity.this, "number=" + strNewPostion, Toast.LENGTH_SHORT).show();
				}
        	}
        };

        this.registerReceiver(this.mReceiver, theFilter);
	}
	
	private void unregisterReceiver() {
		if(mReceiver != null)
			this.unregisterReceiver(mReceiver);
	}
}