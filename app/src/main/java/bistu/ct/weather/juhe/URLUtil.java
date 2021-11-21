package bistu.ct.weather.juhe;

public class URLUtil {
    public static final String KEY="7af26deec3660b9fca08cd489345f04a";
    public  static String tem_url="http://apis.juhe.cn/simpleWeather/query";
    public static String getTem_url(String city){
        String url=tem_url+"?city="+city+"&key="+KEY;
        return url;
    }



}
