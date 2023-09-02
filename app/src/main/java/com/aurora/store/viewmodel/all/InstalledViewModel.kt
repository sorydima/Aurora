package com.aurora.store.viewmodel.all

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.aurora.extensions.flushAndAdd
import com.aurora.store.data.RequestState
import com.aurora.store.data.event.BusEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.Locale

class InstalledViewModel(application: Application) : BaseAppsViewModel(application) {

    init {
        EventBus.getDefault().register(this)

        requestState = RequestState.Init
        observe()
    }

    override fun observe() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                appList.flushAndAdd(getFilteredApps())
                liveData.postValue(appList.sortedBy { it.displayName.lowercase(Locale.getDefault()) })
                requestState = RequestState.Complete
            } catch (e: Exception) {
                requestState = RequestState.Pending
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onEvent(event: BusEvent) {
        when (event) {
            is BusEvent.InstallEvent -> {
                updateListAndPost(event.packageName)
            }
            is BusEvent.UninstallEvent -> {
                updateListAndPost(event.packageName)
            }
            is BusEvent.Blacklisted -> {
                observe()
            }
            else -> {

            }
        }
    }

    private fun updateListAndPost(packageName: String) {
        //Remove from current list
        val updatedList = appList.filter {
            it.packageName != packageName
        }.toList()

        appList.flushAndAdd(updatedList)

        //Post new update list
        liveData.postValue(appList.sortedBy { it.displayName.lowercase(Locale.getDefault()) })
    }

    override fun onCleared() {
        EventBus.getDefault().unregister(this)
        super.onCleared()
    }
}
