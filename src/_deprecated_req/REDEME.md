```
String KEY_COOKIE = "Set-Cookie";
String KEY_SESSION = "JSESSIONID"
new OkHttpClient.Builder()
    .cookieJar(new CookieJar() {
        @Override public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {}
        @Override public List<Cookie> loadForRequest(HttpUrl url) { return null; }
    })
```
