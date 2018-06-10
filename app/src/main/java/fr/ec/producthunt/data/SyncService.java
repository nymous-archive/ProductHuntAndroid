package fr.ec.producthunt.data;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import static android.content.ContentValues.TAG;
import static fr.ec.producthunt.ui.collections.CollectionsFragments.SyncCollectionReceiver.ACTION_LOAD_COLLECTIONS;
import static fr.ec.producthunt.ui.home.PostsFragments.SyncPostReceiver.ACTION_LOAD_POSTS;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class SyncService extends IntentService {
    private static final String ACTION_FETCH_NEW_POSTS = "fr.ec.producthunt.data.action.FETCH_NEW_POSTS";
    private static final String ACTION_FETCH_NEW_COLLECTIONS = "fr.ec.producthunt.data.action.FETCH_NEW_COLLECTIONS";

    public SyncService() {
        super("SyncService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startSyncPosts(Context context) {
        Intent intent = new Intent(context, SyncService.class);
        intent.setAction(ACTION_FETCH_NEW_POSTS);
        context.startService(intent);
    }

    public static void startSyncCollections(Context context) {
        Intent intent = new Intent(context, SyncService.class);
        intent.setAction(ACTION_FETCH_NEW_COLLECTIONS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FETCH_NEW_POSTS.equals(action)) {
                handleActionFetchNewPosts();
                Log.d(TAG, "handleActionFetchNewPosts is Ok");
            } else if (ACTION_FETCH_NEW_COLLECTIONS.equals(action)) {
                handleActionFetchNewCollections();
                Log.d(TAG, "handleActionFetchNewCollections is Ok");
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFetchNewPosts() {
        DataProvider.getInstance(this.getApplication()).syncPost();
        Intent intentToSend = new Intent();
        intentToSend.setAction(ACTION_LOAD_POSTS);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intentToSend);
    }

    private void handleActionFetchNewCollections() {
        DataProvider.getInstance(this.getApplication()).syncCollection();
        Intent intentToSend = new Intent();
        intentToSend.setAction(ACTION_LOAD_COLLECTIONS);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intentToSend);
    }
}
