package fr.ec.producthunt.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ViewAnimator;

import fr.ec.producthunt.R;
import fr.ec.producthunt.data.DataProvider;
import fr.ec.producthunt.data.SyncService;
import fr.ec.producthunt.data.model.Post;
import fr.ec.producthunt.ui.detail.DetailActivity;

import java.util.List;

public class PostsFragments extends Fragment {

    private static final int PROGRESS_CHILD = 1;
    private static final int LIST_CHILD = 0;
    public static final String CATEGORY_ID_KEY = "CATEGORY_ID_KEY";


    long categoryId;
    private DataProvider dataProvider;
    private PostAdapter postAdapter;
    private ViewAnimator viewAnimator;
    private SwipeRefreshLayout swipeRefreshLayout;

    private SyncPostReceiver syncPostReceiver;

    private Callback callback;

    public static Fragment getNewInstance(long id) {
        Fragment fragment = new PostsFragments();
        Bundle bundle = new Bundle();
        bundle.putLong(CATEGORY_ID_KEY, id);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getLong(CATEGORY_ID_KEY);
        }
        this.setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (Callback) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home_post_list_fragment, container, false);

        syncPostReceiver = new SyncPostReceiver();

        postAdapter = new PostAdapter();

        ListView listView = rootView.findViewById(R.id.list_item);
        listView.setEmptyView(rootView.findViewById(R.id.empty_element));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post post = (Post) parent.getAdapter().getItem(position);
                callback.onClickPost(post);
            }
        });
        viewAnimator = rootView.findViewById(R.id.main_view_animator);
        listView.setAdapter(postAdapter);

        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (categoryId != 0L) {
                    loadPostsForCollection(categoryId);
                } else {
                    refreshPosts();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dataProvider = DataProvider.getInstance(getActivity().getApplication());
        if (categoryId != 0L) {
            loadPostsForCollection(categoryId);
        } else {
            loadPosts();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SyncPostReceiver.ACTION_LOAD_POSTS);
        LocalBroadcastManager.getInstance(this.getContext())
                .registerReceiver(syncPostReceiver, intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this.getContext()).unregisterReceiver(syncPostReceiver);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.refresh:
                swipeRefreshLayout.setRefreshing(true);
                if (categoryId != 0L) {
                    loadPostsForCollection(categoryId);
                } else {
                    refreshPosts();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class SyncPostReceiver extends BroadcastReceiver {
        public static final String ACTION_LOAD_POSTS = "fr.ec.producthunt.data.action.LOAD_POSTS";

        public SyncPostReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equals(ACTION_LOAD_POSTS)) {
                loadPosts();
            }
        }
    }

    private void refreshPosts() {
        SyncService.startSyncPosts(getContext());
    }

    private void loadPosts() {
        FetchPostsAsyncTask fetchPostsAsyncTask = new FetchPostsAsyncTask();
        fetchPostsAsyncTask.execute();
    }

    private void loadPostsForCollection(long categoryId) {
        FetchPostsForCollectionAsyncTask fetchPostsForCollectionAsyncTask = new FetchPostsForCollectionAsyncTask();
        fetchPostsForCollectionAsyncTask.execute(categoryId);
    }

    private class FetchPostsAsyncTask extends AsyncTask<Void, Void, List<Post>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            viewAnimator.setDisplayedChild(PROGRESS_CHILD);
        }

        @Override
        protected List<Post> doInBackground(Void... params) {

            return dataProvider.getPostsFromDatabase();
        }

        @Override
        protected void onPostExecute(List<Post> posts) {
            if (posts != null && !posts.isEmpty()) {
                postAdapter.showPosts(posts);
            }
            swipeRefreshLayout.setRefreshing(false);
            viewAnimator.setDisplayedChild(LIST_CHILD);
        }
    }

    private class FetchPostsForCollectionAsyncTask extends AsyncTask<Long, Void, List<Post>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            viewAnimator.setDisplayedChild(PROGRESS_CHILD);
        }

        @Override
        protected List<Post> doInBackground(Long... params) {
            return dataProvider.getPostsFromWeb(params[0]);
        }

        @Override
        protected void onPostExecute(List<Post> posts) {
            if (posts != null && !posts.isEmpty()) {
                postAdapter.showPosts(posts);
            }
            swipeRefreshLayout.setRefreshing(false);
            viewAnimator.setDisplayedChild(LIST_CHILD);
        }
    }

    public interface Callback {
        void onClickPost(Post post);
    }
}
