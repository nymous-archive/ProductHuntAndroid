package fr.ec.producthunt.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import fr.ec.producthunt.R;

public class DetailActivity extends AppCompatActivity {

    public static final String POST_URL_KEY = "post_url_key";
    public static final String PRODUCT_NAME_KEY = "product_name_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setTitle(obtainProductNameFromIntent());

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

    private String obtainProductNameFromIntent() {

        Intent intent = getIntent();
        if (intent.getExtras().containsKey(PRODUCT_NAME_KEY)) {
            return intent.getExtras().getString(PRODUCT_NAME_KEY);
        } else {
            throw new IllegalStateException("Il faut passer le titre du produit");
        }
    }
}
