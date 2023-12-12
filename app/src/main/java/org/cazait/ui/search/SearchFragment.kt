package org.cazait.ui.search

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentSearchBinding
import org.cazait.core.model.cafe.Cafe
import org.cazait.core.model.cafe.Cafes
import org.cazait.core.model.cafe.FavoriteCafes
import org.cazait.core.model.Resource
import org.cazait.ui.adapter.ItemDecoration
import org.cazait.ui.adapter.SearchAdapter
import org.cazait.ui.adapter.SearchResultAdapter
import org.cazait.ui.base.BaseFragment
import org.cazait.ui.search.clicklistener.OnResultClick
import org.cazait.ui.search.clicklistener.OnSearchClick
import org.cazait.utils.observe
import org.cazait.utils.toGone
import org.cazait.utils.toVisible
import kotlin.math.roundToInt

@AndroidEntryPoint
class SearchFragment :
    BaseFragment<FragmentSearchBinding, SearchViewModel>(
        SearchViewModel::class.java,
        R.layout.fragment_search,
    ),
    OnSearchClick,
    OnResultClick {
    private val navArgs: SearchFragmentArgs by navArgs()
    private lateinit var favoriteCafes: FavoriteCafes
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var resultAdapter: SearchResultAdapter

    override fun initView() {
        favoriteCafes = navArgs.favoriteCafe
        viewModel.initLocation()
        // 키보드 자동으로 올림
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.searchBarSearch, 0)
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

    private fun setBackBtn() {
        binding.icArrowBack.setOnClickListener {
            navigateToCafeListFragment()
        }
    }

    private fun search() {
        onSearchTextChanged()
        onSearchDone()
    }

    private fun onSearchTextChanged() {
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
    }

    private fun onSearchDone() {
        binding.searchBarSearch.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = v.text.toString()
                viewModel.getCafeSearch(query)

                convertToSearchResult()
                binding.searchText.text = query

                // 키보드 자동으로 내림
                val inputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)

                binding.appBarLayoutSearch.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.main_white,
                    ),
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
                    R.color.main_black,
                ),
            )
            convertToSearchCur()
        }
    }

    private fun initCurAdapter() {
        searchAdapter = SearchAdapter(this)
        binding.rvSearch.adapter = searchAdapter
        binding.rvSearch.addItemDecoration(
            ItemDecoration(
                extraMargin = resources.getDimension(R.dimen.cafe_item_space).roundToInt(),
            ),
        )
        searchAdapter.submitList(null)
    }

    private fun initResultAdapter() {
        resultAdapter = SearchResultAdapter(this)
        binding.rvSearchResult.adapter = resultAdapter
        binding.rvSearchResult.addItemDecoration(
            ItemDecoration(
                extraMargin = resources.getDimension(R.dimen.cafe_item_space).roundToInt(),
            ),
        )
        resultAdapter.submitList(null)
    }

    private fun observeViewModel() {
        observe(viewModel.cafeSearchData, ::handleCafeSearch)
    }

    private fun handleCafeSearch(status: Resource<Cafes>) {
        when (status) {
            is Resource.Error -> {}
            is Resource.Loading -> {}
            is Resource.Success -> {
                val cafes = status.data?.cafes ?: emptyList()
                viewModel.updateFavoriteStatus(favoriteCafes.cafes, cafes)
                searchAdapter.submitList(cafes)
                resultAdapter.submitList(cafes)
            }
        }
    }

    private fun convertToSearchCur() {
        binding.layoutSearchResult.toGone()
        binding.rvSearch.toVisible()
    }

    private fun convertToSearchResult() {
        binding.rvSearch.toGone()
        binding.layoutSearchResult.toVisible()
    }

    private fun navigateToCafeListFragment() {
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToCafeListFragment())
    }

    private fun navigateToCafeInfoFragment(cafe: Cafe) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToCafeInfoFragment(
                cafe,
            ),
        )
    }

    override fun onResultClick(item: Cafe) {
        navigateToCafeInfoFragment(item)
    }

    override fun onSearchClick(item: Cafe) {
        navigateToCafeInfoFragment(item)
    }
}
