package com.toure.santepourtous.data;

import android.arch.persistence.room.TypeConverter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Toure Nathan on 7/18/2018.
 */
public class JSONObjectConverter {

    @TypeConverter
    public static JSONObject toJSONObject(String string) {
        if (string != null) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(string);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject;
        }
        return null;

    }

    @TypeConverter
    public static String toString(JSONObject jsonObject) {
        return jsonObject == null ? null : jsonObject.toString();

    }
}
