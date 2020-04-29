package com.pretty.core.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * @author yu
 * @date 2019/4/8
 */

fun FragmentActivity.replace(layoutId: Int, f: Fragment, bundle: Array<out Pair<String, Any>>? = null) {
    if (bundle != null) f.arguments = bundle.toBundle()
    supportFragmentManager.beginTransaction()
        .replace(layoutId, f, f.javaClass.name)
        .commitAllowingStateLoss()
}

fun FragmentActivity.add(layoutId: Int, f: Fragment, bundle: Array<out Pair<String, Any>>? = null) {
    if (bundle != null) f.arguments = bundle.toBundle()
    supportFragmentManager.beginTransaction()
        .add(layoutId, f, f.javaClass.name)
        .commitAllowingStateLoss()
}

fun FragmentActivity.add(layoutId: Int, list: List<Fragment>, showIndex: Int) {
    supportFragmentManager.beginTransaction()
        .apply {
            list.forEachIndexed { index, fragment ->
                add(layoutId, fragment, fragment.javaClass.name)
                if (showIndex == index) {
                    show(fragment)
                } else {
                    hide(fragment)
                }
            }
        }
        .commitAllowingStateLoss()
}

fun FragmentActivity.hide(f: Fragment) {
    supportFragmentManager.beginTransaction()
        .hide(f)
        .commitAllowingStateLoss()
}

fun FragmentActivity.show(f: Fragment) {
    supportFragmentManager.beginTransaction()
        .show(f)
        .commitAllowingStateLoss()
}

fun FragmentActivity.showHide(showIndex: Int, list: List<Fragment>) {
    supportFragmentManager.beginTransaction()
        .apply {
            list.forEachIndexed { index, fragment ->
                if (showIndex == index) {
                    show(fragment)
                } else {
                    hide(fragment)
                }
            }
        }
        .commitAllowingStateLoss()
}

fun FragmentActivity.remove(f: Fragment) {
    supportFragmentManager.beginTransaction()
        .remove(f)
        .commitAllowingStateLoss()
}


fun Fragment.replace(layoutId: Int, f: Fragment, bundle: Array<out Pair<String, Any>>?) {
    if (bundle != null) f.arguments = bundle.toBundle()
    childFragmentManager.beginTransaction()
        .replace(layoutId, f, f.javaClass.name)
        .commitAllowingStateLoss()
}

fun Fragment.add(layoutId: Int, f: Fragment, bundle: Array<out Pair<String, Any>>?) {
    if (bundle != null) f.arguments = bundle.toBundle()
    childFragmentManager.beginTransaction()
        .add(layoutId, f, f.javaClass.name)
        .commitAllowingStateLoss()
}

fun Fragment.add(layoutId: Int, list: List<Fragment>, showIndex: Int) {
    childFragmentManager.beginTransaction()
        .apply {
            list.forEachIndexed { index, fragment ->
                add(layoutId, fragment, fragment.javaClass.name)
                if (showIndex == index) {
                    show(fragment)
                } else {
                    hide(fragment)
                }
            }
        }
        .commitAllowingStateLoss()
}

fun Fragment.hide(f: Fragment) {
    childFragmentManager.beginTransaction()
        .hide(f)
        .commitAllowingStateLoss()
}

fun Fragment.show(f: Fragment) {
    childFragmentManager.beginTransaction()
        .show(f)
        .commitAllowingStateLoss()
}

fun Fragment.showHide(showIndex: Int, list: List<Fragment>) {
    childFragmentManager.beginTransaction()
        .apply {
            list.forEachIndexed { index, fragment ->
                if (showIndex == index) {
                    show(fragment)
                } else {
                    hide(fragment)
                }
            }
        }
        .commitAllowingStateLoss()
}

fun Fragment.remove(f: Fragment) {
    childFragmentManager.beginTransaction()
        .remove(f)
        .commitAllowingStateLoss()
}