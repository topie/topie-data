package com.topie.data.common.utils.http;

public class RequestPairBuilder {

    public static RequestPair build(String key, Object value) {
        return new RequestPair(key, value);
    }
}
