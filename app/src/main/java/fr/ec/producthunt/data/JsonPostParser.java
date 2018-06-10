package fr.ec.producthunt.data;

import fr.ec.producthunt.data.model.Post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Mohammed Boukadir  @:mohammed.boukadir@gmail.com
 */
public class JsonPostParser {

    public List<Post> jsonToPosts(String json) {

        try {

            JSONObject postsRespnse = new JSONObject(json);
            JSONArray postsJson = postsRespnse.getJSONArray("posts");

            int size = postsJson.length();

            ArrayList<Post> posts = new ArrayList(size);

            for (int i = 0; i < postsJson.length(); i++) {
                JSONObject postJson = (JSONObject) postsJson.get(i);

                posts.add(jsonToPost(postJson));
            }

            return posts;
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Post> collectionJsonToPosts(String json) {
        try {
            JSONObject collectionDetailResponse = new JSONObject(json);
            JSONObject collection = collectionDetailResponse.getJSONObject("collection");
            return jsonToPosts(collection.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private Post jsonToPost(JSONObject postJson) throws JSONException, ParseException {
        Post post = new Post();
        //"thumbnail": {
        //    "id": 139278,
        //    "media_type": "image",
        //    "image_url": "https://ph-files.imgix.net/b27175ba-ff99-4099-9092-1e8e8fb1cc77?auto=format&w=430&h=570&fit=max",
        //    "metadata": {}
        //},
        post.setId(postJson.getInt("id"));
        post.setTitle(postJson.getString("name"));
        post.setSubTitle(postJson.getString("tagline"));
        post.setPostUrl(postJson.getString("redirect_url"));
        post.setImageUrl(postJson.getJSONObject("thumbnail").getString("image_url"));
        post.setCommentsCount(postJson.getInt("comments_count"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US);
        Date postDate = dateFormat.parse(postJson.getString("created_at"));
        post.setPostDate(postDate.getTime());

        return post;
    }
}
