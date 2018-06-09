package fr.ec.producthunt.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import fr.ec.producthunt.R;
import fr.ec.producthunt.data.model.Post;
import java.util.Collections;
import java.util.List;

public class PostAdapter extends BaseAdapter {

  private List<Post> dataSource = Collections.emptyList();

  public PostAdapter() {
  }

  @Override public int getCount() {
    return dataSource.size();
  }

  @Override public Object getItem(int position) {
    return dataSource.get(position);
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {

    ViewHolder viewHolder;

    if (convertView == null) {
      convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

      viewHolder = new ViewHolder();
      viewHolder.title = convertView.findViewById(R.id.title);
      viewHolder.subTitle = convertView.findViewById(R.id.sub_title);
      viewHolder.postImage = convertView.findViewById(R.id.img_product);

      convertView.setTag(viewHolder);
    } else {

      viewHolder = (ViewHolder) convertView.getTag();
    }

    Post post = dataSource.get(position);
    viewHolder.title.setText(post.getTitle());
    viewHolder.subTitle.setText(post.getSubTitle());


    Picasso.with(parent.getContext())
        .load(post.getImageUrl())
        .centerCrop()
        .fit()
        .into(viewHolder.postImage);

    return convertView;
  }

  public void showPosts(List<Post> posts) {
    dataSource = posts;

    notifyDataSetChanged();
  }

  private static class ViewHolder {
    TextView title;
    TextView subTitle;
    ImageView postImage;
  }
}
