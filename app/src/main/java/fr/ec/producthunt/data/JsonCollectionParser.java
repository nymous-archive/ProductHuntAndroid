package fr.ec.producthunt.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.ec.producthunt.data.model.Collection;
import fr.ec.producthunt.data.model.Post;

public class JsonCollectionParser {

    public List<Collection> jsonToCollections(String json) {

        try {

            JSONObject collectionsResponse = new JSONObject(json);
            JSONArray collectionsJson = collectionsResponse.getJSONArray("collections");

            int size = collectionsJson.length();

            ArrayList<Collection> collections = new ArrayList(size);

            for (int i = 0; i < collectionsJson.length(); i++) {
                JSONObject collectionJson = (JSONObject) collectionsJson.get(i);

                collections.add(jsonToCollection(collectionJson));
            }

            return collections;
        } catch (JSONException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private Collection jsonToCollection(JSONObject collectionJson) throws JSONException {
        Collection collection = new Collection();
        collection.setId(collectionJson.getInt("id"));
        collection.setName(collectionJson.getString("name"));
        collection.setTitle(collectionJson.getString("title"));
        collection.setImageUrl(collectionJson.getString("background_image_url"));

        return collection;
    }
}
