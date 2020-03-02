package com.chama.challenge.common.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.chama.challenge.R

fun AppCompatActivity.replaceFragment(target: Fragment) {
    supportFragmentManager
        .beginTransaction()
        .addToBackStack(target::class.java.name)
        .replace(R.id.content, target, target::class.java.name)
        .commit()
}

fun Fragment.replaceFragment(target: Fragment) {
    requireActivity()
        .supportFragmentManager
        .beginTransaction()
        .addToBackStack(target::class.java.name)
        .replace(R.id.content, target, target::class.java.name)
        .commit()
}

fun Fragment.popBackStack() {
    (requireActivity() as? FragmentActivity)?.popBackStack()
}

fun FragmentActivity.popBackStack() {
    if (supportFragmentManager.backStackEntryCount < 2) {
        finish()
    } else {
        supportFragmentManager.popBackStackImmediate()
    }
}