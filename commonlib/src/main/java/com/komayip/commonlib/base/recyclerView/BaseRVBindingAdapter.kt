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
    protected val mDataSource = arrayListOf<T>()

    abstract fun itemAt(position: Int): T

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
    protected fun onCellClicked(): Observable<T> {
        return mOnCellClickSubject
    }

    override fun getItemCount(): Int = mDataSource.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindExecution(itemAt(position))
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
        mDataSource.addAll(data)
    }

    /**
     * set current source with another source
     */
    open fun set(data: List<T>) {
        mDataSource.clear()
        append(data)
        notifyDataSetChanged()
    }

    /**
     * clear data
     */
    open fun clear() {
        mDataSource.clear()
        notifyDataSetChanged()
    }
}