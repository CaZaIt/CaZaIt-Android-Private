package org.cazait.ui.findaccount

import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentFindUserIdBinding
import org.cazait.ui.base.BaseFragment

@AndroidEntryPoint
class FindUserIdFragment : BaseFragment<FragmentFindUserIdBinding, FindUserIdViewModel>(
    FindUserIdViewModel::class.java,
    R.layout.fragment_find_user_id
) {
    override fun initView() {
        binding.apply {
            clTop.includedTvTitle.text = resources.getString(R.string.btn_find_id)
            clTop.btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun initAfterBinding() {

    }
}