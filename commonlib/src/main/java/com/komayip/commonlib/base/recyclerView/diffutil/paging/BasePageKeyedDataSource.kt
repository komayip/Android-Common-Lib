package com.komayip.commonlib.base.recyclerView.diffutil.paging

import androidx.paging.PageKeyedDataSource
import com.komayip.commonlib.ext.disposedBy
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class BasePageKeyedDataSource<T> : PageKeyedDataSource<Int, T>() {

    // indicate the source is zero based, 0 if true, 1 otherwise
    abstract val isZeroBased: Boolean

    // implement data source api or dao etc
    abstract fun queryData(page: Int): Observable<List<T>>

    private val disposeBag = CompositeDisposable()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) {
        val page = if (isZeroBased) {
            0
        } else {
            1
        }

        getData(page)
            .doOnNext {
                callback.onResult(it, null, page.inc())
            }
            .subscribe()
            .disposedBy(disposeBag)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        val page = params.key
        getData(page)
            .doOnNext {
                callback.onResult(it, page.inc())
            }
            .subscribe()
            .disposedBy(disposeBag)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        val page = params.key
        getData(page)
            .doOnNext {
                callback.onResult(it, page.dec())
            }
            .subscribe()
            .disposedBy(disposeBag)
    }

    private fun getData(current: Int): Observable<List<T>> {
        return queryData(current).onErrorReturnItem(listOf())
    }
}