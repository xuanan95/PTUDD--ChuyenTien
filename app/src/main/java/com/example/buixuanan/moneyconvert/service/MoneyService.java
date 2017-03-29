package com.example.buixuanan.moneyconvert.service;

import com.example.buixuanan.moneyconvert.dto.MoneyDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface MoneyService {
    @GET("/api/live")
    Call<MoneyDTO> getListMoney(@Query("access_key")String access_key, @Query("currencies") String currencies);
}
