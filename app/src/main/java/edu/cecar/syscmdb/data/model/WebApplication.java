package edu.cecar.syscmdb.data.model;

public class WebApplication extends  FunctionalCI {

    private int webServer_id;
    private String webServer_name;
    private String url;

    public WebApplication(int key, String class_, String name, String desc, String org_name, int org_id) {
        super(key, class_, name, desc, org_name, org_id);
    }
    public void setWebServer_id(int webServer_id) {
        this.webServer_id = webServer_id;
    }

    public void setWebServer_name(String webServer_name) {
        this.webServer_name = webServer_name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWebServer_id() {
        return webServer_id;
    }

    public String getWebServer_name() {
        return webServer_name;
    }

    public String getUrl() {
        return url;
    }
}
