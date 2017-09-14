package com.nepalicoders.retrofitdemo;

/**
 * Created by Sulav on 8/12/17.
 */

class User {

    private Integer id;
    private String name;
    private String email;
    private int age;
    private String[] topics;

    public User(String name, String email, int age, String[] topics) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.topics = topics;
    }

    public Integer getId() {
        return id;
    }
}
