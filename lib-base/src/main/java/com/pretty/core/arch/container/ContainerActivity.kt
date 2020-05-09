package com.pretty.core.arch.container

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.pretty.core.R

/**
 * 容器类，用于加载fragment
 */
class ContainerActivity : AppCompatActivity() {

    companion object {
        const val KEY_CLASS = "fragment_class"
        const val KEY_ARGS = "args_bundle"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_fragment_container)

        val args = intent.getBundleExtra(KEY_ARGS)
        val fragmentClass = intent.getSerializableExtra(KEY_CLASS) as Class<Fragment>

        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, fragmentClass, args)
            .commitAllowingStateLoss()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

}

inline fun <reified T : Fragment> launchFragmentInContainer(
    context: Context,
    fragment: Class<T>,
    args: Bundle? = null
) {
    val intent = Intent(context, ContainerActivity::class.java)
    intent.putExtra(ContainerActivity.KEY_CLASS, fragment)
    if (args != null) {
        intent.putExtra(ContainerActivity.KEY_ARGS, args)
    }
    if (context !is Activity) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
}