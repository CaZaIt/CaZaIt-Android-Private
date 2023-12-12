package org.cazait.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<T : ViewDataBinding, R : ViewModel>(
    private val viewModelClass: Class<R>,
    @LayoutRes private val layoutResourceId: Int,
) : Fragment() {

    private var _binding: T? = null

    // This property is only valid between onCreateView and onDestroyView.
    protected val binding get() = _binding!!

    lateinit var viewModel: R

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DataBindingUtil.inflate<T>(inflater, layoutResourceId, container, false).apply {
            lifecycleOwner = this@BaseFragment.viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBeforeBinding()
        initView()
        initAfterBinding()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initBeforeBinding() {
        viewModel = ViewModelProvider(requireActivity())[viewModelClass]
    }

    /**
     * initiate view and click event
     */
    abstract fun initView()

    /**
     * initiate others (ex. observe Livedata)
     */
    abstract fun initAfterBinding()
}
