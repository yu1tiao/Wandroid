package com.pretty.core.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit

/**
 * @author yu
 * @date 2019/4/8
 */

fun FragmentActivity.showHide(showIndex: Int, list: List<Fragment>) {
    supportFragmentManager.commit {
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
    childFragmentManager.commit {
        list.forEachIndexed { index, fragment ->
            if (index == showIndex) {
                show(fragment)
            } else {
                hide(fragment)
            }
        }
    }
}