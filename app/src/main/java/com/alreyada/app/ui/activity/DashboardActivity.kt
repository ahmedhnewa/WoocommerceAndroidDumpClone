package com.alreyada.app.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alreyada.app.R
import com.alreyada.app.data.preference.AppPreference
import com.alreyada.app.data.preference.PrefKey
import com.alreyada.app.data.preference.SessionManager
import com.alreyada.app.data.sqlite.DbCartController
import com.alreyada.app.databinding.ActivityDashboardBinding
import com.alreyada.app.ui.dialog.EnterNameDialog.EnterNameDialogListener
import com.alreyada.app.ui.dialog.PleaseSwipeToRefreshCartDialog
import com.alreyada.app.ui.fragment.CartFragment.CartFragmentListener
import com.alreyada.app.ui.fragment.HomeFragment.HomeFragmentListener
import com.alreyada.app.util.Utils
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity(), HomeFragmentListener, CartFragmentListener, EnterNameDialogListener {

    private var sessionManager: SessionManager? = null
    private var cartItems = 0
    private var actionBar: ActionBar? = null
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var itemCount = 0
    private var tvNotificationCount: TextView? = null
    private var activity: Activity? = null
    private var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        activity = this@DashboardActivity
        context = this@DashboardActivity
        Toast.makeText(this, "Hello", Toast.LENGTH_LONG)
        initVar()
        initToolbar()
        initDrawer()
        setupBottomNavigation()
        /*
        fm = getSupportFragmentManager();
        if (savedInstanceState == null) {
            initFragments();
        }*/sessionManager = SessionManager(this@DashboardActivity)
        /*if (!sessionManager.isLoggedIn() && AppPreference.getInstance(DashboardActivity.this).getBoolean(AppConstant.isFirstTime, true)) {
            AppPreference.getInstance(DashboardActivity.this).setBoolean(AppConstant.isFirstTime, false);
        }*/updateCountOfCartFragment()
        //        bottomNavigation.setCount(3, String.valueOf(itemCount));

/*        if (sessionManager.isLoggedIn()) {
            validateCredentials(sessionManager.getUser().getEmail(), sessionManager.getUserPassword());
        }*/
    }

    private fun logoutUserPasswordChanged() {
        sessionManager!!.logout()
        Utils.showLongToast("تم تغير معلومات حسابك , الرجاء تسجيل الدخول مرة اخرى", context)
        startActivity(Intent(context, LoginActivity::class.java))
        finish()
    }

    private fun initVar() {
        cartItems = DbCartController(context).cartList.size
    }

    private fun setupBottomNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(binding.fragment.id) as NavHostFragment
        val navController: NavController = navHostFragment.findNavController()
        appBarConfiguration = AppBarConfiguration(setOf(bottomNavHomeId, bottomNavCategories, bottomNavCart, bottomNavAccount))

        setSupportActionBar(binding.toolbarLayout.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigation.setupWithNavController(navController)
    }


    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            tapToExit()
        }
    }

    private fun tapToExit() {
        if (backPressed + 2500 > System.currentTimeMillis()) {
            finish()
        } else {
            Toast.makeText(this@DashboardActivity, "اضغط مرة اخرى للخروج", Toast.LENGTH_SHORT).show()
        }
        backPressed = System.currentTimeMillis()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbarLayout.toolbar)
        actionBar = supportActionBar
        updateTitleTv("<font color='#ffffff'>الصفحة الرئيسية</font>")
    }

    private fun initDrawer() {
        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbarLayout.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                binding.fragment.translationX = -slideOffset * drawerView.width
                binding.drawerLayout.bringChildToFront(drawerView)
                binding.drawerLayout.requestLayout()
            }
        }
        toggle.toolbarNavigationClickListener = View.OnClickListener {
            if (binding.drawerLayout.isDrawerVisible(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        toggle.isDrawerIndicatorEnabled = false
        toggle.setHomeAsUpIndicator(ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_drawer,
                theme
        ))
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        /*binding.toolbarLayout.toolbar.setNavigationIcon();*/
    }

    private fun updateCountOfCartFragment() {
        itemCount = DbCartController(context).cartList.size
        //        bottomNavigation.setCount(3, String.valueOf(itemCount));
        try {
            tvNotificationCount!!.text = itemCount.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateTitleTv(title: String) {
        actionBar!!.title = Html.fromHtml(title)
        // AhmedRiyadh
        // GoodBye for ever!!
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_dashboard, menu)
        // search for cartIcon and setOnClickListener for It
        for (i in 0 until menu.size()) {
            val item = menu.findItem(R.id.action_cart)
            if (item.itemId == R.id.action_cart) {
                val itemChooser = item.actionView
                itemChooser?.setOnClickListener { //                            bottomNavigation.show(bottomNavCart, true);
                    binding.bottomNavigation.selectedItemId = R.id.cartFragment
                }
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search ->                 // not implemented here , in home fragment
                return false
            R.id.action_cart -> //                bottomNavigation.show(bottomNavCart, true);
                return true
            R.id.action_wish_list -> {
                startActivity(Intent(context, WishListActivity::class.java))
                return true
            }
            else -> {
            }
        }
        return false
    }

    override fun onRequestRefreshCartIcon() {
        updateCountOfCartFragment()
    }

    override fun onRequestRefreshCart() {
        if (AppPreference.getInstance(context).getBoolean(PrefKey.isShouldShow1, true)) {
            Handler().postDelayed({
                val pleaseSwipeToRefreshCartDialog = PleaseSwipeToRefreshCartDialog()
                pleaseSwipeToRefreshCartDialog.show(supportFragmentManager, "please swipe to refresh cart dialog")
            }, 50)
        }
        updateCountOfCartFragment()
    }

    override fun onRequestLoadCartFragment() {
//        bottomNavigation.show(bottomNavCart, false);
    }

    override fun onRequestLoadAccountFragment() {
//        bottomNavigation.show(bottomNavAccount, false);
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val item = menu.findItem(R.id.action_cart)
        val rootView = item.actionView
        tvNotificationCount = rootView!!.findViewById<View>(R.id.tvNotificationCount) as TextView
        itemCount = DbCartController(context).cartList.size
        tvNotificationCount!!.text = itemCount.toString()
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onReceiveTextsFromEnterNameDialog(firstName: String, lastName: String, phoneNumber: String) {
        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
//                    createUserAnonymouslyData(task.getResult().getUser());
            } else {
                Utils.showErrorMessage("تعذر انشاء درشة محادثة", activity)
                Log.d(TAG, "onComplete: " + task.exception!!.message)
            }
        }
    }

    companion object {
        private var backPressed: Long = 0

        private const val bottomNavHomeId = 1
        private const val bottomNavCategories = 2
        private const val bottomNavCart = 3
        private const val bottomNavAccount = 4
        private const val TAG = "DashboardActivity"
    }
}