package com.codepath.apps.nftweetsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.nftweetsapp.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by najmeh.f on 12/13/2015.
 */
public class TweetsArrayAdaptor extends ArrayAdapter<Tweet>{
    private static class ViewHolder {
        TextView tvUserName;
        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvDate;
    }

    public TweetsArrayAdaptor(Context context, List<Tweet> tweets ){
        super(context, 0, tweets);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Tweet tweet= getItem( position);
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
            viewHolder.ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
            viewHolder.tvUserName=(TextView) convertView.findViewById(R.id.tvUserName);
            viewHolder.tvBody=(TextView) convertView.findViewById(R.id.tvBody);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();}

            //ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
            //TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
            //TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
            viewHolder.tvUserName.setText(tweet.getUser().getScreenName());
            viewHolder.tvDate.setText(TwitterClient.getRelativeTimeAgo(tweet.getCreatedAt()));
            viewHolder.tvBody.setText(tweet.getBody());
            //ivProfileImage.setImageResource(android.R.color.transparent);
            Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(viewHolder.ivProfileImage);
            return convertView;

    }
}
