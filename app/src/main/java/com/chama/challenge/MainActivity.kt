package com.chama.challenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chama.challenge.common.utils.popBackStack
import com.chama.challenge.common.utils.replaceFragment
import com.chama.challenge.heritages.ui.HeritagesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.findFragmentById(R.id.content) != null) return

        replaceFragment(HeritagesFragment.create())
    }

    override fun onBackPressed() {
        popBackStack()
    }
}
