package com.example.yishutansuo_2zhang_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

public class MessengerActivity extends AppCompatActivity {
    private static final String TAG = MessengerActivity.class.getSimpleName();
    private Messenger mGetReplyMessenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler {


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    Log.e(TAG, "接收到服务器的消息：" + msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private Messenger mService;
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            //创建Messenger(用来发送消息的)
            mService = new Messenger(service);
            //创建Message，设置数据，设置需要从服务器返回的Messenger。
            Message msg = Message.obtain(null, 1);
            Bundle data = new Bundle();
            data.putString("msg", "你好，世界");
            msg.setData(data);
            msg.replyTo = mGetReplyMessenger;
            try {
                //利用消息发送器来发送消息
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName className) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        //开始绑定远程服务
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("test_service");
                intent.setPackage("com.example.yishutansuo_2zhang_server");
                bindService(intent, mConnection, BIND_AUTO_CREATE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }
}
