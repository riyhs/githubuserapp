package com.riyaldi.githubuserapp.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.riyaldi.githubuserapp.R
import com.riyaldi.githubuserapp.ui.DetailFragment
import com.riyaldi.githubuserapp.ui.FollowersFragment
import com.riyaldi.githubuserapp.ui.FollowingFragment

class SectionsPagerAdapter(private val mContext : Context, fm : FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitles = intArrayOf(R.string.detail, R.string.followers, R.string.followings)

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
        return mContext.resources.getString(tabTitles[position])
    }
}