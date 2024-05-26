package dav.project.shoestore.activity

import BrandAdapter
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import dav.project.shoestore.Adapter.PopularAdapter
import dav.project.shoestore.Model.SliderModel
import dav.project.shoestore.R
import dav.project.shoestore.Adapter.SliderAdapter
import dav.project.shoestore.Model.BrandModel
import dav.project.shoestore.ViewModel.MainViewModel
import dav.project.shoestore.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private val viewModel = MainViewModel()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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


        binding.btnNotify.setOnClickListener{
            startActivity(Intent(this@MainActivity, NotificationActivity::class.java))
        }

        binding.btnSearch.setOnClickListener{
            startActivity(Intent(this@MainActivity, SearchActivity::class.java))
        }



        initBanner()
        initBrand()
        initPopular()
        initBottomMenu()
    }

    private fun initBottomMenu() {
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, CartActivity::class.java))
        }
    }

    private fun initBanner() {
        binding.progressBarBanner.visibility = View.VISIBLE
        viewModel.banners.observe(this, Observer { items ->
            banners(items)
            binding.progressBarBanner.visibility = View.GONE
        })
        viewModel.loadBanners()
    }

    private fun banners(images: List<SliderModel>) {
        binding.viewpageSlider.adapter = SliderAdapter(images, binding.viewpageSlider)
        binding.viewpageSlider.clipToPadding = false
        binding.viewpageSlider.clipChildren = false
        binding.viewpageSlider.offscreenPageLimit = 3
        binding.viewpageSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }
        binding.viewpageSlider.setPageTransformer(compositePageTransformer)
        if (images.size > 1) {
            binding.dotsIndicator.visibility = View.VISIBLE
            binding.dotsIndicator.attachTo(binding.viewpageSlider)
        }


    }

    private fun initBrand() {
        binding.progressBarBrand.visibility = View.VISIBLE
        viewModel.brands.observe(this, {
            binding.viewBrand.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            binding.viewBrand.adapter = BrandAdapter(it)
            binding.progressBarBrand.visibility = View.GONE
        })
        viewModel.loadBrands()


    }

    private fun initPopular() {
        binding.progressBarPopular.visibility = View.VISIBLE
        viewModel.popular.observe(this, { items ->
            binding.viewPopular.layoutManager =
                GridLayoutManager(this@MainActivity, 2, LinearLayoutManager.HORIZONTAL, false)
            binding.viewPopular.adapter = PopularAdapter(items)
            binding.progressBarPopular.visibility = View.GONE
        })
        viewModel.loadPopular()

    }


}