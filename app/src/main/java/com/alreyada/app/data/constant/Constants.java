package com.alreyada.app.data.constant;

public class Constants {

    // Root Url
    public static final String DEFAULT_URL = "http://192.168.0.182/";
    public static final String BASE_URL = DEFAULT_URL + "wp-json/";
    public static final String CUSTOM_API_VERSION = "/v1/";
    public static final String CUSTOM_API_NAMESPACE = "egt-api" + CUSTOM_API_VERSION;

/*
    public static final String CONSUMER_KEY = "vBWbg9i1O0nN"; // ck_fc570fa83a288b3a322795df2180d2e2be44152f
    public static final String CONSUMER_SECRET = "sWC2Q0Z91NTaz6VH0hO10zVVVydbF5tH0AB844NG1LG4uJyY"; // cs_f36484d657b10afc3abc488976936aaf0a69a881
    public static final String TOKEN = "3VQIE3yv97sfsV2xlXkcbaih";
    public static final String TOKEN_SECRET = "gC5TbPjhGLPDijX1PRCE663aa7odjp4QWhqg345nk3aVu08m";

    public static final String WOO_CONSUMER_KEY = "ck_c09aff37d80793fe36f984ffefdbbef283e01634";
    public static final String WOO_CONSUMER_SECRET = "cs_5afa804a1490ee6e9582a2ed17292b68a352c9c9";
    public static final String APP_AUTH = "YWhtZWRyaXlhZGgwOmF1S24gajdJZCBKdWx2IHNQNFAgZGVDNSBpSFZP";*/

    // Wordpress Json Url
    private static final String JSON = "wp-json/";

    public static final String API_TEXT_PAGE = "page";
    public static final String API_RECENT_ITEM_PER_PAGE = "20";
    public static final String API_EMBED = "_embed=true";

    // Woocommerce Api
    public static final String WC_ENDPOINT = JSON + "wc/v3/";
    public static final String GET_ALL_CATEGORIES = WC_ENDPOINT + "products/categories";
    public static final String PRODUCTS = WC_ENDPOINT + "products";
    public static final String ORDERS = WC_ENDPOINT + "orders";
    public static final String CUSTOMERS = WC_ENDPOINT + "customers";
    public static final String SYSTEM = WC_ENDPOINT + "system_status";
    public static final String CHECKOUT_URL = WC_ENDPOINT + "checkout_url";

    // JSON Web Token Plugin
    private static final String JWT_ENDPOINT = "jwt-auth/v1/";
    public static final String GET_TOKEN = JWT_ENDPOINT + "token/";
    public static final String VALIDATE_TOKEN = GET_TOKEN + "validate";
    /*public static final String LOGIN_USER_AUTH = JSON + "login-jwt-custom/v1/auth";
    public static final String VALIDATE_TOKEN = JSON + "login-jwt-custom/v1/auth/validate";*/

    // Password Reset with Code Plugin
    public static final String RESET_PASSWORD_ENDPOINT = "reset-password/";

    public static final String RESET_PASSWORD = JSON + RESET_PASSWORD_ENDPOINT + "v1/reset-password";
    public static final String SET_PASSWORD = JSON + RESET_PASSWORD_ENDPOINT + "v1/set-password";
    public static final String VALIDATE_PASSWORD_CODE = JSON + RESET_PASSWORD_ENDPOINT + "v1/validate-code";

    // Authentication Plugin Api (Use For Validate User Credentials)
    public static final String AUTHENTICATION_API = JSON + "api/v1/";
    public static final String VALIDATE_CREDENTIALS = AUTHENTICATION_API + "user/validate_credentials";
    public static final String LOST_PASSWORD = AUTHENTICATION_API + "user/lost_password";

    //
    public static final String ADVANCED_TRACKING_SHIPMENT = JSON +  "wc-ast/v3/orders/{order_id}/shipment-trackings";

    // Wordpress Api
    public static final String API_RECENT_POSTS = JSON + "wp/v2/posts?per_page=" + API_RECENT_ITEM_PER_PAGE + "&" + API_EMBED;


    public static final boolean isAllowToCreate = true;
    public static final boolean isDemoApp = true;
    public static final boolean isUseOAuth1 = true;
    public static final boolean isUsingWooAuth = true;

    /* Request Code Start */

    public static final int REQUEST_CODE_FINISH_ACTIVITY_AFTER_LOGGED_IN = 1;
    public static final int REQUEST_CODE_WHEN_ADD_PRODUCT_TO_CART = 2;
    public static final int REQUEST_CODE_CLEAR_CART_AFTER_ORDER_CREATED = 3;
    public static final int REQUEST_CODE_OPEN_CART_AFTER_CLICK_MENU_CART_IN_VIEW_ALL_PRODUCTS = 4;
    public static final int REQUEST_CODE_EDIT_ADDRESS_DIALOG_TARGET_FRAGMENT = 5;
    public static final int REQUEST_CODE_SIGN_IN_WITH_GOOGLE = 6;

    /* Request Code End */

    /* Firebase Realtime Database Start */
    public static final String SLIDER_KEY = "Sliders";
    public static final String BANNERS_KEY = "Banners";
    public static final String USERS_KEY = "Users";
    public static final String BANNER_1 = "Banner-1";
    public static final String BANNER_2 = "Banner-2";
    public static final String MESSAGES_KEY = "Messages";
    public static final String CLIENTS_KEY = "Clients";
    public static final String SETTINGS_KEY = "Settings";
    /* Firebase Realtime Database End */

    /* send data key start */
    public static final String productDetails = "product_details";
    /* send data key end */

    /* Another Data */
    public static final String CSS_PROPERTIES = "<style>body{width:100%;margin:0;}img {max-width:100%;height:auto;} iframe{width:100%;}</style>";
}
