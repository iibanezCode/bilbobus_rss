package App;

import Messaging.Messager;
import Messaging.MessagerImpl;
import Utils.RSSParserUtils;
import com.apptastic.rssreader.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainApplication {


    public static void main(String[] args) {


        List<Item> items;
        RSSParser parser = new RSSParser(RSSParserUtils.RSS_ROOT_URL);

        items = parser.fetchRSS();
        System.out.println(">>>>RSS_ROOT_URL parsing complete");

        try {
            if (parser.hasFeedChanged(items)) {
                List<Item> newItems;
                newItems = parser.getNewItems(items);
                System.out.println(">>>>Numero de Items Nuevos:" + newItems.size());
                ArrayList<ArrayList<String>> changedChannels = parser.findChangedChannels(newItems);
                Messager messager = new MessagerImpl();
                messager.preparePush(changedChannels, newItems);
            } else {
                System.out.println("Nothing Changed");
            }

            //parser.saveLastNewsGUID(items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
