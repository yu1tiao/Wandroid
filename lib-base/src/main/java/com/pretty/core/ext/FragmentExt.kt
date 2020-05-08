package com.pretty.core.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction

/**
 * @author yu
 * @date 2019/4/8
 */

fun FragmentActivity.transact(action: FragmentTransaction.() -> Unit) {
    supportFragmentManager.beginTransaction()
        .apply(action)
        .commit()
    supportFragmentManager.executePendingTransactions()
}

fun Fragment.transact(action: FragmentTransaction.() -> Unit) {
    childFragmentManager.beginTransaction()
        .apply(action)
        .commit()
    childFragmentManager.executePendingTransactions()
}


fun FragmentActivity.showHide(showIndex: Int, list: List<Fragment>) {
    transact {
        list.forEachIndexed { index, fragment ->
            if (index == showIndex) {
                show(fragment)
            } else {
                hide(fragment)
            }
        }
    }
}

fun Fragment.showHide(showIndex: Int, list: List<Fragment>) {
    transact {
        list.forEachIndexed { index, fragment ->
            if (index == showIndex) {
                show(fragment)
            } else {
                hide(fragment)
            }
        }
    }
}