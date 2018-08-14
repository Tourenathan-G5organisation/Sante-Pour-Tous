package com.toure.santepourtous.utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Toure Nathan on 7/9/2018.
 */
public class Utility {

    public static final String ADMOB_APP_ID = "ca-app-pub-4038752504921943~1545299972";

    /**
     * Extract the ingredient name and image from the Map
     *
     * @param image Map use to extract the ingredients
     * @return ingredient JSONObject
     */
    public static JSONObject getIngredientImages(Map<String, String> image) {
        JSONObject imagesJson = new JSONObject();
        // extract the ingredient image
        if (image.size() > 0) {
            for (Map.Entry<String, String> entry : image.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                try {
                    imagesJson.put(key, value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        return imagesJson;
    }
}
