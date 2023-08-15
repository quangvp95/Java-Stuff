package demo.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

public class JsonExecution {
    private static final Type REVIEW_TYPE = new TypeToken<HashMap<String, TVPhone>>() {
    }.getType();


    public static void main(String[] args) throws FileNotFoundException {
        // TODO Auto-generated method stub
//		Scanner scanner = new Scanner(System.in);
//		Scanner scanner = new Scanner(new File("/home/quangnh/Work/abp/diff103.diff"));
        String line = "";
        Gson gson = new Gson();
        JsonReader reader;
        Type REVIEW_TYPE;
        HashMap<String, Integer> set = new HashMap<>();
        int count;

//        HashMap<String, TVPhone> dataPhone = getPhoneObject("/home/quangnh/Downloads/coc-coc-browser-device_connected-export.json");
//        System.out.println("dataPhone : " + dataPhone.size());
//        int count = 0;
//        for (Map.Entry<String, TVPhone> i : dataPhone.entrySet()) {
//            if (i.getValue().size() > 0) {
//                for (Map.Entry<String, Phone> phone : i.getValue().entrySet()) {
//                    if (set.containsKey(phone.getValue().senderType)) {
//                        set.put(phone.getValue().senderType, set.get(phone.getValue().senderType) + 1);
//                    } else {
//                        set.put(phone.getValue().senderType, 1);
//                    }
//                }
//            } else {
//                count++;
//            }
//        }
//        //        for (String i : set) {
//        System.out.println("phone : " + set);
////        }


        TV dataTV; // contains the whole reviews list

        dataTV = getTVObject("/home/quangnh/Downloads/data/2023-07-16T23_36_41Z_coc-coc-browser_data.json");
        System.out.println("dataLink : " + dataTV);


        HashMap<String, TVLink> dataLink = dataTV.cctv.links;
        System.out.println("dataLink : " + dataLink.size());
        ArrayList<TVLink> tvList = new ArrayList<>();

        count = 0;
        for (Map.Entry<String, TVLink> i : dataLink.entrySet())
            if (i.getValue().size() > 0) {
                i.getValue().entry = i.getKey();
                tvList.add(i.getValue());
                for (Map.Entry<String, PushedLink> link : i.getValue().entrySet()) {
                    if (set.containsKey(link.getValue().url)) {
                        set.put(link.getValue().url, set.get(link.getValue().url) + 1);
                    } else {
                        set.put(link.getValue().url, 1);
//                        System.out.println("TV url : " + link.getValue().url);
                    }
                    count++;
                }
            } else {
                System.out.println("TV null : " + i.getKey());
            }

        tvList.sort(Comparator.comparingLong(TVLink::getTimestamp));
        System.out.println("TVLink : " + count + " link | " + set.size());
        for (int j = 0; j < tvList.size(); j++) {
            TVLink i = tvList.get(j);
            System.out.println(i);
        }
    }

    public static TV getTVObject(String filePath) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(filePath));
        Type REVIEW_TYPE = new TypeToken<TV>() {
        }.getType();
        return gson.fromJson(reader, REVIEW_TYPE);
    }

    public static HashMap<String, TVPhone> getPhoneObject(String filePath) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(filePath));
        Type REVIEW_TYPE = new TypeToken<HashMap<String, TVPhone>>() {
        }.getType();
        return gson.fromJson(reader, REVIEW_TYPE);
    }

    public static HashMap<String, TVLink> getLinksObject(String filePath) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(filePath));
        Type REVIEW_TYPE = new TypeToken<HashMap<String, TVLink>>() {
        }.getType();
        return gson.fromJson(reader, REVIEW_TYPE);
    }


    public static class TV {
        public CCTV cctv;

        @Override
        public String toString() {
            return cctv.toString();
        }
    }

    public static class CCTV {
        public HashMap<String, TVPhone> device_connected;
        public HashMap<String, TVLink> links;

        @Override
        public String toString() {
            return "CCTV{" +
                    "device_connected=" + device_connected.size() +
                    ", links=" + links.size() +
                    '}';
        }
    }


    public static class TVPhone extends HashMap<String, Phone> {
    }

    public static class Phone {
        public boolean disconnected;
        public String receiver;
        public String senderId;
        public String senderName;
        public String senderType;
        public String timestamp;
    }

    public static class TVLink extends HashMap<String, PushedLink> {
        private long timestamp = -1;
        public String entry = "";

        public long getTimestamp() {
            if (timestamp != -1) {
                return timestamp;
            }
            for (Entry<String, PushedLink> i : entrySet()) {
                try {
                    timestamp = Math.max(timestamp, Long.parseLong(i.getValue().timestamp));
                } catch (Exception e) {
                    System.out.println("Error parseLong " + i.getValue());
                }
            }
            if (timestamp < 2_000_000_000)
                timestamp *= 1000;
            return timestamp;
        }

        @Override
        public String toString() {
            return new Date(timestamp) + " | " + size() + " | " + "\"" + entry + "\",";
        }
    }

    public static class PushedLink {
        public String senderName;
        public String sender;
        public String receiver;
        public String title;
        public String url;
        public String timestamp;
    }
}
