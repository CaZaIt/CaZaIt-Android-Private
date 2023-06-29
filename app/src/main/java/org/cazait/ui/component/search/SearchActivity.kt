package org.cazait.ui.component.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import org.cazait.R
import org.cazait.databinding.ActivitySearchBinding
import org.cazait.ui.base.BaseActivity


@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>(
    SearchViewModel::class.java,
    R.layout.activity_search
) {
    override fun initView() {
        viewModel.initLocation()
        Log.d("location", viewModel.locationLiveData.toString())
        setBackBtn()
        search()
        initEditText()
        val searchFrag = SearchCurFragment(viewModel)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.search_frag, searchFrag)
            .commit()
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
                // 키보드 자동으로 내림
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)

                binding.appBarLayoutSearch.setBackgroundColor(ContextCompat.getColor(this, R.color.main_white))

                val query = v.text.toString()
                Log.d("v", query)
                viewModel.getCafeSearchResult(query)

                val resultFrag = SearchResultFragment(viewModel)
                val bundle = Bundle()
                bundle.putString("SearchText", query)
                resultFrag.arguments = bundle
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.search_frag, resultFrag)
                    .commit()
                true
            } else {
                false
            }
        }
    }

    private fun initEditText(){
        binding.searchBarSearch.setOnClickListener {
            binding.appBarLayoutSearch.setBackgroundColor(ContextCompat.getColor(this, R.color.main_black))
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.search_frag, SearchCurFragment(viewModel))
                .commit()
        }
    }

    private fun setBackBtn() {
        binding.icArrowBack.setOnClickListener {
            finish()
        }
    }
}