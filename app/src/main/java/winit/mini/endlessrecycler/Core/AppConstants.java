package winit.mini.endlessrecycler.Core;

/**
 * Created by Fainir on 10/05/2016.
 */
public class AppConstants {

    public static class Http {

        public static final String SERVER_PREF = "http://ec2-52-25-156-32.us-west-2.compute.amazonaws.com:8080";
        public static final String TAB = "/tabs";
        public static final String HOT = "/Hot";
        public static final String COVER = "/lotteries/getCover/";

        //Http Calls
        public static final String GET_HOT = SERVER_PREF + TAB + HOT;
        public static final String GET_COVER = SERVER_PREF + COVER;

    }

}