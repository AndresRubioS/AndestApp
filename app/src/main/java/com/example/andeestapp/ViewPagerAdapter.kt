package com.example.andeestapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(supporFragmentManager: FragmentManager):
FragmentPagerAdapter(supporFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val mFragmenList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()


    override fun getPageTitle(position: Int):CharSequence?{
        return mFragmentTitleList[position]
    }


    override fun getCount(): Int {
        return mFragmenList.size
    }

    override fun getItem(position: Int): Fragment {
       return mFragmenList[position]
    }
    fun addFragment(fragment: Fragment,title:String){
        mFragmenList.add(fragment)
        mFragmentTitleList.add(title)
    }


}