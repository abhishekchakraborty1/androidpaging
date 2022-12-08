package com.abhishekchakraborty.androidpaging.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.abhishekchakraborty.androidpaging.R
import com.abhishekchakraborty.androidpaging.model.ViewEffect
import com.abhishekchakraborty.androidpaging.viewModel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val model by viewModel<MainViewModel>()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, NewsListFragment.newInstance(), NewsListFragment::class.qualifiedName)
            .commit()

        observeViewEffect()
    }

    private fun observeViewEffect() {
        model.obtainViewEffects.observe(this, Observer {
            trigger(it)
        })
    }

    private fun trigger(effect: ViewEffect) {
        when(effect) {
            is ViewEffect.TransitionToScreen -> {
                TODO( "implement second screen")
//                val intent = Intent(this, SinglePhotoActivity::class.java)
//                intent.putExtra(getString(R.string.extra_item), effect.article)
//                startActivity(intent)
            }
        }
    }
}