package org.cazait.ui.component.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentSearchBinding
import org.cazait.model.Cafe
import org.cazait.model.Cafes
import org.cazait.model.Resource
import org.cazait.ui.adapter.ItemDecoration
import org.cazait.ui.adapter.SearchAdapter
import org.cazait.ui.adapter.SearchResultAdapter
import org.cazait.ui.base.BaseFragment
import org.cazait.ui.component.search.clicklistener.OnResultClick
import org.cazait.ui.component.search.clicklistener.OnSearchClick
import org.cazait.utils.observe
import org.cazait.utils.toGone
import org.cazait.utils.toVisible
import kotlin.math.roundToInt

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(
    SearchViewModel::class.java,
    R.layout.fragment_search
), OnSearchClick, OnResultClick {
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var resultAdapter: SearchResultAdapter
    override fun initView() {
        viewModel.initLocation()
        Log.d("location", viewModel.locationLiveData.toString())
        setBackBtn()
        search()
        initEditText()
        initCurAdapter()
        initResultAdapter()
        observeViewModel()
    }

    override fun initAfterBinding() {

    }

    private fun search() {
        binding.searchBarSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                val query = s.toString()
                viewModel.getCafeSearch(query)
            }
        })

        binding.searchBarSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = v.text.toString()
                viewModel.getCafeSearchResult(query)

                binding.rvSearch.toGone()
                binding.layoutSearchResult.toVisible()
                binding.searchText.text = query

                // 키보드 자동으로 내림
                val inputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)

                binding.appBarLayoutSearch.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.main_white
                    )
                )
                true
            } else {
                false
            }
        }
    }

    private fun initEditText() {
        binding.searchBarSearch.setOnClickListener {
            binding.appBarLayoutSearch.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.main_black
                )
            )
            binding.layoutSearchResult.toGone()
            binding.rvSearch.toVisible()
        }
    }

    private fun observeViewModel() {
        observe(viewModel.cafeSearchData, ::handleCafeSearch)
        observe(viewModel.cafeSearchData, ::handleCafeSearchResult)
    }

    private fun handleCafeSearch(status: Resource<Cafes>) {
        when (status) {
            is Resource.Error -> {}
            is Resource.Loading -> {}
            is Resource.Success -> {
                val name = status.data?.list
                searchAdapter.submitList(name)
            }
        }
    }

    private fun handleCafeSearchResult(status: Resource<Cafes>) {
        when (status) {
            is Resource.Error -> {}
            is Resource.Loading -> {}
            is Resource.Success -> {
                val cafes = status.data?.list
                resultAdapter.submitList(cafes)
            }
        }
    }

    private fun setBackBtn() {
        binding.icArrowBack.setOnClickListener {
            navigateToCafeListFragment()
        }
    }

    private fun navigateToCafeListFragment() {
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToCafeListFragment())
    }

    private fun navigateToCafeInfoFragment(cafe: Cafe) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToCafeInfoFragment(
                cafe
            )
        )
    }

    private fun initCurAdapter() {
        searchAdapter = SearchAdapter(this)
        binding.rvSearch.adapter = searchAdapter
        binding.rvSearch.addItemDecoration(
            ItemDecoration(
                extraMargin = resources.getDimension(R.dimen.cafe_item_space).roundToInt()
            )
        )
    }

    private fun initResultAdapter() {
        resultAdapter = SearchResultAdapter(this)
        binding.rvSearchResult.adapter = resultAdapter
        binding.rvSearchResult.addItemDecoration(
            ItemDecoration(
                extraMargin = resources.getDimension(R.dimen.cafe_item_space).roundToInt()
            )
        )
    }

    override fun onResultClick(item: Cafe) {
        navigateToCafeInfoFragment(item)
    }

    override fun onSearchClick(item: Cafe) {
        navigateToCafeInfoFragment(item)
    }
}