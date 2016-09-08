/**
 * Definition of Tweet:
 * public class Tweet {
 *     public int id;
 *     public int user_id;
 *     public String text;
 *     public static Tweet create(int user_id, String tweet_text) {
 *         // This will create a new tweet object,
 *         // and auto fill id
 *     }
 * }
 */
public class MiniTwitter {
    private int order;
    private HashMap<Integer, Set<Integer>> friends;
    private HashMap<Integer, List<Node>> userTweet;
    
    class Node implements Comparable<Node> {
        int order;
        Tweet tweet;
        Node(int order, Tweet tweet) {
            this.order = order;
            this.tweet = tweet;
        }
        public int compareTo(Node node2) {
            return node2.order - this.order;
        }
    }
    
    public MiniTwitter() {
        // initialize your data structure here.
        order = 0;
        friends = new HashMap<>();
        userTweet = new HashMap<>();
    }

    // @param user_id an integer
    // @param tweet a string
    // return a tweet
    public Tweet postTweet(int user_id, String tweet_text) {
        if (!userTweet.containsKey(user_id)) {
            userTweet.put(user_id, new ArrayList<Node>());
        }
        Tweet tweet = Tweet.create(user_id, tweet_text);
        Node node = new Node(order, tweet);
        order++;
        userTweet.get(user_id).add(node);
        return tweet;
    }

    // @param user_id an integer
    // return a list of 10 new feeds recently
    // and sort by timeline
    public List<Tweet> getNewsFeed(int user_id) {
        List<Node> result = new ArrayList<Node>();
        if (userTweet.containsKey(user_id)) {
            result.addAll(getLast10Tweets(userTweet.get(user_id)));
        }
        if (friends.containsKey(user_id)) {
            for (int friendId : friends.get(user_id)) {
                if (userTweet.containsKey(friendId)) {
                    result.addAll(getLast10Tweets(userTweet.get(friendId)));
                }
            }
        }
        
        Collections.sort(result);
        result = getFirst10Tweets(result);
        List<Tweet> tweets = new ArrayList<>();
        for (Node node : result) {
            tweets.add(node.tweet);
        }
        return tweets;
    }
        
    // @param user_id an integer
    // return a list of 10 new posts recently
    // and sort by timeline
    public List<Tweet>  getTimeline(int user_id) {
        List<Node> result = new ArrayList<Node>();
        if (userTweet.containsKey(user_id)) {
            result.addAll(getLast10Tweets(userTweet.get(user_id)));
        }
        Collections.sort(result);
        result = getFirst10Tweets(result);
        List<Tweet> tweets = new ArrayList<>();
        for (Node node : result) {
            tweets.add(node.tweet);
        }
        return tweets;
    }
    
    List<Node> getFirst10Tweets(List<Node> list) {
        int last = 10;
        if (list.size() < last) {
            last = list.size();
        }
        return list.subList(0, last); 
    }
    
    List<Node> getLast10Tweets(List<Node> list) {
        int last = 10;
        if (list.size() < last) {
            last = list.size();
        }
        return list.subList(list.size() - last, list.size());
    }
    
    // @param from_user_id an integer
    // @param to_user_id an integer
    // from user_id follows to_user_id
    public void follow(int from_user_id, int to_user_id) {
        if (!friends.containsKey(from_user_id)) {
            friends.put(from_user_id, new HashSet<Integer>());
        }
        friends.get(from_user_id).add(to_user_id);
    }

    // @param from_user_id an integer
    // @param to_user_id an integer
    // from user_id unfollows to_user_id
    public void unfollow(int from_user_id, int to_user_id) {
        if (friends.containsKey(from_user_id)) {
            friends.get(from_user_id).remove(to_user_id);
        }
    }
}