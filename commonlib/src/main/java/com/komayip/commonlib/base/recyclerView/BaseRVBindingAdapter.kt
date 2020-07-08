package com.komayip.commonlib.base.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Abstract class of a recyclerview with data binding
 *
 * VH: View HolderClass extending BaseBindingViewHolder
 * T: the data class type
 */
abstract class BaseRVBindingAdapter<T, VH : BaseBindingViewHolder<T>>  : RecyclerView.Adapter<VH>() {

    private val mOnCellClickSubject = PublishSubject.create<T>()
    protected val dataSource = arrayListOf<T>()

    /**
     * to create the data binding of the recycler view layout
     */
    protected fun <B: ViewDataBinding> createBinding(layoutRes: Int, parent: ViewGroup): B {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutRes,
            parent,
            false
        )
    }

    /**
     * subscribe to observe on cell clicked event
     */
    fun onCellClicked(): Observable<T> {
        return mOnCellClickSubject
    }

    override fun getItemCount(): Int = dataSource.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        itemAt(position)?.let {
            holder.bindExecution(it)
        }
    }

    open fun itemAt(position: Int): T? {
        if (position >= dataSource.size) {
            return null
        }

        return dataSource[position]
    }

    /**
     * append single data source
     */
    open fun append(data: T) {
        append(listOf(data))
    }

    /**
     * append list of source
     */
    open fun append(data: List<T>) {
        dataSource.addAll(data)
    }

    /**
     * set current source with another source
     */
    open fun set(data: List<T>) {
        dataSource.clear()
        append(data)
        notifyDataSetChanged()
    }

    /**
     * clear data
     */
    open fun clear() {
        dataSource.clear()
        notifyDataSetChanged()
    }
}