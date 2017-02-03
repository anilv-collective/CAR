package Core.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.util.Strings;

/**
 * Created by anilv on 2/3/17.
 */
public class JsonUtils {


    /**
     * get Integer value from string strJsonData
     *
     * @param strJsonData Json response in String format
     * @param key field for which value need to be fetched
     * @param defaultValue default value to be returned if key is not found or null
     * @return integer value
     */
    public static Integer GetIntegerValue(String strJsonData, String key, Integer defaultValue) {
        if (Strings.isNullOrEmpty(strJsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(strJsonData);
            return GetIntegerValue(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            System.out.println("Something went wrong: " + e.getMessage());
            return defaultValue;
        }
    }


    /**
     * get Integer value from jsonObject
     *
     * @param jsonObject response jsonObject
     * @param key field for which value need to be fetched
     * @param defaultValue default value to be returned if key is not found or null
     * @return integer value
     */
    public static Integer GetIntegerValue(JSONObject jsonObject, String key, Integer defaultValue) {
        if (jsonObject == null || Strings.isNullOrEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getInt(key);
        } catch (org.json.JSONException e) {
            System.out.println("Something went wrong: " + e.getMessage());
            return defaultValue;
        }
    }


    /**
     * get String from jsonObject
     *
     * @param jsonObject response jsonObject
     * @param key field for which value need to be fetched
     * @param defaultValue default value to be returned if key is not found or null
     * @return string value
     */
    public static String GetStringValue(JSONObject jsonObject, String key, String defaultValue) {
        if (jsonObject == null || Strings.isNullOrEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getString(key);
        } catch (org.json.JSONException e) {
            System.out.println("Something went wrong: " + e.getMessage());
            return defaultValue;
        }
    }

    /**
     * This method is used to get String value from String strJsonData
     *
     * @param strJsonData Json response in String format
     * @param key field for which value need to be fetched
     * @param defaultValue default value to be returned if key is not found or null
     * @return string value
     */
    public static String GetStringValue(String strJsonData, String key, String defaultValue) {
        if (Strings.isNullOrEmpty(strJsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(strJsonData);
            return GetStringValue(jsonObject, key, defaultValue);
        } catch (org.json.JSONException e) {
            System.out.println("Something went wrong: " + e.getMessage());
            return defaultValue;
        }
    }

}
