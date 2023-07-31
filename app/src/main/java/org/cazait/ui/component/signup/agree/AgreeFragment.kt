package org.cazait.ui.component.signup.agree

import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentAgreeBinding
import org.cazait.ui.base.BaseFragment
import org.cazait.utils.SingleEvent
import org.cazait.utils.showToast

@AndroidEntryPoint
class AgreeFragment : BaseFragment<FragmentAgreeBinding, AgreeViewModel>(
    AgreeViewModel::class.java,
    R.layout.fragment_agree
) {
    override fun initView() {
        binding.fragment = this
        binding.viewModel = this.viewModel
        binding.clTop.includedTvTitle.text = getString(R.string.agree_title)
        binding.clTop.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        initNextBtn()
        initLocationAgree()
        initPrivacyAgree()
        initAgreeCheck()
    }

    override fun initAfterBinding() {
        observeViewModel()
    }

    private fun initAgreeCheck() {
        val checkBoxList = listOf(binding.cbAgreeLocation, binding.cbAgreePrivacy)

        binding.cbAgreeAll.setOnCheckedChangeListener { _, isChecked ->
            checkBoxList.forEach { checkBox ->
                checkBox.isChecked = isChecked
            }
        }

        checkBoxList.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) {
                    binding.cbAgreeAll.isChecked = false
                } else {
                    // 모든 개별 체크박스들이 선택되었는지 확인하고, 모두 선택되었으면 전체동의 체크박스도 체크
                    binding.cbAgreeAll.isChecked = checkBoxList.all { it.isChecked }
                }
            }
        }
    }

    private fun observeViewModel() {
        observeToast(viewModel.showToast)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun initLocationAgree() {
        binding.layoutAgreeLocation.setOnClickListener {
            navigateToLocationTermsFragment()
        }
    }

    private fun navigateToLocationTermsFragment() {
        findNavController().navigate(AgreeFragmentDirections.actionAgreeFragmentToLocationTermsFragment())
    }

    private fun initPrivacyAgree() {
        binding.layoutAgreePrivacy.setOnClickListener {
            navigateToPrivacyTermsFragment()
        }
    }

    private fun navigateToPrivacyTermsFragment() {
        findNavController().navigate(AgreeFragmentDirections.actionAgreeFragmentToPrivacyTermsFragment())
    }

    private fun initNextBtn() {
        binding.btnAgreeNext.setOnClickListener {
            if (binding.cbAgreeAll.isChecked) {
                navigateToSignUpFragment()
            } else {
                viewModel.showToastMessage(resources.getString(R.string.agree_please))
            }
        }
    }

    private fun navigateToSignUpFragment() {
        findNavController().navigate(AgreeFragmentDirections.actionAgreeFragmentToSignUpFragment())
    }
}