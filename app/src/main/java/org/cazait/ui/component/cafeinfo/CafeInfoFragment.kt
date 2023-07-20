package org.cazait.ui.component.cafeinfo

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentCafeInfoBinding
import org.cazait.model.Cafe
import org.cazait.ui.adapter.CafeImgAdapter
import org.cazait.ui.adapter.ViewPagerAdapter
import org.cazait.ui.component.cafeinfo.detail.CafeInfoMenuFragment
import org.cazait.ui.component.cafeinfo.detail.CafeInfoReviewFragment

@AndroidEntryPoint
class CafeInfoFragment : Fragment() {
    private val navArgs: CafeInfoFragmentArgs by navArgs()
    private lateinit var cafe: Cafe
    private var _binding: FragmentCafeInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CafeInfoViewModel by viewModels()
    private lateinit var viewPagerImageAdapter: CafeImgAdapter
    private lateinit var fragmentList: List<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updateSignInState()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        cafe = navArgs.cafe
        _binding = DataBindingUtil.inflate<FragmentCafeInfoBinding?>(
            inflater,
            R.layout.fragment_cafe_info,
            container,
            false
        ).apply {
            cafe = this@CafeInfoFragment.cafe
            viewModel = this@CafeInfoFragment.viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        setupCafe()
        setupViewPager()
        setupButtons()
        initViewModel(cafe, cafe.isFavorite)
    }

    private fun setupCafe() {
        with(binding) {
            cafe = this.cafe
            viewModel = this.viewModel
            fragmentList =
                listOf(
                    CafeInfoMenuFragment(
                        this@CafeInfoFragment.cafe,
                        this@CafeInfoFragment.viewModel
                    ),
                    CafeInfoReviewFragment(
                        this@CafeInfoFragment.cafe,
                        this@CafeInfoFragment.viewModel
                    )
                )
        }
    }

    private fun setupViewPager() {
        initViewPagerImageAdapter(cafe.images)
        initDotIndicator(binding.vpImg)
        initFragmentViewPager()
    }

    private fun setupButtons() {
        initBackPressButton()
        initFavoriteButton()
    }

    private fun initBackPressButton() {
        binding.imgBack.apply {
            bringToFront()
            setOnClickListener { findNavController().popBackStack() }
        }
    }

    private fun initDotIndicator(imgViewPager: ViewPager2) {
        binding.dotsIndicator.attachTo(imgViewPager)
    }

    private fun initViewPagerImageAdapter(images: List<String>) {
        viewPagerImageAdapter = CafeImgAdapter(requireContext(), images)
        binding.vpImg.adapter = viewPagerImageAdapter
    }

    private fun initFragmentViewPager() {
        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle, fragmentList)

        binding.vpFragment.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.vpFragment) { tab, position ->
            tab.text = getString(
                if (fragmentList[position] is CafeInfoMenuFragment)
                    R.string.info_cafemenu
                else
                    R.string.info_caferev
            )
        }.attach()
    }

    private fun initFavoriteButton() {
        with(binding.btnFavorite) {
            bringToFront()
            setOnClickListener {
                Log.e("ivFavor", "onclick")
                val isLoggedIn = viewModel.signInStateFlow.value
                if (isLoggedIn) {
                    if (viewModel.isFavoriteCafe.value) {
                        viewModel.deleteFavoriteCafeAuth()
                    } else {
                        viewModel.postFavoriteCafeAuth()
                    }
                } else {
                    AlertDialog.Builder(requireContext())
                        .setMessage(resources.getString(R.string.need_login))
                        .setPositiveButton("확인") { dialog, which ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }
        }
    }

    private fun initViewModel(cafe: Cafe, isFavoriteCafe: Boolean) {
        viewModel.initViewModel(cafe, isFavoriteCafe)
    }
}