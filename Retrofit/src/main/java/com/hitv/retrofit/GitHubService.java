package com.hitv.retrofit;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Kiven on 2017/10/31.
 * Details:
 */
public interface GitHubService {
    @GET("ms-app/{path}")
    Call<List<ResponseBody>> listRepos(@Path("path") String path);
}
