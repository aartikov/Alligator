package me.aartikov.alligator.helpers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import me.aartikov.alligator.animations.TransitionAnimation

/**
 * Custom implementation of a fragment backstack with flexible animation control.
 */
class FragmentStack(fragmentManager: FragmentManager, containerId: Int) {
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

    val fragmentCount get() = fragments.size

    val currentFragment get() = mFragmentManager.findFragmentById(mContainerId)

    fun pop(animation: TransitionAnimation) {
        val fragments = fragments
        val count = fragments.size
        check(count != 0) { "Can't pop fragment when stack is empty." }
        val currentFragment = fragments[count - 1]
        val previousFragment = if (count > 1) fragments[count - 2] else null
        val transaction = mFragmentManager.beginTransaction()
        if (previousFragment != null) {
            animation.applyBeforeFragmentTransactionExecuted(
                transaction,
                previousFragment,
                currentFragment
            )
        }
        transaction.remove(currentFragment)
        if (previousFragment != null) {
            transaction.attach(previousFragment)
        }
        transaction.commitNow()
        if (previousFragment != null) {
            animation.applyAfterFragmentTransactionExecuted(previousFragment, currentFragment)
        }
    }

    fun popUntil(fragment: Fragment, animation: TransitionAnimation) {
        val fragments = fragments
        val count = fragments.size
        val index = fragments.indexOf(fragment)
        require(index != -1) { "Fragment is not found." }
        if (index == count - 1) {
            return  // nothing to do
        }
        val transaction = mFragmentManager.beginTransaction()
        for (i in index + 1 until count) {
            if (i == count - 1) {
                animation.applyBeforeFragmentTransactionExecuted(
                    transaction,
                    fragment,
                    fragments[i]
                )
            }
            transaction.remove(fragments[i])
        }
        transaction.attach(fragment)
        transaction.commitNow()
        animation.applyAfterFragmentTransactionExecuted(fragment, fragments[count - 1])
    }

    fun push(fragment: Fragment, animation: TransitionAnimation) {
        val currentFragment = this.currentFragment
        val transaction = mFragmentManager.beginTransaction()
        if (currentFragment != null) {
            animation.applyBeforeFragmentTransactionExecuted(transaction, fragment, currentFragment)
            transaction.detach(currentFragment)
        }
        val index = this.fragmentCount
        transaction.add(mContainerId, fragment, getFragmentTag(index))
        transaction.commitNow()
        if (currentFragment != null) {
            animation.applyAfterFragmentTransactionExecuted(fragment, currentFragment)
        }
    }

    fun replace(fragment: Fragment, animation: TransitionAnimation) {
        val currentFragment = this.currentFragment
        val transaction = mFragmentManager.beginTransaction()
        if (currentFragment != null) {
            animation.applyBeforeFragmentTransactionExecuted(transaction, fragment, currentFragment)
            transaction.remove(currentFragment)
        }
        val count = this.fragmentCount
        val index = if (count == 0) 0 else count - 1
        transaction.add(mContainerId, fragment, getFragmentTag(index))
        transaction.commitNow()
        if (currentFragment != null) {
            animation.applyAfterFragmentTransactionExecuted(fragment, currentFragment)
        }
    }

    fun reset(fragment: Fragment, animation: TransitionAnimation) {
        val fragments = fragments
        val count = fragments.size
        val transaction = mFragmentManager.beginTransaction()
        for (i in 0 until count) {
            if (i == count - 1) {
                animation.applyBeforeFragmentTransactionExecuted(
                    transaction,
                    fragment,
                    fragments[i]
                )
            }
            transaction.remove(fragments[i])
        }
        transaction.add(mContainerId, fragment, getFragmentTag(0))
        transaction.commitNow()
        if (count > 0) {
            animation.applyAfterFragmentTransactionExecuted(fragment, fragments[count - 1])
        }
    }

    private fun getFragmentTag(index: Int): String {
        return TAG_PREFIX + mContainerId + "_" + index
    }

    companion object {
        private const val TAG_PREFIX = "me.aartikov.alligator.FRAGMENT_STACK_TAG_"
    }
}