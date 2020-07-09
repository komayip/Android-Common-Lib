package com.komayip.commonlib.base.recyclerView.diffutil.paging.repo

import androidx.paging.PositionalDataSource
import com.komayip.commonlib.ext.disposedBy
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

/**
 * the data source of loading data from a to b of a range
 */
abstract class BasePositionalDataSource<T> : PositionalDataSource<T>() {

    // implement data source api or dao etc
    /**
     * first: data list
     * second: offset
     * third: total
     */
    abstract fun queryData(start: Int, pageSize: Int): Observable<Triple<List<T>, Int, Int?>>

    private val disposeBag = CompositeDisposable()

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
        getData(params.startPosition, params.loadSize)
            .doOnNext {
                callback.onResult(it.first)
            }
            .subscribe()
            .disposedBy(disposeBag)
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        getData(params.requestedStartPosition, params.requestedLoadSize)
            .doOnNext { resp ->
                resp.third?.let {
                    callback.onResult(resp.first, resp.second, it)
                } ?: callback.onResult(resp.first, resp.second)
            }
            .subscribe()
            .disposedBy(disposeBag)
    }

    private fun getData(start: Int, pageSize: Int): Observable<Triple<List<T>, Int, Int?>> {
        return queryData(start, pageSize).onErrorReturnItem(Triple(listOf(), 0, null))
    }

}