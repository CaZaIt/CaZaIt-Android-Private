package org.cazait.ui.component.recentlycafe


import androidx.lifecycle.LiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.cazait.database.dao.RecentlyViewedCafeDao
import org.cazait.database.model.entity.RecentlyViewedCafe
import org.cazait.ui.base.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class RecentlyCafeViewModel @Inject constructor(
    private val recentlyViewedCafeDao: RecentlyViewedCafeDao
) : BaseViewModel() {
    fun getAllRecentlyViewedCafes(): LiveData<List<RecentlyViewedCafe>> {
        return recentlyViewedCafeDao.getAllRecentlyViewedCafes()
    }
}