package fr.ec.producthunt.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import fr.ec.producthunt.R;
import fr.ec.producthunt.data.model.Post;
import fr.ec.producthunt.ui.detail.DetailActivity;
import fr.ec.producthunt.ui.detail.DetailPostFragment;

public class MainActivity extends AppCompatActivity implements PostsFragments.Callback {

  private boolean towPane;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);

    FrameLayout detailContainer = findViewById(R.id.home_detail_container);

    if (detailContainer != null) {
      towPane = true;
      getSupportFragmentManager().beginTransaction()
          .add(R.id.home_detail_container, DetailPostFragment.getNewInstance(null))
          .commit();
    }
  }

  @Override public void onClickPost(Post post) {

    if (towPane) {
      DetailPostFragment detailPostFragment =
          (DetailPostFragment) getSupportFragmentManager().findFragmentById(R.id.home_detail_container);

      if (detailPostFragment != null) {
        detailPostFragment.loadUrl(post.getPostUrl());
      }
    } else {

      Intent intent = new Intent(this, DetailActivity.class);
      intent.putExtra(DetailActivity.POST_URL_KEY, post.getPostUrl());

      startActivity(intent);
    }
  }
}
