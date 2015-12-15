package com.codepath.apps.nftweetsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.nftweetsapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class composeActivity extends ActionBarActivity {
    private ImageView ivUserProfilePic;
    private TextView tvScreenName;
    private EditText etCompose;
    private TextView tvFirstName;
    private TextView tvChar;
    private TextWatcher mTextEditorWatcher;
    private TwitterClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        getSupportActionBar().setTitle("Compose");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_twitter);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        client=TwitterApplication.getRestClient();
        ivUserProfilePic = (ImageView) findViewById(R.id.ivUserProfilePic);
        tvFirstName = (TextView) findViewById(R.id.tvFirstName);
        tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        etCompose = (EditText) findViewById(R.id.etCompose);
        tvChar = (TextView) findViewById(R.id.tvChar);
        updateUserName();
        tvChar.setText("140");
        final Button button = (Button) findViewById(R.id.post);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // Intent data = new Intent();
                //data.putExtra("name", input.getText().toString());
                //data.putExtra("position", position); // ints work too
                // Activity finished ok, return the data
                //setResult(RESULT_OK, data); // set result code and bundle data for response
               // finish(); // closes the act
                postToTwitter();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id ==2) {
            postToTwitter();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void updateUserName() {
        client.getUserCredentials(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                User auth_user = User.fromJSON(response);
                tvScreenName.setText("@" + auth_user.getScreenName());
                tvFirstName.setText(auth_user.getName().toString());
                Picasso.with(getBaseContext()).load(auth_user.getProfileImageUrl()).into(ivUserProfilePic);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getBaseContext(), "no user", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postToTwitter() {
        String tweet = etCompose.getText().toString();
        client.postTweet(tweet, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(getBaseContext(), "tweet posted", Toast.LENGTH_SHORT).show();
                String tweet = etCompose.getText().toString();
                Intent data = new Intent();
                data.putExtra("tweet", tweet);

                setResult(RESULT_OK, data);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

}
