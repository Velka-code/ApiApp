package jp.techacademy.kenichi.nadaya.apiapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.activity_web_view.fab

class WebViewActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        var shop = intent.getSerializableExtra(KEY_URL) as Shop
        //webView.loadUrl(intent.getStringExtra(KEY_URL).toString())
        webView.loadUrl(if (shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc)

        var isFavorite = FavoriteShop.findBy(shop.id) != null
        fab.setImageResource(if (isFavorite) R.drawable.ic_star else R.drawable.ic_star_border)

        fab.setOnClickListener{
            if(isFavorite){
                Log.d("kotlintest", shop.toString())
                //onClickDeleteFavorite?.invoke(shop.id)
                FavoriteShop.delete(shop.id)
                fab.setImageResource(R.drawable.ic_star_border)
                isFavorite = false

            }else{
                fab.setImageResource(R.drawable.ic_star)
                isFavorite = true

                FavoriteShop.insert(FavoriteShop().apply {
                    id = shop.id
                    name = shop.name
                    imageUrl = shop.logoImage
                    url = if (shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc
                    address = shop.address
                })
            }
        }
    }

    companion object {
        private const val KEY_URL = "key_url"

        //fun start(activity: Activity, url: String) {
        fun start(activity: Activity, shop: Shop) {
            activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(KEY_URL, shop))
        }

    }
}