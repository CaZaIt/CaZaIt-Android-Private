package org.cazait.ui.component.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentSearchResultBinding
import org.cazait.model.Cafe
import org.cazait.model.Cafes
import org.cazait.model.Resource
import org.cazait.ui.adapter.ItemDecoration
import org.cazait.ui.adapter.SearchResultAdapter
import org.cazait.ui.component.search.clicklistener.OnResultClick
import org.cazait.utils.observe
import kotlin.math.roundToInt

@AndroidEntryPoint
class SearchResultFragment(
    private val viewModel: SearchViewModel
) : Fragment(), OnResultClick {
    private lateinit var binding: FragmentSearchResultBinding
    private lateinit var resultAdapter: SearchResultAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchResultBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchText = arguments?.getString("SearchText")
        binding.searchText.text = searchText

        initAdapter()
        observeViewModel()
    }

    override fun onResultClick(item: Cafe) {
        navigateToCafeInfoFragment(cafe = item)
    }

    private fun navigateToCafeInfoFragment(cafe: Cafe) {
        findNavController().navigate(
            SearchCurFragmentDirections.actionSearchCurFragmentToCafeInfoFragment(
                cafe
            )
        )
    }

    private fun observeViewModel() {
        observe(viewModel.cafeSearchData, ::handleCafeSearchResult)
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

    private fun initAdapter() {
        resultAdapter = SearchResultAdapter(this)
        binding.rvSearchResult.adapter = resultAdapter
        binding.rvSearchResult.addItemDecoration(
            ItemDecoration(
                extraMargin = resources.getDimension(R.dimen.cafe_item_space).roundToInt()
            )
        )
    }
}