@file:Suppress("PackageDirectoryMismatch")

package androidx.lifecycle


import com.products.arch.extensions.LoadingAware
import com.products.core.domain.error.ErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private const val LOADING_FLOW_KEY = "androidx.lifecycle.LoadingFlow"
val errorMessage by lazy { MutableStateFlow(ErrorMessage(-1, "")) }


val <T> T.loadingFlow: StateFlow<Boolean> where T : LoadingAware, T : ViewModel
    get() {
        return loadingMutableStateFlow
    }

private val <T> T.loadingMutableStateFlow: MutableStateFlow<Boolean> where T : LoadingAware, T : ViewModel
    get() {
        val flow: MutableStateFlow<Boolean>? = getTag(LOADING_FLOW_KEY)
        return flow ?: setTagIfAbsent(LOADING_FLOW_KEY, MutableStateFlow(false))
    }

