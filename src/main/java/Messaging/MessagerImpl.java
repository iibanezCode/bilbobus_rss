package Messaging;

import com.apptastic.rssreader.Item;

import java.util.ArrayList;
import java.util.List;

public class MessagerImpl implements Messager {

    @Override
    public void preparePush(ArrayList<ArrayList<String>> channelsToNotify, List<Item> newItems) {
        System.out.println("/t/t-------------------");
        for (int i = 0; i < newItems.size(); i++) {
            List<String> channels = channelsToNotify.get(i);

            for (String channel : channels) {
                sendPush("es_" + channel, newItems.get(i).getTitle());
                sendPush("en_" + channel, newItems.get(i).getTitle());
                sendPush("eus_" + channel, newItems.get(i).getTitle());
                System.out.println("/t/t-------------------");
            }
                sendPush("es_GENERAL", newItems.get(i).getTitle());
                sendPush("en_GENERAL", newItems.get(i).getTitle());
                sendPush("eus_GENERAL", newItems.get(i).getTitle());
            System.out.println("/t/t-------------------");
        }
        System.out.println("/t/t-------------------");
    }

    @Override
    public void sendPush(String topic, String content) {

        System.out.println("Channel:" + topic + " Message: " + content);

    }

}
