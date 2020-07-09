package com.komayip.commonlib.base.recyclerView.diffutil.paging

/**
 * Interface must be implemented when using {@link DiffUtilRecyclerViewAdapter}
 */
interface PagingComparable<in T> {
    /**
     * function used in DiffUtil#areItemsTheSame
     */
    fun isSameItemWith(other: T): Boolean

    /**
     * function used in DiffUtil#areContentsTheSame
     */
    fun isSameContentWith(other: T): Boolean
}