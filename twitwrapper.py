import tweepy
import constants as c

class twitwrapper:

    geo = ""
    loc_name = ""

    def __init__(self):
        _auth = tweepy.OAuthHandler(c.API_KEY, c.API_SECRET)
        _auth.set_access_token(c.ACCESS_TOKEN, c.ACCESS_SECRET)
        #use jsonparser
        self._api = tweepy.API(_auth, parser=tweepy.parsers.JSONParser())
    
    def closest_woeid(self, latitude, longitude):
        closestLocation = self._api.trends_closest(latitude, longitude)
        self.geo = "{},{},100km".format(latitude, longitude)
        loc = closestLocation[0]
        self.loc_name = loc["name"]
        return loc["woeid"]

    def top_trend(self, woeid):
        trending = self._api.trends_place(woeid)
        top = -2
        for trend in trending[0].get("trends"):
            #print("{} and {}".format(trend.get("name"), trend.get("tweet_volume")))
            if trend.get("promoted_content"):
                continue
            if trend.get("tweet_volume") is None:
                continue
            if trend.get("tweet_volume") > top:
                top = trend.get("tweet_volume")
                top_trend = trend.get("name")
        return top_trend

    def top_by_location(self, geo, trend):
        top = -2
        top_tweet = None
        results = (self._api.search(q=trend, geocode=geo, count=100, lang='en'))
        #results returns a dictionary with "search_metadata" and "statuses" if using jsonparser
        for r in results["statuses"]:
            tot_count = r["retweet_count"] + r["favorite_count"]
            if tot_count > top and self.loc_name in r["user"]["location"]:
                top = tot_count
                top_tweet = r
        return r
