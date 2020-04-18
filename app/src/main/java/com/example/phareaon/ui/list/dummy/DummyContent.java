package com.example.phareaon.ui.list.dummy;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.phareaon.MainActivity;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();


    static {
        loadPhare();
    }

    public static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    static public String loadJson() throws JSONException {
        // but
        // lecture json
        // boucle obj
        //addItem
        try {
            BufferedReader br = new BufferedReader(new
                    InputStreamReader(MainActivity.getContext().getAssets().open("phares.json")));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            String str = new String(sb.toString());

            return str;

        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    static public void loadPhare() {
        String str = null;
        try {
            str = loadJson();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jObjConnection = new JSONObject(str);
            JSONObject jsonBix = jObjConnection.getJSONObject("phares");
            JSONArray jsonA=jsonBix.getJSONArray("liste");

            for(int i =0 ; i < jsonA.length() ; i++) { // lecture
                JSONObject nomObj = (JSONObject) jsonA.get(i);
                addItem(new DummyItem(
                        nomObj.getString("id"),
                        nomObj.getString("name"),
                        nomObj.getString("region"),
                        nomObj.getString("filename"),
                        nomObj.getInt("construction"),
                        nomObj.getInt("lat"),
                        nomObj.getInt("lng"),
                        nomObj.getInt("period")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }







    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String name;
        public final String region;
        public final String filename;
        public final int construction;
        public final LatLng latLng;
        public final int period;


        public DummyItem(String id, String name, String region, String filename, int construction, double lat, double lng, int period) {
            this.id = id;
            this.name = name;
            this.region = region;
            this.filename = filename;
            this.construction = construction;
            this.latLng = new LatLng(lat,lng);
            this.period = period;
        }

        @Override
        public String toString() {
            return name+", "+region;
        }
    }
}
