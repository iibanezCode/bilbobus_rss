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

    public boolean hasFeedChanged(List<Item> items) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(RSSParserUtils.SAVEFILE_PATH));

        if (!br.readLine().trim().equals(items.get(0).getGuid())) {
            return true;
        }
        return false;
    }

    public List<Item> getNewItems(List<Item> items) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(RSSParserUtils.SAVEFILE_PATH));
        String lastGuid = br.readLine();
        br.close();
        List<Item> newItems = new ArrayList<Item>();

        for (Item item : items) {
            if (!item.getGuid().equals(lastGuid)) {
                newItems.add(item);
            } else {
                break;
            }
        }
        return newItems;
    }

    public ArrayList<ArrayList<String>> findChangedChannels(List<Item> newItems) throws IOException {


        BufferedReader br = new BufferedReader(new FileReader(RSSParserUtils.SAVEFILE_PATH));
        String lastGuid = br.readLine();
        br.close();

        ArrayList<ArrayList<String>> changedChannelsPerItem = new ArrayList<ArrayList<String>>();
        for (Item item : newItems) {
            System.out.println(">>>> findChangedChannels proccessing following Guid: " + item.getGuid());
            ArrayList<String> changedChannels = new ArrayList<String>();

            checkLineasRegulares(item.getGuid(), lastGuid, changedChannels);
            checkLineasAuzolinea(item.getGuid(), lastGuid, changedChannels);
            checkLineasGautxori(item.getGuid(), lastGuid, changedChannels);
            checkLineasEspeciales(item.getGuid(), lastGuid, changedChannels);

            changedChannelsPerItem.add(changedChannels);
        }

        return changedChannelsPerItem;
    }


    private void checkLineasRegulares(String newItemId, String lastProcessedId, List<String> changedChannels) {

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
                if (!items.get(0).getGuid().equals(lastProcessedId)) {
                    changedChannels.add("" + i);
                }
            }
        }

    }

    private void checkLineasAuzolinea(String newItemId, String lastProcessedId, List<String> changedChannels) {
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
                if (!items.get(0).getGuid().equals(lastProcessedId)) {
                    changedChannels.add("A" + i);
                }
            }
        }
    }

    private void checkLineasGautxori(String newItemId, String lastProcessedId, List<String> changedChannels) {
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
                if (!items.get(0).getGuid().equals(lastProcessedId)) {
                    changedChannels.add("G" + i);
                }
            }
        }
    }

    private void checkLineasEspeciales(String newItemId, String lastProcessedId, List<String> changedChannels) {
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
                if (!items.get(0).getGuid().equals(lastProcessedId)) {
                    changedChannels.add("E" + i);
                }
            }
        }
    }


}
