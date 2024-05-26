package dav.project.shoestore.activity

import ColorAdapter
import SizeAdapter
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project1762.Helper.ManagmentCart
import dav.project.shoestore.Adapter.SliderAdapter
import dav.project.shoestore.Model.ItemsModel
import dav.project.shoestore.Model.SliderModel
import dav.project.shoestore.R
import dav.project.shoestore.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var item:ItemsModel
    private var numberOder = 1
    private lateinit var managmentCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Status Bar color
        window.statusBarColor = Color.WHITE
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR



        managmentCart = ManagmentCart(this@DetailActivity)

        getBundle()
        banners()
        initLists()

    }

    private fun initLists() {
        val sizeList = ArrayList<String>()
        for (size in item.size){
            sizeList.add(size.toString())
        }

        binding.sizeList.adapter = SizeAdapter(sizeList)
        binding.sizeList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)


        val colorList = ArrayList<String>()
        for (imageUrl in item.picUrl){
            colorList.add(imageUrl)
        }

        binding.colorList.adapter = ColorAdapter(colorList)
        binding.colorList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
    }

    private fun banners() {
        val sliderItems = ArrayList<SliderModel>()
        for(imageUrl in item.picUrl){
            sliderItems.add(SliderModel(imageUrl))
        }

        binding.slider.adapter = SliderAdapter(sliderItems,binding.slider)
        binding.slider.clipToPadding=true
        binding.slider.clipChildren=false
        binding.slider.offscreenPageLimit=1

        if(sliderItems.size > 1){
            binding.dotsIndicator.visibility = View.VISIBLE
            binding.dotsIndicator.attachTo(binding.slider)
        }

    }

    private fun getBundle() {
        item = intent.getParcelableExtra("object")!!

        binding.titleTxt.text = item.title
        binding.descriptionTxt.text = item.description
        binding.priceTxt.text = "$"+item.price
        binding.ratingTxt.text = "${item.rating} Rating"
        binding.addToCartBtn.setOnClickListener{
            item.numberInCart = numberOder
            managmentCart.insertFood(item)
        }
        binding.backBtn.setOnClickListener{finish()}
        binding.cartBtn.setOnClickListener{
            startActivity(Intent(this@DetailActivity, CartActivity::class.java))
        }
    }
}
