package fr.ec.producthunt.ui.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ViewAnimator;

import fr.ec.producthunt.R;

public class DetailPostFragment extends Fragment {

    public static final String URL_KEY = "URL_KEY";
    private static final int PROGRESS_CHILD = 0;
    private static final int WEBVIEW_CHILD = 1;

    String postUrl;
    private WebView webView;
    private ViewAnimator viewAnimator;

    public static Fragment getNewInstance(String url) {
        Fragment fragment = new DetailPostFragment();
        Bundle bundle = new Bundle();
        bundle.putString(URL_KEY, url);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postUrl = getArguments().getString(URL_KEY);
        this.setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.detail_post_fragment, container, false);

        webView = rootView.findViewById(R.id.detail_webview);

        viewAnimator = rootView.findViewById(R.id.detail_viewanimator);
        viewAnimator.setDisplayedChild(PROGRESS_CHILD);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                viewAnimator.setDisplayedChild(PROGRESS_CHILD);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                viewAnimator.setDisplayedChild(WEBVIEW_CHILD);
            }
        });

        if (postUrl != null) {
            webView.loadUrl(postUrl);
        } else {
            viewAnimator.setDisplayedChild(WEBVIEW_CHILD);
        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.webview_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.open_in_browser:
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webView.getUrl()));
                startActivity(sendIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadUrl(String postUrl) {
        this.postUrl = postUrl;
        viewAnimator.setDisplayedChild(PROGRESS_CHILD);
        webView.loadUrl(this.postUrl);
    }
}
