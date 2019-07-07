package edu.cecar.syscmdb.data.model;

public  class FunctionalCI {
    private int key;
    private String class_;
    private String name;
    private String desc;
    private String org_name;
    private int org_id;

    public FunctionalCI() {
    }

    public FunctionalCI(int key, String class_, String name, String desc, String org_name, int org_id) {
        this.key = key;
        this.class_ = class_;
        this.name = name;
        this.desc = desc;
        this.org_name = org_name;
        this.org_id = org_id;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getClass_() {
        return class_;
    }

    public void setClass_(String class_) {
        this.class_ = class_;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public int getOrg_id() {
        return org_id;
    }

    public void setOrg_id(int org_id) {
        this.org_id = org_id;
    }
}
