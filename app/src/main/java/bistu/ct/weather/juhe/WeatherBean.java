package bistu.ct.weather.juhe;

import java.io.Serializable;
import java.util.List;

/**
 * 聚合平台接口提供最近5天的情况
 */

public class WeatherBean implements Serializable {


    /**
     * reason : 查询成功!
     * result : {"city":"北京","realtime":{"temperature":"5","humidity":"77","info":"小雨","wid":"07","direct":"北风","power":"5级","aqi":"37"},"future":[{"date":"2021-11-06","temperature":"-1/13℃","weather":"中雨转大雪","wid":{"day":"08","night":"16"},"direct":"北风"},{"date":"2021-11-07","temperature":"-4/2℃","weather":"中雪转多云","wid":{"day":"15","night":"01"},"direct":"北风转西北风"},{"date":"2021-11-08","temperature":"-3/4℃","weather":"多云转晴","wid":{"day":"01","night":"00"},"direct":"西风"},{"date":"2021-11-09","temperature":"-2/5℃","weather":"晴","wid":{"day":"00","night":"00"},"direct":"西风"},{"date":"2021-11-10","temperature":"0/9℃","weather":"晴","wid":{"day":"00","night":"00"},"direct":"西北风"}]}
     * error_code : 0
     */

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean implements Serializable {
        /**
         * city : 北京
         * realtime : {"temperature":"5","humidity":"77","info":"小雨","wid":"07","direct":"北风","power":"5级","aqi":"37"}
         * future : [{"date":"2021-11-06","temperature":"-1/13℃","weather":"中雨转大雪","wid":{"day":"08","night":"16"},"direct":"北风"},{"date":"2021-11-07","temperature":"-4/2℃","weather":"中雪转多云","wid":{"day":"15","night":"01"},"direct":"北风转西北风"},{"date":"2021-11-08","temperature":"-3/4℃","weather":"多云转晴","wid":{"day":"01","night":"00"},"direct":"西风"},{"date":"2021-11-09","temperature":"-2/5℃","weather":"晴","wid":{"day":"00","night":"00"},"direct":"西风"},{"date":"2021-11-10","temperature":"0/9℃","weather":"晴","wid":{"day":"00","night":"00"},"direct":"西北风"}]
         */

        private String city;
        private RealtimeBean realtime;
        private List<FutureBean> future;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public RealtimeBean getRealtime() {
            return realtime;
        }

        public void setRealtime(RealtimeBean realtime) {
            this.realtime = realtime;
        }

        public List<FutureBean> getFuture() {
            return future;
        }

        public void setFuture(List<FutureBean> future) {
            this.future = future;
        }

        public static class RealtimeBean implements Serializable {
            /**
             * temperature : 5
             * humidity : 77
             * info : 小雨
             * wid : 07
             * direct : 北风
             * power : 5级
             * aqi : 37
             */

            private String temperature;
            private String humidity;
            private String info;
            private String wid;
            private String direct;
            private String power;
            private String aqi;

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public String getWid() {
                return wid;
            }

            public void setWid(String wid) {
                this.wid = wid;
            }

            public String getDirect() {
                return direct;
            }

            public void setDirect(String direct) {
                this.direct = direct;
            }

            public String getPower() {
                return power;
            }

            public void setPower(String power) {
                this.power = power;
            }

            public String getAqi() {
                return aqi;
            }

            public void setAqi(String aqi) {
                this.aqi = aqi;
            }
        }


        public static class FutureBean implements Serializable {
            /**
             * date : 2021-11-06
             * temperature : -1/13℃
             * weather : 中雨转大雪
             * wid : {"day":"08","night":"16"}
             * direct : 北风
             */

            private String date;
            private String temperature;
            private String weather;
            private WidBean wid;
            private String direct;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public WidBean getWid() {
                return wid;
            }

            public void setWid(WidBean wid) {
                this.wid = wid;
            }

            public String getDirect() {
                return direct;
            }

            public void setDirect(String direct) {
                this.direct = direct;
            }

            public static class WidBean implements Serializable {
                /**
                 * day : 08
                 * night : 16
                 */

                private String day;
                private String night;
            }

        }
    }
}
