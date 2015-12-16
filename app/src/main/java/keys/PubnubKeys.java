package keys;

/**
 * Created by Anjith Sasindran
 * on 11-Oct-15.
 */
public class PubnubKeys {

    public static final String PUBLISH_KEY = "pub-c-6cbf37fd-d99d-4693-811c-529aa00b2f9c";
    public static final String SUBSCRIBE_KEY = "sub-c-184bdf0a-a099-11e5-b829-02ee2ddab7fe";
    public static String CHANNEL_NAME;

    public PubnubKeys(String host){
      CHANNEL_NAME = host;
    }


}

