package mg.itu.m1p10android.data.http;

import mg.itu.m1p10android.BuildConfig;

public enum ApiURL {
    BASE_URL(BuildConfig.ApiUrl),
    ARTICLE_URL(BASE_URL.value+"/article");

    public final String value;

    ApiURL(String value) {
        this.value = value;
    }
}
