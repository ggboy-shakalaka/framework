package cn.zhaizq.sso.sdk.domain;

public enum SsoApiEnum {
    query_config("/api/query_config"),
    check_token("/api/check_token"),
    ;

    private String path;
    SsoApiEnum(String path) {
        this.path = path;
    }

    public String getUrl(String server) {
        return server + path;
    }
}