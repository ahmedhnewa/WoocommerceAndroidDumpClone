package com.alreyada.app.api;

import com.alreyada.app.model.authentication.jwt.JwtGetToken;
import com.alreyada.app.model.product.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static com.alreyada.app.data.constant.Constants.CUSTOMERS;
import static com.alreyada.app.data.constant.Constants.CUSTOM_API_NAMESPACE;
import static com.alreyada.app.data.constant.Constants.GET_TOKEN;

public interface ApiInterface {

    @GET(CUSTOM_API_NAMESPACE + "woocommerce/products")
    Call<List<Product>> getProducts();

    @GET(CUSTOM_API_NAMESPACE + "woocommerce/products/{id}")
    Call<Product> getProduct(@Path("id") int id);

    @GET(CUSTOM_API_NAMESPACE + "woocommerce/products/categories")
    Call<Product> getCategories();

    @POST(GET_TOKEN)
    Call<JwtGetToken> getToken(@Body String username, @Body String password);
}
