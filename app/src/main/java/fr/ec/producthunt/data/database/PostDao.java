package fr.ec.producthunt.data.database;

import android.database.Cursor;
import fr.ec.producthunt.data.model.Post;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammed Boukadir  @:mohammed.boukadir@gmail.com
 */
public class PostDao {

  private final ProductHuntDbHelper productHuntDbHelper;

  public PostDao(ProductHuntDbHelper productHuntDbHelper) {
    this.productHuntDbHelper = productHuntDbHelper;
  }

  public long save(Post post) {
    return productHuntDbHelper.getWritableDatabase()
        .replace(DataBaseContract.PostTable.TABLE_NAME, null, post.toContentValues());
  }

  public List<Post> retrievePosts() {


    Cursor cursor = productHuntDbHelper.getReadableDatabase()
        .query(DataBaseContract.PostTable.TABLE_NAME,
            DataBaseContract.PostTable.PROJECTIONS,
            null, null, null, null, DataBaseContract.PostTable.POST_DATE_COLUMN + " DESC");

    List<Post> posts = new ArrayList<>(cursor.getCount());

    if (cursor.moveToFirst()) {
      do {

        Post post = new Post();

        post.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseContract.PostTable.ID_COLUMN)));
        post.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.PostTable.TITLE_COLUMN)));
        post.setSubTitle(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.PostTable.SUBTITLE_COLUMN)));
        post.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.PostTable.IMAGE_URL_COLUMN)));
        post.setPostUrl(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.PostTable.POST_URL_COLUMN)));
        post.setPostDate(cursor.getLong(cursor.getColumnIndexOrThrow(DataBaseContract.PostTable.POST_DATE_COLUMN)));
        post.setCommentsCount(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseContract.PostTable.COMMENTS_COUNT_COLUMN)));

        posts.add(post);


      } while (cursor.moveToNext());
    }

    cursor.close();

    return posts;
  }
}
