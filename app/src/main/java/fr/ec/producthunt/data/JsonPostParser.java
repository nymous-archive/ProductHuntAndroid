package fr.ec.producthunt.data;

import fr.ec.producthunt.data.model.Post;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Mohammed Boukadir  @:mohammed.boukadir@gmail.com
 */
public class JsonPostParser {

  public  List<Post> jsonToPosts(String json) {

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
    } catch (JSONException e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  private  Post jsonToPost(JSONObject postJson) throws JSONException {
    Post post = new Post();
    //"thumbnail": {
    //  "id": 139278,
    //      "media_type": "image",
    //      "image_url": "https://ph-files.imgix.net/b27175ba-ff99-4099-9092-1e8e8fb1cc77?auto=format&w=430&h=570&fit=max",
    //      "metadata": {}
    //},
    post.setId(postJson.getInt("id"));
    post.setTitle(postJson.getString("name"));
    post.setSubTitle(postJson.getString("tagline"));
    post.setPostUrl(postJson.getString("redirect_url"));

    post.setImageUrl(postJson.getJSONObject("thumbnail").getString("image_url"));


    return post;
  }
}
