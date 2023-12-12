package org.cazait.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun Fragment.showToast(message: String) =
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

fun Fragment.launch(block: suspend CoroutineScope.() -> Unit): Job {
    return viewLifecycleOwner.lifecycleScope.launch { block() }
}