package mg.itu.m1p10android.data.http;

import mg.itu.m1p10android.BuildConfig;

public enum ApiURL {
    BASE_URL(BuildConfig.ApiUrl),
    BASE_AUTHORITY(BuildConfig.ApiAuthority);

    public final String value;

    ApiURL(String value) {
        this.value = value;
    }
}
