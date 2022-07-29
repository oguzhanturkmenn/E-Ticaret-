package com.oguzhanturkmen.kotlineticaret.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhanturkmen.kotlineticaret.model.Product
import com.oguzhanturkmen.kotlineticaret.service.ProductAPI
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductViewModel : ViewModel() {
    private var job : Job? = null
    val productList = MutableLiveData<List<Product>>()
    val basket = MutableLiveData<List<Product>>()
    val totalBasket = MutableLiveData<Int>()

    fun downloadData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductAPI::class.java)
        //io olması arka planda olacağı anlamına gelir.
        job = viewModelScope.launch(context = Dispatchers.IO) {
            val response = retrofit.getData()

            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let {
                       productList.value = it
                    }
                }
            }
        }
    }

    fun addToBasket(product: Product){
        if (basket.value != null){
            val arrayList = ArrayList(basket.value!!)
            //contains: eğer bu listenin içine eklenmeye çalışan ürün halihazırda varsa demek.
            if (arrayList.contains(product)){
                //indexOfFirst: istediğim elemanın hangi indexte olduğunu söylüyor.
                //indexOfFirst'e tanımlanan şey mesela telefon bu dizinin içinde var
                //ve 2. indexin içinde var diye bana bunu veriyor.
                val indexOfFirst = arrayList.indexOfFirst { it == product }
                val relatedProduct = arrayList.get(indexOfFirst)
                relatedProduct.count += 1
                basket.value = arrayList

            }else{
                product.count += 1
                arrayList.add(product)
                basket.value = arrayList
            }
        }else{
            val arrayList = arrayListOf(product)
            product.count += 1
            basket.value = arrayList
        }

        basket.value.let {
            refreshTotalValue(it!!)
        }
    }



    private fun refreshTotalValue(listOfProduct:List<Product>){
        var total = 0
        //total değeri almak için önce ürün listesini alırız ve bu listeyi tek tek işlicez.
        //forEach tek tek bana ürünleri verecek.
        listOfProduct.forEach { product ->
            val price = product.price.toIntOrNull()
            price.let {
                //productan kaç tane var onu alırız
                val count = product.count
                //mesela 3 telefon var bunların getirdiği toplam ciroyu hesaplama algoritması
                val revenue = count * it!!
                //ciroyu totale eşitleriz
                total += revenue
                //total toplam değerimizi verir ve totalbasket mutablelive'e eşitleriz
            }
        }
        totalBasket.value = total
    }

    fun deleteProductFromBasket(product: Product){
        if (basket.value != null){
            val arrayList = ArrayList(basket.value!!)
            arrayList.remove(product)
            basket.value = arrayList
            refreshTotalValue(arrayList)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}