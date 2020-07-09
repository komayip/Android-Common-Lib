package com.komayip.commonlib.base.viewModel.paging

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.komayip.commonlib.base.recyclerView.diffutil.paging.repo.BasePageKeyedDataSource
import com.komayip.commonlib.base.recyclerView.diffutil.paging.repo.BasePositionalDataSource
import com.komayip.commonlib.base.viewModel.BaseViewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.min

/**
 * abstract class of a View Model with pagination
 */
abstract class BasePagableViewModel<T> : BaseViewModel() {

    protected lateinit var mDataSource: DataSource<*, T>

    /**
     * the data source
     */
    abstract val ldDataSource: LiveData<PagedList<T?>>

    /**
     * refresh the paging source, ie, reset to initial page
     */
    private fun refreshData() {
        mDataSource.invalidate()
    }

    open fun fetchExecutor(): ExecutorService {
        val threads = Runtime.getRuntime().availableProcessors() + 1
        return Executors.newFixedThreadPool(threads)
    }
}

/**
 * abstract class of view model which the pagination is with paged key, ie, page 1, 2 ... etc
 */
abstract class PageKeyedPagableViewModel<T> : BasePagableViewModel<T>() {

    /**
     * the data source list
     */
    override val ldDataSource: LiveData<PagedList<T?>>
        get() = dataSourceProvider()

    /**
     * the factory of creating the data source
     */
    private val factory = object : DataSource.Factory<Int, T>() {
        override fun create(): DataSource<Int, T> {
            mDataSource = createDataSource()
            return mDataSource as BasePageKeyedDataSource<T>
        }
    }

    /**
     * the data source provider
     */
    private fun dataSourceProvider() = LivePagedListBuilder(factory, configBuilder().build())
        .setFetchExecutor(fetchExecutor())
        .build()

    abstract fun createDataSource(): BasePageKeyedDataSource<T>

    abstract val pageSize: Int

    open fun configBuilder(): PagedList.Config.Builder {
        return PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setPrefetchDistance(min(5, pageSize.div(2)))
    }
}

/**
 * abstract class of view model which the pagination is with offset & page size
 */
abstract class PositionPagableViewModel<T> : BasePagableViewModel<T>() {

    /**
     * the data source list
     */
    override val ldDataSource: LiveData<PagedList<T?>>
        get() = dataSourceProvider()

    /**
     * the factory of creating the data source
     */
    private val factory = object : DataSource.Factory<Int, T>() {
        override fun create(): DataSource<Int, T> {
            mDataSource = createDataSource()
            return mDataSource as BasePositionalDataSource<T>
        }
    }

    /**
     * the data source provider
     */
    private fun dataSourceProvider() = LivePagedListBuilder(factory, configBuilder().build())
        .setFetchExecutor(fetchExecutor())
        .build()

    abstract fun createDataSource(): BasePositionalDataSource<T>

    abstract val pageSize: Int

    open fun configBuilder(): PagedList.Config.Builder {
        return PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setPrefetchDistance(min(5, pageSize.div(2)))
    }
}