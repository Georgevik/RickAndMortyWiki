package com.georgevik.rickandmorty.repository.webservices

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import android.util.Log
import com.georgevik.rickandmorty.repository.Resource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Resources provided by API calls
 * ResultType: Type for the resourceData => User, Character etc..
 * RequestType: Type for the API response => ResponseList<Character>
 */
abstract class NetworkResource<ResultType, RequestType> {
    /**
     * Called to save the data from API to the database
     */
    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    /**
     * Called with database data to decide if need to search new data from network
     */
    @WorkerThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    /**
     * Called to get the data from DB
     */
    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

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
    fun getAsLiveData(): LiveData<Resource<ResultType>> = result

    //region Class Implementation

    private val result: MediatorLiveData<Resource<ResultType>> = MediatorLiveData()
    private val TAG = NetworkResource::class.java.canonicalName

    /**
     * We have to decide if dbSource is ready. If not, we will listen the network source
     * to update the db one
     */
    constructor() {
        loadData()
    }

    fun loadData() {
        // Init value with loading data
        result.value = Resource.loading(result.value?.data)

        val dbSource = this.loadFromDb()
        result.addSource(dbSource) {
            if (it == null) {
                return@addSource
            }

            // Delete dbSource because we must change the listener OnChange
            result.removeSource(dbSource)
            if (this.shouldFetch(it)) {
                // DbSource is old so we need to updoad the source using the network
                this.fetchFromNetwork(dbSource)
            } else {
                // DbSource is update so, we just add again the dbSource with other listener
                result.addSource(dbSource) {
                    result.value = Resource.success(it!!)
                }
            }
        }
    }

    /**
     * Start with a resource loading with the data of database and fetch from network
     */
    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        // First of all return a data while we are fetching
        result.addSource(dbSource) {
            if (it != null) {
                Log.d(TAG, "fetchFromNetwork Loading value")
                result.value = Resource.loading(it)
            }
        }

        Log.d(TAG, "fetchFromNetwork Fetching...")
        createCall().enqueue(object : Callback<RequestType> {
            override fun onFailure(call: Call<RequestType>?, t: Throwable?) {
                onFetchFailed()

                // Change listener from DbSource
                result.removeSource(dbSource)
                result.addSource(dbSource) { newData ->
                    Log.d(TAG, "fetchFromNetwork errorData")
                    result.setValue(Resource.error(call?.toString() ?: "Unknown error", newData))
                }
            }

            override fun onResponse(call: Call<RequestType>?, response: Response<RequestType>?) {
                result.removeSource(dbSource)
                if (response?.body() != null) {
                    saveResultAndReInit(response.body()!!);
                }
            }

        })
    }

    /**
     * Save the result from response and a source from database
     */
    @MainThread
    private fun saveResultAndReInit(response: RequestType) {
        Observable.fromCallable { saveCallResult(response) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.d(TAG, "saveResultAndReInit addSourceDB")
                    result.addSource(loadFromDb()) { newData ->
                        Log.d(TAG, "saveResultAndReInit setValueFromDb")
                        result.setValue(Resource.success(newData!!))
                    }
                }
    }

    //endregion
}