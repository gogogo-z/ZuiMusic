package somebody_z.me.zuimusic.utils.http;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import somebody_z.me.zuimusic.BuildConfig;
import somebody_z.me.zuimusic.MyApplication;

/**
 * retrofit
 *
 * @author HuanxingZeng
 * @version 创建时间：2016年12月5日 上午11:14:07
 */
public class ServiceFactory {

    private File cacheFile = new File(MyApplication.getContext().getExternalCacheDir(), "ZuiMusic");
    private Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);

    public ServiceFactory() {

    }

    private static class SingletonHolder {

        private static final ServiceFactory INSTANCE = new ServiceFactory();
    }

    public static ServiceFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public <T> T createService(Class<T> serviceClass) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        return retrofit.create(serviceClass);
    }

    // 设置超时时间
    private final static long DEFAULT_TIMEOUT = 30;

    private OkHttpClient getOkHttpClient() {
        // 定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        //添加公共参数
        httpClientBuilder.addInterceptor(new QueryParameterInterceptor());
        //Log信息拦截器
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(loggingInterceptor);
        }

        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                                // .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36")
                        .addHeader("User-Agent", "android_5.9.9.3;baiduyinyue")
                                //.addHeader("Cache-Control", "max-age=0")
                        .build();
                return chain.proceed(request);
            }
        });

        //缓存机制
        httpClientBuilder.cache(cache).addInterceptor(new CacheInterceptor());

        // 设置超时时间
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        return httpClientBuilder.build();
    }
}
