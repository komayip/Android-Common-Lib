package com.komayip.commonlib.base.recyclerView.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.komayip.commonlib.base.recyclerView.BaseBindingViewHolder
import com.komayip.commonlib.base.recyclerView.BaseRVBindingAdapter
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

abstract class DiffUtilRecyclerViewAdapter<T, VH : BaseBindingViewHolder<T>> :
    BaseRVBindingAdapter<T, VH>() {

    // the "new" list, just for comparison
    private var listDataNew: ArrayList<T> = arrayListOf()

    private var diffCallback: DiffUtil.Callback = object : DiffUtil.Callback() {
        /**
         * check if the items are the same, normally we check the id
         */
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return areItemsTheSame(itemAt(oldItemPosition), listDataNew[newItemPosition])
        }

        override fun getOldListSize(): Int {
            return dataSource.size
        }

        override fun getNewListSize(): Int {
            return listDataNew.size
        }

        /**
         * check if the content are the same, if the items are the same
         */
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return areContentsTheSame(itemAt(oldItemPosition), listDataNew[newItemPosition])
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return null
        }

    }

    override fun set(data: List<T>) {
        compareDataList(data)
    }

    override fun append(data: List<T>) {
        val newDataList: ArrayList<T> = arrayListOf()
        newDataList.addAll(dataSource)
        newDataList.addAll(data)
        compareDataList(newDataList)
    }

    abstract fun areItemsTheSame(oldItem: T?, newItem: T): Boolean

    abstract fun areContentsTheSame(oldItem: T?, newItem: T): Boolean

    private fun compareDataList(newList: List<T>) {
        listDataNew.clear()
        listDataNew.addAll(newList)

        synchronized(dataSource) {
            Observable.just(DiffUtil.calculateDiff(diffCallback))
                .observeOn(Schedulers.newThread())
                .doOnNext {
                    dataSource.clear()
                    dataSource.addAll(newList)
                    it.dispatchUpdatesTo(this)
                }
                .subscribe()
        }
    }

}