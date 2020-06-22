package com.riyaldi.githubuserapp.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.riyaldi.githubuserapp.R
import com.riyaldi.githubuserapp.tabfragment.DetailFragment
import com.riyaldi.githubuserapp.tabfragment.FollowersFragment
import com.riyaldi.githubuserapp.tabfragment.FollowingFragment

class SectionsPagerAdapter(private val mContext : Context, fm : FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.detail, R.string.followers, R.string.followings)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = DetailFragment()
            1 -> fragment = FollowersFragment()
            2 -> fragment = FollowingFragment()
        }
        return fragment as Fragment
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }
}