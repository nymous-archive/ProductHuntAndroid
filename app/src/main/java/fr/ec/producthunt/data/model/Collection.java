package fr.ec.producthunt.data.model;

import android.content.ContentValues;
import fr.ec.producthunt.data.database.DataBaseContract;

public class Collection {
    private long id;
    private String name;
    private String title;
    private String imageUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ContentValues toContentValues() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseContract.CollectionTable.ID_COLUMN, id);
        contentValues.put(DataBaseContract.CollectionTable.NAME_COLUMN, name);
        contentValues.put(DataBaseContract.CollectionTable.TITLE_COLUMN, title);
        contentValues.put(DataBaseContract.CollectionTable.IMAGE_URL_COLUMN, imageUrl);
        return contentValues;
    }

}
