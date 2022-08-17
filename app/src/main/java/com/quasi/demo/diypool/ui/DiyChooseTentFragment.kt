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
import com.quasi.demo.diypool.databinding.FragmentDiyChooseTentBinding
import com.quasi.template.demo.diypool.base.VSBaseViewModel

class DiyChooseTentFragment : Fragment() {

    private val mViewModel: DiyKitPoolViewModel by activityViewModels()
    lateinit var mBinding: FragmentDiyChooseTentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_diy_choose_tent,
            container,
            false
        )
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        addSubView()

        mViewModel.refreshListLiveData.observe(this) {
            Log.d(VSBaseViewModel.LOG_TAG, "数据通知界面刷新列表(Tent) $it")

            if (mViewModel.mExpandSection == "Tent") {
                mBinding.sectionHeader.typeLabel.text = "Tent 选中"
                mBinding.chooseTentListView.visibility = View.VISIBLE
            } else {
                mBinding.sectionHeader.typeLabel.text = "Tent 未选中"
                mBinding.chooseTentListView.visibility = View.GONE
            }
        }
    }

    private fun addSubView() {
        mBinding.sectionHeader.root.setOnClickListener {
            mViewModel.clickSectionTitle("Tent")
        }
    }
}