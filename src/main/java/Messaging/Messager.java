package Messaging;

import com.apptastic.rssreader.Item;

import java.util.ArrayList;
import java.util.List;

public interface Messager {

    void preparePush(ArrayList<ArrayList<String>> channelsToNotify, List<Item> newItems);
    void sendPush(String topic,String content);
}
