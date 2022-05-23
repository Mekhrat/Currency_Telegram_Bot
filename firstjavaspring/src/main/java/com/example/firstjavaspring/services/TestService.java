package com.example.firstjavaspring.services;

public interface TestService {

    String getData();
    int getDataInt();
    void setData(String data,int i);
    boolean auth(String email, String password);
}
