package com.example.yishutansuo_2zhang_client;

import java.util.ArrayList;

import apis.amapv2.com.listviewlibrary.activity.BaseListActivty;
import apis.amapv2.com.listviewlibrary.bean.ItemObject;


public class MainActivity extends BaseListActivty {


    @Override
    protected void addData(ArrayList<ItemObject> data) {
        data.add(new ItemObject("通过Messenger来实现进程间通信",MessengerActivity.class));
    }
}
