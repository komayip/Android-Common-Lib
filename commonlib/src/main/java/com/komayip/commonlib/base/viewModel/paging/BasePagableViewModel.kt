package com.komayip.commonlib.base.viewModel.paging

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.komayip.commonlib.base.viewModel.BaseViewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.min

/**
 * abstract class of a View Model with pagination
 */
abstract class BasePagableViewModel<T>: BaseViewModel() {

    /**
     * the data source
     */
    abstract val ldDataSource: LiveData<PagedList<T?>>

    /**
     * refresh the paging source, ie, reset to initial page
     */
    fun refreshData() {
        ldDataSource.value?.dataSource?.invalidate()
    }

    open fun fetchExecutor(): ExecutorService {
        val threads = Runtime.getRuntime().availableProcessors() + 1
        return Executors.newFixedThreadPool(threads)
    }

}

/**
 * abstract class of view model which the pagination is with paged key, ie, page 1, 2 ... etc
 */
abstract class PagedKeyPagableViewModel<T>: BasePagableViewModel<T>() {

    /**
     * the data source list
     */
    override val ldDataSource: LiveData<PagedList<T?>>
        get() = dataSourceProvider()

    /**
     * the factory of creating the data source
     */
    private val factory = object: DataSource.Factory<Int, T>() {
        override fun create(): DataSource<Int, T> {
            return createDataSource()
        }
    }

    /**
     * the data source provider
     */
    private fun dataSourceProvider() = LivePagedListBuilder(factory, configBuilder().build())
            .setFetchExecutor(fetchExecutor())
            .build()

    abstract fun createDataSource(): DataSource<Int, T>

    abstract val pageSize: Int

    open fun configBuilder(): PagedList.Config.Builder {
        return PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setPrefetchDistance(min(5, pageSize.div(2)))
    }
}