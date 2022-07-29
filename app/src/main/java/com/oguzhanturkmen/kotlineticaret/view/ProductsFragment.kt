package com.oguzhanturkmen.kotlineticaret.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.oguzhanturkmen.kotlineticaret.R
import com.oguzhanturkmen.kotlineticaret.model.Product
import com.oguzhanturkmen.kotlineticaret.service.ProductAPI
import com.oguzhanturkmen.kotlineticaret.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_products.*
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ProductsFragment : Fragment(),ProductRecyclerAdapter.Listener {
    //tek bir view modeli tüm fragmentlarda kullanabilmek için böyle yaparız. tabi fragmanlar
    //aynı aktiviteye bağlıysa
    private val productViewModel : ProductViewModel by activityViewModels()
    private var productRecyclerAdapter : ProductRecyclerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        productViewModel.downloadData() //veriyi indiririz ana sayfaya..

        productViewModel.productList.observe(viewLifecycleOwner, Observer {
            productRecyclerAdapter = ProductRecyclerAdapter(it,this)
            recyclerView.adapter = productRecyclerAdapter
            recyclerView.layoutManager = GridLayoutManager(activity?.baseContext,2)

        })

    }

    override fun onItemClick(product: Product) {
        //sepete ekleme
        productViewModel.addToBasket(product)
    }


}