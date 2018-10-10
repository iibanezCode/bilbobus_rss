package Messaging;

import com.apptastic.rssreader.Item;

import java.util.ArrayList;
import java.util.List;

public class MessagerImpl implements Messager {

    @Override
    public void preparePush(ArrayList<ArrayList<String>> channelsToNotify, List<Item> newItems) {

        for (int i = 0; i < newItems.size(); i++) {
            List<String> channels = channelsToNotify.get(i);

            for (String channel : channels) {
                sendPush("es_" + channel, newItems.get(i).getTitle(), newItems.get(i).getPubDate());
                sendPush("en_" + channel, newItems.get(i).getTitle(), newItems.get(i).getPubDate());
                sendPush("eus_" + channel, newItems.get(i).getTitle(), newItems.get(i).getPubDate());

            }
            sendPush("es_GENERAL", newItems.get(i).getTitle(), newItems.get(i).getPubDate());
            sendPush("en_GENERAL", newItems.get(i).getTitle(), newItems.get(i).getPubDate());
            sendPush("eus_GENERAL", newItems.get(i).getTitle(), newItems.get(i).getPubDate());

        }
    }

    @Override
    public void sendPush(String topic, String title, String pubDate) {

        System.out.println("Channel:" + topic + " Message: " + title + " On : " + pubDate);

    }

}
