import twitter4j.Location;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Trends;
import twitter4j.TwitterException;

public class Application {
	public static void main(String[] args){
		Application app = new Application();
		app.run();
	}
	
	public void update() 
	{
		TwitterApi twitter = new TwitterApi();
		try {
			ResponseList<Location> t = twitter.getClosestLoc(49.25, -123.119);
			Location closest = t.get(0);
			int woeid = closest.getWoeid();
			Trends trend = twitter.trendsByLoc(woeid); // vancouver 9807 // global 1// 
			twitter.printTrends(trend);
			String query = twitter.getTopTrendQuery(trend);
			QueryResult result = twitter.queryResult(twitter.seekQuery(query));
			for(Status s : result.getTweets()) {
				System.out.println("@" + s.getUser().getScreenName() + ": " + s.getText() );
			}
	
		} catch (TwitterException e) {
			System.out.println("Error: "  + e + "occured at this location");
			e.printStackTrace();
		}
	}
	
	public synchronized void run()
	{
		while(true) {
			this.update();
			try {
				this.wait(28800000);//8 hours
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

