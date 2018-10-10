package App;

import Utils.RSSParserUtils;
import com.apptastic.rssreader.Item;
import com.apptastic.rssreader.RssReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RSSParser {

    private String URL;

    public RSSParser(String URL) {
        this.URL = URL;
    }

    public List<Item> fetchRSS() {
        RssReader reader = new RssReader();

        try {
            return reader.read(URL).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveLastNewsGUID(List<Item> items) throws FileNotFoundException {
        PrintStream out = new PrintStream(new FileOutputStream(RSSParserUtils.SAVEFILE_PATH));
        out.println(items.get(0).getGuid());
        out.close();
    }

    private String getLastNewsGUID() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(RSSParserUtils.SAVEFILE_PATH));
        String lastGuid = br.readLine();
        br.close();
        return lastGuid;
    }

    public boolean hasFeedChanged(List<Item> items) throws IOException {

        if (!getLastNewsGUID().equals(items.get(0).getGuid())) {
            return true;
        }
        return false;
    }

    public List<Item> getNewItems(List<Item> items) throws IOException {

        List<Item> newItems = new ArrayList<Item>();

        for (Item item : items) {
            if (!item.getGuid().equals(getLastNewsGUID())) {
                newItems.add(item);
            } else {
                break;
            }
        }
        return newItems;
    }

    public ArrayList<ArrayList<String>> findChangedChannels(List<Item> newItems) throws IOException {

        ArrayList<ArrayList<String>> changedChannelsPerItem = new ArrayList<ArrayList<String>>();
        for (Item item : newItems) {
            ArrayList<String> changedChannels = new ArrayList<String>();

            checkLineasRegulares(item.getGuid(), changedChannels);
            checkLineasAuzolinea(item.getGuid(), changedChannels);
            checkLineasGautxori(item.getGuid(), changedChannels);
            checkLineasEspeciales(item.getGuid(), changedChannels);

            changedChannelsPerItem.add(changedChannels);
        }

        return changedChannelsPerItem;
    }


    private void checkLineasRegulares(String newItemId, List<String> changedChannels) {

        List<Item> items;
        boolean itemExists = false;

        for (int i = 1; i <= RSSParserUtils.MAX_LINEAS_REGULARES; i++) {
            RSSParser parser = new RSSParser(RSSParserUtils.RSS_BASE_URL + i);

            items = parser.fetchRSS();

            if (items.size() < 1) continue;

            for (Item item : items) {
                if (item.getGuid().equals(newItemId)) {
                    itemExists = true;
                    break;
                }
            }
            if (itemExists) {
                if (!items.get(0).getGuid().equals(getLastNewsGUID())) {
                    changedChannels.add("" + i);
                }
            }
        }

    }

    private void checkLineasAuzolinea(String newItemId, List<String> changedChannels) {
        List<Item> items;
        boolean itemExists = false;

        for (int i = 1; i <= RSSParserUtils.MAX_LINEAS_AUZOLINEA; i++) {
            RSSParser parser = new RSSParser(RSSParserUtils.RSS_BASE_URL + "A" + i);

            items = parser.fetchRSS();

            if (items.size() < 1) continue;

            for (Item item : items) {
                if (item.getGuid().equals(newItemId)) {
                    itemExists = true;
                    break;
                }
            }
            if (itemExists) {
                if (!items.get(0).getGuid().equals(getLastNewsGUID())) {
                    changedChannels.add("A" + i);
                }
            }
        }
    }

    private void checkLineasGautxori(String newItemId, List<String> changedChannels) {
        List<Item> items;
        boolean itemExists = false;

        for (int i = 1; i <= RSSParserUtils.MAX_LINEAS_GAUTXORI; i++) {
            RSSParser parser = new RSSParser(RSSParserUtils.RSS_BASE_URL + "G" + i);

            items = parser.fetchRSS();

            if (items.size() < 1) continue;

            for (Item item : items) {
                if (item.getGuid().equals(newItemId)) {
                    itemExists = true;
                    break;
                }
            }
            if (itemExists) {
                if (!items.get(0).getGuid().equals(getLastNewsGUID())) {
                    changedChannels.add("G" + i);
                }
            }
        }
    }

    private void checkLineasEspeciales(String newItemId, List<String> changedChannels) {
        List<Item> items;
        boolean itemExists = false;

        for (int i = 1; i <= RSSParserUtils.MAX_LINEAS_ESPECIALES; i++) {
            RSSParser parser = new RSSParser(RSSParserUtils.RSS_BASE_URL + "E" + i);

            items = parser.fetchRSS();

            if (items.size() < 1) continue;

            for (Item item : items) {
                if (item.getGuid().equals(newItemId)) {
                    itemExists = true;
                    break;
                }
            }
            if (itemExists) {
                if (!items.get(0).getGuid().equals(getLastNewsGUID())) {
                    changedChannels.add("E" + i);
                }
            }
        }
    }


}
