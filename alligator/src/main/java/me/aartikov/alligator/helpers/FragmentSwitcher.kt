package me.aartikov.alligator.helpers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import me.aartikov.alligator.animations.TransitionAnimation

/**
 * Helper class for fragment switching.
 */
class FragmentSwitcher(fragmentManager: FragmentManager, containerId: Int) {

    private val mFragmentManager: FragmentManager
    private val mContainerId: Int

    init {
        require(containerId > 0) { "ContainerId is not set." }
        mFragmentManager = fragmentManager
        mContainerId = containerId
    }

    val fragments: List<Fragment>
        get() {
            val result: MutableList<Fragment> = ArrayList()
            var index = 0
            while (true) {
                val tag = getFragmentTag(index)
                val fragment = mFragmentManager.findFragmentByTag(tag) ?: break
                if (!fragment.isRemoving) {
                    result.add(fragment)
                }
                index++
            }
            return result
        }

    fun switchTo(fragment: Fragment, animation: TransitionAnimation) {
        val fragments = fragments
        val isNewFragment = !fragments.contains(fragment)
        val currentFragment = currentFragment
        val transaction = mFragmentManager.beginTransaction()
        if (currentFragment != null) {
            animation.applyBeforeFragmentTransactionExecuted(transaction, fragment, currentFragment)
            transaction.detach(currentFragment)
        }
        if (isNewFragment) {
            transaction.add(mContainerId, fragment, getFragmentTag(fragments.size))
        } else {
            transaction.attach(fragment)
        }
        transaction.commitNow()
        if (currentFragment != null) {
            animation.applyAfterFragmentTransactionExecuted(fragment, currentFragment)
        }
    }

    val currentFragment: Fragment?
        get() = mFragmentManager.findFragmentById(mContainerId)

    private fun getFragmentTag(index: Int): String {
        return TAG_PREFIX + mContainerId + "_" + index
    }

    companion object {
        private const val TAG_PREFIX = "me.aartikov.alligator.FRAGMENT_SWITCHER_TAG_"
    }
}