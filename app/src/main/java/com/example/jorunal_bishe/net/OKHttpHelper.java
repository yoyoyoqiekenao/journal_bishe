package com.example.jorunal_bishe.net;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author : 徐无敌
 * date   : 2021/4/2214:46
 * desc   :
 */
public class OKHttpHelper {
    private static final String WFC_OKHTTP_COOKIE_CONFIG = "WFC_OK_HTTP_COOKIES";
    private static final Map<String, List<Cookie>> cookieStore = new ConcurrentHashMap<>();
    private static WeakReference<Context> AppContext;
    private static Gson gson = new Gson();

    public static void init(Context context) {
        AppContext = new WeakReference<>(context);
    }

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .cookieJar(new CookieJar() {
                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    cookieStore.put(url.host(), cookies);
                    if (AppContext != null && AppContext.get() != null) {
                        SharedPreferences sharedPreferences = AppContext.get().getSharedPreferences(WFC_OKHTTP_COOKIE_CONFIG, 0);
                        Set<String> set = new HashSet<>();
                        for (Cookie k : cookies) {
                            set.add(gson.toJson(k));
                        }
                        sharedPreferences.edit().putStringSet(url.host(), set).apply();
                    }
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> cookies = cookieStore.get(url.host());
                    if (cookies == null) {
                        if (AppContext != null && AppContext.get() != null) {
                            SharedPreferences sp = AppContext.get().getSharedPreferences(WFC_OKHTTP_COOKIE_CONFIG, 0);
                            Set<String> set = sp.getStringSet(url.host(), new HashSet<>());
                            cookies = new ArrayList<>();
                            for (String s : set) {
                                Cookie cookie = gson.fromJson(s, Cookie.class);
                                cookies.add(cookie);
                            }
                            cookieStore.put(url.host(), cookies);
                        }
                    }
                    return cookies;
                }
            })
            .build();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static <T> void post(final String url, Map<String, String> param, final Callback<T> callback) {
        RequestBody body = RequestBody.create(JSON, gson.toJson(param));
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(-1, e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handleResponse(url, call, response, callback);

            }
        });
    }

    private static <T> void handleResponse(String url, Call call, Response response, Callback<T> callback) {
        if (callback != null) {
            if (!response.isSuccessful()) {
                callback.onFailure(response.code(), response.message());
                return;
            }
            Type type;
            if (callback instanceof SimpleCallback) {
                Type types = callback.getClass().getGenericSuperclass();
                type = ((ParameterizedType) types).getActualTypeArguments()[0];
            } else {
                Type[] types = callback.getClass().getGenericInterfaces();
                type = ((ParameterizedType) types[0]).getActualTypeArguments()[0];
            }
            if (type.equals(Void.class)) {
                callback.onSuccess(null);
                return;
            }
            if (type.equals(String.class)) {
                try {
                    callback.onSuccess((T) response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }


            try {
                StatusResult statusResult;
                if (type instanceof Class && type.equals(StatusResult.class)) {
                    statusResult = gson.fromJson(response.body().string(), StatusResult.class);
                    if (statusResult.isSuccess()) {
                        callback.onSuccess((T) statusResult);
                    } else {
                        callback.onFailure(statusResult.getCode(), statusResult.getMessage());
                    }
                } else {
                    ResultWrapper<T> wrapper = gson.fromJson(response.body().string(), new ResultType(type));
                    if (wrapper == null) {
                        callback.onFailure(-1, "response is null");
                        return;
                    }
                    if (wrapper.isSuccess() && wrapper.getResult() != null) {
                        callback.onSuccess(wrapper.getResult());
                    } else {
                        callback.onFailure(wrapper.getCode(), wrapper.getMessage());
                    }
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                callback.onFailure(-1, e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                callback.onFailure(-1, e.getMessage());
            }
        }
    }

    private static class ResultType implements ParameterizedType {
        private final Type type;

        public ResultType(Type type) {
            this.type = type;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{type};
        }

        @Override
        public Type getOwnerType() {
            return null;
        }

        @Override
        public Type getRawType() {
            return ResultWrapper.class;
        }
    }
}
