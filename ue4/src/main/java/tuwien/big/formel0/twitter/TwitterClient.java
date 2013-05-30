package tuwien.big.formel0.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class TwitterClient implements ITwitterClient
{
    @Override
    public void publishUuid(TwitterStatusMessage message) throws Exception {
        // credentials are in twitter4j.properties
        // see http://twitter4j.org/en/configuration.html
        // and http://twitter4j.org/en/code-examples.html
        Twitter twitter = TwitterFactory.getSingleton();
        twitter.updateStatus(message.getTwitterPublicationString());
    }
}
