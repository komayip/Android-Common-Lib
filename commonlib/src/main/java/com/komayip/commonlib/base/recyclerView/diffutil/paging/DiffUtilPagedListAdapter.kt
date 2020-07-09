package com.komayip.commonlib.base.recyclerView.diffutil.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.komayip.commonlib.base.recyclerView.BaseBindingViewHolder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

abstract class DiffUtilPagedListAdapter<T : PagingComparable<T>, VH : BaseBindingViewHolder<T>> :
    PagedListAdapter<T, VH>(DiffUtilItemCallbackBuilder.build<T>()) {

    private val mOnCellClickSubject = PublishSubject.create<T>()

    /**
     * to create the data binding of the recycler view layout
     */
    protected fun <B : ViewDataBinding> createBinding(layoutRes: Int, parent: ViewGroup): B {
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

    override fun onBindViewHolder(holder: VH, position: Int) {
        getItem(position)?.let { item ->
            holder.bindExecution(item)
            // add an on item click listener
            holder.itemView.setOnClickListener {
                mOnCellClickSubject.onNext(item)
            }
        }
    }
}

private class DiffUtilItemCallbackBuilder private constructor() {
    companion object {
        fun <T : PagingComparable<T>> build(): DiffUtil.ItemCallback<T?> {
            return object : DiffUtil.ItemCallback<T?>() {
                override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
                    return oldItem.isSameItemWith(newItem)
                }

                override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
                    return oldItem.isSameContentWith(newItem)
                }
            }
        }
    }
}