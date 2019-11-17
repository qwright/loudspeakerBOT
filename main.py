import time
from twitwrapper import twitwrapper

def run():
    while True:
        update()
        time.sleep(30)
        
def update():
    twit = twitwrapper()
    woeid = twit.closest_woeid(49.28, -123.12)
    top_trend = twit.top_trend(woeid)
    print("The top trend is:",top_trend)
    top_tweet = twit.top_by_location(twit.geo, top_trend)
    print("\nThe current Top Tweet in " + twit.loc_name + " is:\n" + top_tweet["text"] + "\nBy user: " + top_tweet["user"]["screen_name"])

def main():
    run()

main()
