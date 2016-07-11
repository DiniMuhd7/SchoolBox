package com.deenysoft.schoolbox.cosmos.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.deenysoft.schoolbox.cosmos.services.CosmosService;

/**
 * Created by shamsadam on 28/03/16.
 */
public class BootCompletedReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent msgIntent = new Intent(context, CosmosService.class);
            context.startService(msgIntent);
        }
    }
}
