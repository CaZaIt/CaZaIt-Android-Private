package org.cazait.ui.component.search

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        setBackBtn()

        // Verify the action and get the query
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
//                doMySearch(query)
            }
        }
    }

    override fun initAfterBinding() {

    }

    private fun setBackBtn(){
        binding.icArrowBack.setOnClickListener {
            finish()
        }
    }
}