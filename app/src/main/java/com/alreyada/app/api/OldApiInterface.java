package com.alreyada.app.api;


import com.alreyada.app.model.authentication.customer.Customer;
import com.alreyada.app.model.authentication.jwt.JwtGetToken;
import com.alreyada.app.model.authentication.jwt.JwtValidateToken;
import com.alreyada.app.model.authentication.signin.ValidateCredentials;
import com.alreyada.app.model.categories.Category;
import com.alreyada.app.model.checkouturl.CheckoutUrlResponse;
import com.alreyada.app.model.lostpassword.LostPassword;
import com.alreyada.app.model.ordernote.OrderNoteRequest;
import com.alreyada.app.model.ordernote.OrderNoteResponse;
import com.alreyada.app.model.orders.Order;
import com.alreyada.app.model.orders.OrderRequest;
import com.alreyada.app.model.posts.Post;
import com.alreyada.app.model.product.Product;
import com.alreyada.app.model.resetpassword.ResetPassword;
import com.alreyada.app.model.systemstatus.SystemStatus;
import com.alreyada.app.data.constant.Constants;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface OldApiInterface {

    @GET(Constants.GET_ALL_CATEGORIES)
    Call<List<Category>> getCategories();

    @GET(Constants.PRODUCTS)
    Call<List<Product>> getProductsOfCategories(@Query("category") int category);

    @GET(Constants.SYSTEM)
    Call<SystemStatus> getSystemStatus();

    @GET(Constants.PRODUCTS + "/{id}")
    Call<Product> getProduct(@Path("id") int productID);

    @GET(Constants.PRODUCTS)
    Call<List<Product>> getAllProducts(
            @Query("page") int page,
            @Query("per_page") int itemPerPage,
            @QueryMap Map<String, Object> queryMap
    );

    @GET(Constants.PRODUCTS)
    Call<List<Product>> getSpecificsProducts(
            @Query("include") String include,
            @Query("page") int page,
            @Query("per_page") int perPage);

    @GET(Constants.CUSTOMERS)
    Call<List<Customer>> getCustomers(@Query("page") int page, @Query("per_page") int itemPerPage);

    @POST(Constants.CUSTOMERS)
    Call<Customer> createNewCustomer(
            @QueryMap Map<String, String> queryMap,
            /*@Query("first_name") String firstName,
            @Query("last_name") String lastName,*/
            @Query("email") String email,
            @Query("password") String password
    );

    @PUT(Constants.CUSTOMERS + "/{id}")
    Call<Customer> updateCustomer(@Path("id") int id, @Body Customer customer);

    /*@POST(HttpURLs.LOGIN_USER_AUTH)
    Call<Authentication> loginCustomerWithEmailAndPassword(
            @Query("email") String email,
            @Query("password") String password
    );

    @POST(HttpURLs.LOGIN_USER_AUTH)
    Call<Authentication> loginCustomerWithUsernameAndPassword(
            @Query("username") String username,
            @Query("password") String password
    );

    /*@GET(Constants.VALIDATE_TOKEN)
    Call<ValidateToken> validateUserToken(
            @Query("JWT") String token
    );*/

    @POST(Constants.CHECKOUT_URL)
    Call<CheckoutUrlResponse> getCheckoutUrl(@Query("order_id") String orderId);

    @POST(Constants.GET_TOKEN)
    Call<JwtGetToken> getToken(
            @Query("username") String usernameOrEmail,
            @Query("password") String password
    );

    @POST(Constants.VALIDATE_TOKEN)
    Call<JwtValidateToken> validateToken(@Header("Authorization") String token);

    @POST(Constants.VALIDATE_CREDENTIALS)
    Call<ValidateCredentials> validateCredentials(@Query("username") String username, @Query("password") String password);

    @POST(Constants.LOST_PASSWORD)
    Call<LostPassword> sendResetPasswordLink(@Query("username") String username);

    @GET(Constants.CUSTOMERS + "/{id}")
    Call<Customer> getUserDetailsById(@Path("id") int id);

    @GET(Constants.CUSTOMERS)
    Call<List<Customer>> getUserDetailsByEmail(
            @Query("email") String email,
            @Query("role") String role
    );

    @POST(Constants.RESET_PASSWORD)
    Call<ResetPassword> sendResetPasswordCode(
            @Query("email") String email
    );

    @POST(Constants.VALIDATE_PASSWORD_CODE)
    Call<ResetPassword> validatePasswordCode(
            @Query("email") String email,
            @Query("code") String code
    );


    @POST(Constants.SET_PASSWORD)
    Call<ResetPassword> setNewPassword(
            @Query("email") String email,
            @Query("password") String newPassword,
            @Query("code") String code
    );

    @POST(Constants.ORDERS)
    Call<Order> createOrder(
            @Body OrderRequest order,
            @Query("customer_id") int customerId
    );

    @POST(Constants.ORDERS + "/{id}/notes")
    Call<OrderNoteResponse> createOrderNote(
            @Path("id") int orderId,
            @Body OrderNoteRequest orderNoteRequest,
            @Query("customer_note") boolean customerNote,
            @Query("added_by_user") boolean isAddedByUser
    );

    /*@GET(Constants.ADVANCED_TRACKING_SHIPMENT)
    Call<> getOrderTracking();*/

    @GET(Constants.ORDERS)
    Call<List<Order>> getUserOrders(
            @Query("customer") int customerID,
            @Query("page") int page,
            @Query("per_page") int itemPerPage
    );

    @GET(Constants.API_RECENT_POSTS)
    Call<List<Post>> getRecentPosts(@Query(Constants.API_TEXT_PAGE) int pageCount);


}
