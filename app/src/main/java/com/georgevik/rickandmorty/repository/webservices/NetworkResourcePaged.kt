package com.georgevik.rickandmorty.repository.webservices

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call


/**
 * Resources provided by API calls
 * ResultType: Type for the resourceData => User, Character etc..
 * RequestType: Type for the API response => ResponseList<Character>
 */
abstract class NetworkResourcePaged<ResultType, RequestType> {
    /**
     * Called to save the data from API to the database
     */
    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    /**
     * Called to get the data from DB
     */
    @MainThread
    protected abstract fun loadFromDb(): DataSource.Factory<Int, ResultType>

    /**
     * Called to create the API call
     */
    @MainThread
    protected abstract fun createCall(): Call<RequestType>

    /**
     * Called when fetch fails
     */
    @MainThread
    protected fun onFetchFailed() {
    }

    /**
     * Return the LiveData that represent the resource implemented
     */
    fun getAsLiveData(): LiveData<PagedList<ResultType>> = result

    //region Class Implementation

    private val result: MediatorLiveData<PagedList<ResultType>> = MediatorLiveData()
    private val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val hasError: MutableLiveData<String> = MutableLiveData()
    private val TAG = NetworkResourcePaged::class.java.canonicalName
    var pageSize: Int = 10

    /**
     * We have to decide if dbSource is ready. If not, we will listen the network source
     * to update the db one
     */
    constructor() {
        isLoading.value = false

        Log.d(TAG, "Loading data")
        hasError.value = null
        isLoading.value = true

        val dbSource = getLivePagedFromDb()

        result.addSource(dbSource) { actualData ->
            // Delete dbSource because we must change the listener OnChange
            isLoading.postValue(false)
            resultPipeDb(dbSource)
        }
    }

    private fun getLivePagedFromDb() = LivePagedListBuilder(this.loadFromDb(), pageSize).build()

    fun loadNextPage() {

        fetchFromNetwork(getLivePagedFromDb())
    }

    private fun resultPipeDb(dbSource: LiveData<PagedList<ResultType>>) {
        result.removeSource(dbSource)
        result.addSource(dbSource) { actualData ->
            Log.d(TAG, "resultPipeDb Update data")
            result.value = actualData
        }
    }

    /**
     * Start with a resource loading with the data of database and fetch from network
     */
    private fun fetchFromNetwork(dbSource: LiveData<PagedList<ResultType>>) {
        Log.d(TAG, "fetchFromNetwork Fetching...")
        Observable.fromCallable({
            val execute = createCall().execute()
            if (execute.isSuccessful && execute.body() != null) {
                saveCallResult(execute.body()!!)
            }
            return@fromCallable execute
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    if (!it.isSuccessful) {
                        onFetchFailed()
                        Log.d(TAG, "fetchFromNetwork errorData")
                        hasError.value = it?.toString() ?: "Unknown error"
                    }
                    isLoading.value = false
                }
    }

    //endregion
}