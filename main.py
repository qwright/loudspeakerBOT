import tweepy
import time
from twitwrapper import twitwrapper

def run():
    while True:
        update()
        time.sleep(300)
        
def update():
    try:
        twit = twitwrapper()
        woeid = twit.closest_woeid(49.28, -123.12)
        top_trend = twit.top_trend(woeid)
        top_tweet = twit.top_by_location(twit.geo, top_trend)
        twit.retweet_top(top_tweet["id"])
        print("The top trend is:",top_trend)
        print("\nThe current Top Tweet in " + twit.loc_name + " is:\n" + top_tweet["text"] + "\nBy user: " + top_tweet["user"]["screen_name"])
    
    except tweepy.TweepError:
        print(TweepError)


def main():
    run()

main()
