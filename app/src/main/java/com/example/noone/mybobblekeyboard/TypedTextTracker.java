package com.example.noone.mybobblekeyboard;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * Used for Tracking the typed text
 */
public class TypedTextTracker extends HandlerThread {
    private static final String THREAD_NAME = TypedTextTracker.class.getName();
    private static final int TRACKING_LIMIT = 5;
    private static final int SEND_TRACKING_DURATION = 10 * 60 * 1000;
    private static final int MESSAGE_WHAT_DURATION = 0;
    private static final int MESSAGE_WHAT_DATA_LIMIT = 1;

    private Handler mMessageHandler;

    private Handler mTimerHandler;
    private Runnable mTimerRunnable;

    private List<String> mDictionaryTrackingList;

    public TypedTextTracker() {
        super(THREAD_NAME);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();

        // initialize tracking list
        mDictionaryTrackingList = new ArrayList<>();

        // prepare message handler
        mMessageHandler = new Handler(this.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                messageHandle(msg);
            }
        };

        // prepare timer handler and runnable
        mTimerHandler = new Handler();
        mTimerRunnable = new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = MESSAGE_WHAT_DURATION;
                mMessageHandler.sendMessage(msg);
            }
        };

        mTimerHandler.postDelayed(mTimerRunnable, SEND_TRACKING_DURATION);
    }

    private void messageHandle(Message msg) {
        switch (msg.what) {
            case MESSAGE_WHAT_DURATION:
                actionOnDurationCompleted();
                break;

            case MESSAGE_WHAT_DATA_LIMIT:
                actionOnDataLimitExceed();
                break;
        }
    }

    private void actionOnDurationCompleted() {
        // send tracking to server
        sendTrackingToServer();

        // clear the list
        mDictionaryTrackingList.clear();

        // reset the timer
        mTimerHandler.postDelayed(mTimerRunnable, SEND_TRACKING_DURATION);
    }

    private void actionOnDataLimitExceed() {
        // send tracking to server
        sendTrackingToServer();

        // clear the list
        mDictionaryTrackingList.clear();
    }

    public void addMessageToTrackingList(String word) {
        mDictionaryTrackingList.add(word);

        if (mDictionaryTrackingList.size() == TRACKING_LIMIT) {
            // send message to thread
            Message msg = new Message();
            msg.what = MESSAGE_WHAT_DATA_LIMIT;
            mMessageHandler.sendMessage(msg);
        }
    }

    private void sendTrackingToServer() {
        System.out.println("api call");

        // encrypt the text before send to api
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(mDictionaryTrackingList);
            byte[] text = bos.toByteArray();

            KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
            SecretKey myDesKey = keygenerator.generateKey();
            Cipher desCipher;
            desCipher = Cipher.getInstance("DES");

            desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
            byte[] textEncrypted = desCipher.doFinal(text);

            System.out.println(Arrays.toString(textEncrypted));
        } catch (Exception e) {
            // Exception
        }

        /** SEND ENCRYPTED LIST AND KEY TO SERVER
             ** textEncrypted **
             ** myDesKey **
         */

        System.out.println(mDictionaryTrackingList);
    }
}
