package fr.ec.producthunt.data.model;

import android.content.ContentValues;
import fr.ec.producthunt.data.database.DataBaseContract;

public class Post {
  private String title;
  private String subTitle;
  private String imageUrl;
  private String postUrl;
  private long postDate;
  private int commentsCount;

  private long id;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSubTitle() {
    return subTitle;
  }

  public void setSubTitle(String subTitle) {
    this.subTitle = subTitle;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public String getPostUrl() {
    return postUrl;
  }

  public void setPostUrl(String postUrl) {
    this.postUrl = postUrl;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public long getPostDate() {
    return postDate;
  }

  public void setPostDate(long postDate) {
    this.postDate = postDate;
  }

  public int getCommentsCount() {
    return commentsCount;
  }

  public void setCommentsCount(int commentsCount) {
    this.commentsCount = commentsCount;
  }

  public ContentValues toContentValues() {

    ContentValues contentValues = new ContentValues();
    contentValues.put(DataBaseContract.PostTable.ID_COLUMN, id);
    contentValues.put(DataBaseContract.PostTable.TITLE_COLUMN, title);
    contentValues.put(DataBaseContract.PostTable.SUBTITLE_COLUMN, subTitle);
    contentValues.put(DataBaseContract.PostTable.IMAGE_URL_COLUMN, imageUrl);
    contentValues.put(DataBaseContract.PostTable.POST_URL_COLUMN, postUrl);
    contentValues.put(DataBaseContract.PostTable.POST_DATE_COLUMN, postDate);
    contentValues.put(DataBaseContract.PostTable.COMMENTS_COUNT_COLUMN, commentsCount);
    return contentValues;
  }

}
