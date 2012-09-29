package ccnps.protocol;

public class Protocol{
    public static final String LIGHT_POST_PREFIX = "ccnx:/ccnps/light/post/";
    public static final String LIGHT_PUB_PREFIX = "ccnx:/ccnps/light/pub/";
    public static final String LIGHT_PREFIX = "ccnx:/ccnps/light/";

    public static final String HEAVY_POST_PREFIX = "ccnx:/ccnps/heavy/post/";
    public static final String HEAVY_PUB_PREFIX = "ccnx:/ccnps/heavy/pub/";
    public static final String HEAVY_SUB_PREFIX = "ccnx:/ccnps/heavy/sub/";
    public static final String HEAVY_PREFIX = "ccnx:/ccnps/heavy/";

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String MSG_FORMAT_ERROR = "message format error";
    public static final String PUB_NOT_EXIST = "publisher is not exist";
    public static final String SUB_ALREADY = "already subscribed";
    public static final String SERVICE_NOT_EXIST = "The requested service does not exist";
    public static final String TIME_OUT = "Time out";

    public static final int ONEDAY = 1000 * 60 * 60 * 24;
    public static final Integer MSG_TTL = 1;
}
