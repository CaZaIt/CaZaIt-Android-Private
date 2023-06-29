package org.cazait.ui.component.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.FragmentSearchCurBinding
import org.cazait.model.Cafe
import org.cazait.model.Cafes
import org.cazait.model.Resource
import org.cazait.ui.adapter.ItemDecoration
import org.cazait.ui.adapter.SearchAdapter
import org.cazait.ui.component.cafeinfo.CafeInfoActivity
import org.cazait.ui.component.search.clicklistener.OnSearchClick
import org.cazait.utils.observe
import kotlin.math.roundToInt

@AndroidEntryPoint
class SearchCurFragment(
    private val viewModel: SearchViewModel
) : Fragment(), OnSearchClick {
    private lateinit var binding: FragmentSearchCurBinding
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchCurBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        observeViewModel()
    }

    private fun observeViewModel() {
        observe(viewModel.cafeSearchData, ::handleCafeSearch)
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

    private fun initAdapter() {
        searchAdapter = SearchAdapter(this)
        binding.rvSearch.adapter = searchAdapter
        binding.rvSearch.addItemDecoration(
            ItemDecoration(
                extraMargin = resources.getDimension(R.dimen.cafe_item_space).roundToInt()
            )
        )
    }

    override fun onSearchClick(item: Cafe) {
        val intent = CafeInfoActivity.cafeInfoIntent(requireContext(), item)
        startActivity(intent)
    }

}