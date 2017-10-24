package ps.wwbtraining.teacher_group2.WebService;


import java.util.concurrent.TimeUnit;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiRetrofit {

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitObject() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.SECONDS)
                .readTimeout(10000,TimeUnit.SECONDS).build();


        retrofit = new Retrofit.Builder()
                .baseUrl(URLS.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
