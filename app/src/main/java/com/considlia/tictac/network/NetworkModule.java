package com.considlia.tictac.network;

import android.os.AsyncTask;

import com.considlia.tictac.utils.TicTacService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.openapitools.client.ApiClient;
import org.openapitools.client.api.ActivitiesApiApi;
import org.openapitools.client.api.ProjectsApiApi;
import org.openapitools.client.api.TimelogsApiApi;
import org.openapitools.client.api.UsersApiApi;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

public class NetworkModule {
    UsersApiApi userApi;
    ProjectsApiApi projectApi;
    ActivitiesApiApi activitiesApi;
    TimelogsApiApi timeLogApi;
    Retrofit retrofit;
    ApiClient apiClient;
    TicTacService ticTacService;

    private static NetworkModule INSTANCE;

    public NetworkModule() {
        apiClient = new ApiClient();
        retrofit = apiClient.getAdapterBuilder()
                .baseUrl("http://10.0.2.2:8080")
                .build();
        userApi = retrofit.create(UsersApiApi.class);
        projectApi = retrofit.create(ProjectsApiApi.class);
        activitiesApi = retrofit.create(ActivitiesApiApi.class);
        timeLogApi = retrofit.create(TimelogsApiApi.class);
    }

    public synchronized static NetworkModule getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NetworkModule();
        }

        return INSTANCE;
    }

    public TicTacService getService() {
        if (ticTacService == null) {
            final Call call = retrofit.callFactory().newCall(new Request.Builder()
                    .get()
                    .url(retrofit.baseUrl() + "actuator/health").build());

            class CheckHealthTask extends AsyncTask<Void, Void, Boolean> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected Boolean doInBackground(Void... voids) {
                    boolean result = false;
                    try (Response response = call.execute()) {
                        if (response.isSuccessful()) {
                            String springResponse = response.body().string();
                            Gson gson = new Gson();
                            JsonObject jsonObject = gson.fromJson(springResponse, JsonObject.class);
                            result = (jsonObject.get("status").getAsString().equals("UP"));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return result;
                }
            }

            boolean result = false;
            CheckHealthTask checkHealthtask = new CheckHealthTask();
            try {
                result = checkHealthtask.execute().get(2500, TimeUnit.MILLISECONDS);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            ticTacService = result ? new RestService(userApi, projectApi, activitiesApi, timeLogApi) : new FakeService();
        }
        return ticTacService;
    }
}
