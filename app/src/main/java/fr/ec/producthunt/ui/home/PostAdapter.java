package fr.ec.producthunt.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.ec.producthunt.R;
import fr.ec.producthunt.data.model.Post;

public class PostAdapter extends BaseAdapter {

    public static final int VIEW_BIG_ITEM = 0;
    public static final int VIEW_NORMAL_ITEM = 1;

    private List<Post> dataSource = Collections.emptyList();

    public PostAdapter() {
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        int viewType = getItemViewType(position);
        if (viewType == VIEW_BIG_ITEM) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.big_item, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.title = convertView.findViewById(R.id.title);
                viewHolder.subTitle = convertView.findViewById(R.id.sub_title);
                viewHolder.postImage = convertView.findViewById(R.id.img_product);
                viewHolder.postDate = convertView.findViewById(R.id.post_date);
                viewHolder.commentsCount = convertView.findViewById(R.id.comments_count);

                convertView.setTag(viewHolder);
            } else {

                viewHolder = (ViewHolder) convertView.getTag();
            }
        } else {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.title = convertView.findViewById(R.id.title);
                viewHolder.subTitle = convertView.findViewById(R.id.sub_title);
                viewHolder.postImage = convertView.findViewById(R.id.img_product);
                viewHolder.postDate = convertView.findViewById(R.id.post_date);
                viewHolder.commentsCount = convertView.findViewById(R.id.comments_count);

                convertView.setTag(viewHolder);
            } else {

                viewHolder = (ViewHolder) convertView.getTag();
            }
        }

        Post post = dataSource.get(position);
        viewHolder.title.setText(post.getTitle());
        viewHolder.subTitle.setText(post.getSubTitle());
        Date postDate = new Date(post.getPostDate());
        viewHolder.postDate.setText(DateFormat.getDateTimeInstance().format(postDate));
        viewHolder.commentsCount.setText(String.format(Locale.US, "%d comments", post.getCommentsCount()));


        Picasso.with(parent.getContext())
                .load(post.getImageUrl())
                .centerCrop()
                .fit()
                .into(viewHolder.postImage);

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_BIG_ITEM;
        } else {
            return VIEW_NORMAL_ITEM;
        }
    }

    public void showPosts(List<Post> posts) {
        dataSource = posts;

        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView title;
        TextView subTitle;
        ImageView postImage;
        TextView postDate;
        TextView commentsCount;
    }
}
