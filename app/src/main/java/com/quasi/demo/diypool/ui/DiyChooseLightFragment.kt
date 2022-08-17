package com.quasi.demo.diypool.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.quasi.demo.diypool.R
import com.quasi.demo.diypool.databinding.FragmentDiyChooseLightBinding
import com.quasi.template.demo.diypool.base.VSBaseViewModel.Companion.LOG_TAG

class DiyChooseLightFragment : Fragment() {

    private val mViewModel: DiyKitPoolViewModel by activityViewModels()
    lateinit var mBinding: FragmentDiyChooseLightBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_diy_choose_light,
            container,
            false
        )
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.refreshListLiveData.observe(this) {
            Log.d(LOG_TAG, "数据通知界面刷新列表(Light) $it")

            if (mViewModel.mExpandSection == "Light") {
                mBinding.sectionHeader.typeLabel.text = "Light 选中"
                mBinding.chooseLightListView.visibility = View.VISIBLE
            } else {
                mBinding.sectionHeader.typeLabel.text = "Light 未选中"
                mBinding.chooseLightListView.visibility = View.GONE
            }
        }

        addSubView()
    }

    private fun addSubView() {
        mBinding.sectionHeader.root.setOnClickListener {
            mViewModel.clickSectionTitle("Light")
        }
    }
}