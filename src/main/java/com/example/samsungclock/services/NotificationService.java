package com.example.samsungclock.services;

import android.content.Context;
import android.content.pm.Capability;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @Author: Vitor Rey
 * @Date: 05, junho, 2023
 * @Email: vitorrey.dev@gmail.com
 **/
public class NotificationService {
    private final String TAG = getClass().getSimpleName();
    private final String WEAR_CAPABILITY = "wear_sync";
    private final String WEAR_SEND_MESSAGE = "/wear_send_message";

    private Context mContext;

    public void send() {
        CapabilityInfo capabilityInfo = null;
        try {
            capabilityInfo = Tasks.await(Wearable.getCapabilityClient(mContext)
                    .getCapability(WEAR_CAPABILITY, CapabilityClient.FILTER_REACHABLE ));
        } catch (ExecutionException | InterruptedException e){
            // TODO
        }

        Set<Node> nodeList = capabilityInfo.getNodes();
        if (nodeList.isEmpty()) {
            Log.i(TAG, "send() " + "Node is empty");
        }else {
            for (Node node : nodeList) {
                if (node.isNearby()) {
                    Task<Integer> sendTask = Wearable.getMessageClient(mContext)
                            .sendMessage(node.getId(), WEAR_SEND_MESSAGE, "OI".getBytes(StandardCharsets.UTF_8));

                }
            }
        }
    }
}
