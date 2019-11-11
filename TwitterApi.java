import twitter4j.GeoLocation;
import twitter4j.Location;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterApi {
	private Constants c;
	private ConfigurationBuilder cb;
	private TwitterFactory tf;
	private Twitter twit;

	public TwitterApi()
	{
		c = new Constants();
		cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey(c.getApiKey())
		.setOAuthConsumerSecret(c.getApiSecret())
		.setOAuthAccessToken(c.getATok())
		.setOAuthAccessTokenSecret(c.getATokSecret());

		tf = new TwitterFactory(cb.build());
		twit = tf.getInstance();

	}

	public Trends trendsByLoc(int WOEID) throws TwitterException 
	{
		return twit.getPlaceTrends(WOEID);
	}

	public void getTrendVol(Trends t)
	{
		//TODO Make a hashmap of volume and tweet
		//Trend[] trend = t.getTrends();
		//for(Trend tr : trend) {
		//	System.out.println(tr.getTweetVolume());
		//}
	}

	public void printTrends(Trends t) 
	{
		Trend[] trend = t.getTrends();
		for(Trend tr : trend) {
			if(tr.getTweetVolume() != -1) {
				System.out.println("Trend: " + tr.getName() + " Volume: " + tr.getTweetVolume());

			}
		}
	}

	public String getTopTrendQuery(Trends t) 
	{
		String topTrend = "";
		int top = -2;
		Trend[] trend = t.getTrends();
		for(Trend tr : trend) {
			int curr = tr.getTweetVolume();
			if(curr == -1)	{continue;}
			if(curr > top) {
				top = curr;
				topTrend = tr.getQuery();
			}
		}
		return topTrend;
	}

	public Query seekQuery(String query, GeoLocation geo) 
	{
		Query q = new Query().geoCode(geo, 100.00, Query.KILOMETERS);;
		return q.query(query);
	}

	public QueryResult queryResult(Query query) throws TwitterException 
	{
		return twit.search(query);
	}

	public void filterQuery(QueryResult queryResult, GeoLocation geo)
	{
		for(Status s : queryResult.getTweets()) {
			System.out.println("@" + s.getUser().getScreenName() + ": " + s.getText());
		}
	}

	public GeoLocation getGeo(double latitude, double longitude)
	{
		return new GeoLocation(latitude, longitude);
	}


	public ResponseList<Location> getClosestLoc(GeoLocation geo) throws TwitterException
	{

		return twit.getClosestTrends(geo);
		//returns array of WOEID for near trends to geoLoc
	}
}
