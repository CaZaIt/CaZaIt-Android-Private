package org.cazait.ui.base

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<T : ViewDataBinding, R : BaseViewModel>(
    private val viewModelClass: Class<R>,
    @LayoutRes private val layoutResourceId: Int,
) : AppCompatActivity() {

    lateinit var binding: T
    lateinit var viewModel: R

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        initViewModel()

        initView()
        initAfterBinding()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, layoutResourceId)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[viewModelClass]
    }

    /**
     * initiate view and click event
     */
    abstract fun initView()

    /**
     * initiate others (ex. observe Livedata)
     */
    abstract fun initAfterBinding()

    /**
     * 키보드 위 빈 공간을 터치하면 키보드가 사라지도록 한다
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val focusView: View? = currentFocus
        if (focusView != null) {
            val rect = Rect()
            focusView.getGlobalVisibleRect(rect)
            val x = ev.x.toInt()
            val y = ev.y.toInt()
            if (!rect.contains(x, y)) {
                val imm: InputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(focusView.windowToken, 0)
                focusView.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}
