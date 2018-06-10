package fr.ec.producthunt.data.database;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import fr.ec.producthunt.data.model.Collection;

public class CollectionDao {

  private final ProductHuntDbHelper productHuntDbHelper;

  public CollectionDao(ProductHuntDbHelper productHuntDbHelper) {
    this.productHuntDbHelper = productHuntDbHelper;
  }

  public long save(Collection collection) {
    return productHuntDbHelper.getWritableDatabase()
        .replace(DataBaseContract.CollectionTable.TABLE_NAME, null, collection.toContentValues());
  }

  public List<Collection> retrieveCollections() {
    Cursor cursor = productHuntDbHelper.getReadableDatabase()
        .query(DataBaseContract.CollectionTable.TABLE_NAME,
            DataBaseContract.CollectionTable.PROJECTIONS,
            null, null, null, null, null);

    List<Collection> collections = new ArrayList<>(cursor.getCount());

    if (cursor.moveToFirst()) {
      do {

        Collection collection = new Collection();

        collection.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseContract.CollectionTable.ID_COLUMN)));
        collection.setName(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.CollectionTable.NAME_COLUMN)));
        collection.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.CollectionTable.TITLE_COLUMN)));
        collection.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.CollectionTable.IMAGE_URL_COLUMN)));

        collections.add(collection);
      } while (cursor.moveToNext());
    }

    cursor.close();

    return collections;
  }
}
