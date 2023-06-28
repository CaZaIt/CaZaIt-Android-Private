package org.cazait.ui.component.search

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.ActivitySearchBinding
import org.cazait.model.Cafes
import org.cazait.model.Resource
import org.cazait.ui.adapter.ItemDecoration
import org.cazait.ui.adapter.SearchAdapter
import org.cazait.ui.base.BaseActivity
import org.cazait.utils.observe
import kotlin.math.roundToInt

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>(
    SearchViewModel::class.java,
    R.layout.activity_search
) {
    private lateinit var searchAdapter: SearchAdapter
    override fun initView() {
        viewModel.initLocation()
        Log.d("location", viewModel.locationLiveData.toString())
        initAdapter()
        setBackBtn()
        search()
        observeViewModel()
    }

    override fun initAfterBinding() {

    }

    private fun search(){
        binding.searchBarSearch.addTextChangedListener(object: TextWatcher{
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

    private fun observeViewModel() {
        observe(viewModel.cafeSearchData, ::handleCafeSearch)
    }

    private fun handleCafeSearch(status: Resource<Cafes>){
        when(status){
            is Resource.Error -> {}
            is Resource.Loading -> {}
            is Resource.Success -> {
                val name = status.data?.list
                searchAdapter.submitList(name)
            }
        }
    }

    private fun initAdapter() {
        searchAdapter = SearchAdapter()
        binding.rvSearch.adapter = searchAdapter
        binding.rvSearch.addItemDecoration(
            ItemDecoration(
                extraMargin = resources.getDimension(R.dimen.cafe_item_space).roundToInt()
            )
        )
    }

    private fun setBackBtn(){
        binding.icArrowBack.setOnClickListener {
            finish()
        }
    }
}