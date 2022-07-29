package com.oguzhanturkmen.kotlineticaret.service

import com.oguzhanturkmen.kotlineticaret.model.Product
import retrofit2.Response
import retrofit2.http.GET

interface ProductAPI {

    //https://raw.githubusercontent.com/atilsamancioglu/BTK23-DataSet/main/products.json
    //retrofit response döndürecek.
    @GET("atilsamancioglu/BTK23-DataSet/main/products.json")
    suspend fun getData() : Response<List<Product>>
}