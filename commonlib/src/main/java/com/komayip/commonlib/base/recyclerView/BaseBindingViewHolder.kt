package com.komayip.commonlib.base.recyclerView

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable

/**
 * Abstract RecyclerViewHolder For Data Binding
 */
abstract class BaseBindingViewHolder<T>(open val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    val disposeBag = CompositeDisposable()

    /**
     *  bind view model here
     */
    abstract fun bind(data: T)

    /**
     * add th bind event here, onclick or rxjava, which should be something view holder independent
     */
    abstract fun bindEvent()

    /**
     *  execute in onBindViewHolder
     */
    open fun bindExecution(data: T) {
        bind(data)
        // forces the bindings to run immediately
        binding.executePendingBindings()
    }
}