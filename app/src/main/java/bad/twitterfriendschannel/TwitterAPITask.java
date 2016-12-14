package bad.twitterfriendschannel;

import android.os.AsyncTask;
import android.util.Log;
import java.util.*;
import twitter4j.TwitterFactory;
import twitter4j.IDs;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterAPITask extends AsyncTask<String, String, List<Long>> {

    public enum ResponseResult {SUCCESS, FAILURE};
    private static final String TAG = "TwitterAPICallActivity";
    private static final String TWITTER_KEY = "";
    private static final String TWITTER_SECRET = "";
    private static final String TWITTER_USER_KEY = "";
    private static final String TWITTER_USER_SECRET = "";

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected List<Long> doInBackground(String...params) {
        return postCall(params);


    }
    private List<Long> postCall (String...params){
        TwitterAPITaskEnum task = TwitterAPITaskEnum.valueOf(params[0]);
        String token = params[1];
        String secret = params[2];
        long userId = Long.valueOf(params[3]);
        List<Long> ids = new ArrayList<Long>();

        if (task.equals(TwitterAPITaskEnum.CREATE_FRIENDSHIP)){
            ConfigurationBuilder cb = createConfigurationBuilderForFriendship();
            ResponseResult result = createFriendship(userId, cb);
        }
        else if (task.equals(TwitterAPITaskEnum.GET_FRIENDS)){
            ConfigurationBuilder cb = createConfigurationBuilder(token, secret);
            ResponseResult result = getFriends(userId, cb, ids);

        }
        else if (task.equals(TwitterAPITaskEnum.GET_FOLLOWERS)){
            ConfigurationBuilder cb = createConfigurationBuilder(token, secret);
            ResponseResult result = getFollowers(userId, cb, ids);
        }
        return ids;
    }
    private ResponseResult getFollowers (long userId, ConfigurationBuilder cb, List<Long> followersIDs){

        TwitterFactory twitterFactory = new TwitterFactory(cb.build());
        twitter4j.Twitter twitter = twitterFactory.getInstance();
        IDs ids;
        long cursor = -1L;
        do {
            try {
                ids = twitter.getFollowersIDs(cursor);
                for(long userID : ids.getIDs()){
                    followersIDs.add(userID);
                }
            }
            catch(twitter4j.TwitterException ex){
                Log.d(TAG, "Exception: "+ ex.getErrorMessage());
                return ResponseResult.FAILURE;

            }
        } while((cursor = ids.getNextCursor())!=0 );

        return ResponseResult.SUCCESS;
    }
    private ResponseResult getFriends (long userId, ConfigurationBuilder cb, List<Long> friendIDs){

        TwitterFactory twitterFactory = new TwitterFactory(cb.build());
        twitter4j.Twitter twitter = twitterFactory.getInstance();
        IDs ids;
        long cursor = -1L;
        do {
            try {
                ids = twitter.getFriendsIDs(cursor);
                for(long userID : ids.getIDs()){
                    friendIDs.add(userID);
                }
            }
            catch(twitter4j.TwitterException ex){
                Log.d(TAG, "Exception: "+ ex.getErrorMessage());
                return ResponseResult.FAILURE;

            }
        } while((cursor = ids.getNextCursor())!=0 );

        return ResponseResult.SUCCESS;
    }
    private ResponseResult createFriendship (long userId, ConfigurationBuilder cb){

        TwitterFactory twitterFactory = new TwitterFactory(cb.build());
        twitter4j.Twitter twitter = twitterFactory.getInstance();
        try {
            twitter.createFriendship(userId);
        }
        catch(twitter4j.TwitterException ex){
            Log.d(TAG, "Exception: "+ ex.getErrorMessage());
             return ResponseResult.FAILURE;

        }
        return ResponseResult.SUCCESS;
    }
    private ConfigurationBuilder createConfigurationBuilder(String token, String secret){
        Log.d(TAG, token);
        Log.d(TAG, secret);
        ConfigurationBuilder cb =new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(TWITTER_KEY)
                .setOAuthConsumerSecret(TWITTER_SECRET)
                .setOAuthAccessToken(token)
                .setOAuthAccessTokenSecret(secret);

        return cb;

    }
    private ConfigurationBuilder createConfigurationBuilderForFriendship(){

        ConfigurationBuilder cb =new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(TWITTER_KEY)
                .setOAuthConsumerSecret(TWITTER_SECRET)
                .setOAuthAccessToken(TWITTER_USER_KEY)
                .setOAuthAccessTokenSecret(TWITTER_USER_SECRET);

        return cb;

    }

}
