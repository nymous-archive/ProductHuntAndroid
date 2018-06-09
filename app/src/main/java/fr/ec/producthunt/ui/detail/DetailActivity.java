package fr.ec.producthunt.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import fr.ec.producthunt.R;

public class DetailActivity extends AppCompatActivity {

  public static final String POST_URL_KEY = "post_url_key";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.detail_activity);

    FragmentManager fragmentManager = getSupportFragmentManager();

    fragmentManager.beginTransaction()
        .add(R.id.detail_container, DetailPostFragment.getNewInstance(obtainPostUrlFromIntent()))
        .commit();
  }

  private String obtainPostUrlFromIntent() {

    Intent intent = getIntent();
    if (intent.getExtras().containsKey(POST_URL_KEY)) {
      return intent.getExtras().getString(POST_URL_KEY);
    } else {
      throw new IllegalStateException("Il faut passer l'url du post");
    }
  }
}
