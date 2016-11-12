/*
 * Copyright (c) 2015 Hannes Dorfmann.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.wang.baseadapter.delegate;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.wang.baseadapter.model.RecyclerViewItemArray;


public class AdapterDelegatesManager {


    /**
     * Map for ViewType to AdapterDeleage
     */
    private SparseArrayCompat<AdapterDelegate> delegates = new SparseArrayCompat<>();

    /**
     * Adds an {@link AdapterDelegate}.
     * <b>This method automatically assign internally the view type integer by using the next
     * unused</b>
     * <p>
     * Internally calls {@link #addDelegate(int, boolean, AdapterDelegate)} with
     * allowReplacingDelegate = false as parameter.
     *
     * @param delegate the delegate to add
     * @return self
     * @throws NullPointerException if passed delegate is null
     * @see #addDelegate(int, AdapterDelegate)
     * @see #addDelegate(int, boolean, AdapterDelegate)
     */
    @Deprecated
    public AdapterDelegatesManager addDelegate(@NonNull AdapterDelegate delegate) {

        int viewType = delegates.size();
        while (delegates.get(viewType) != null) {
            viewType++;
        }
        return addDelegate(viewType, false, delegate);
    }

    /**
     * Adds an {@link AdapterDelegate} with the specified view type.
     * <p>
     * Internally calls {@link #addDelegate(int, boolean, AdapterDelegate)} with
     * allowReplacingDelegate = false as parameter.
     *
     * @param viewType the view type integer if you want to assign manually the view type. Otherwise
     *                 use {@link #addDelegate(AdapterDelegate)} where a viewtype will be assigned manually.
     * @param delegate the delegate to add
     * @return self
     * @throws NullPointerException if passed delegate is null
     * @see #addDelegate(AdapterDelegate)
     * @see #addDelegate(int, boolean, AdapterDelegate)
     */
    public AdapterDelegatesManager addDelegate(int viewType, @NonNull AdapterDelegate delegate) {
        return addDelegate(viewType, false, delegate);
    }

    /**
     * Adds an {@link AdapterDelegate}.
     *
     * @param viewType               The viewType id
     * @param allowReplacingDelegate if true, you allow to replacing the given delegate any previous
     *                               delegate for the same view type. if false, you disallow and a {@link IllegalArgumentException}
     *                               will be thrown if you try to replace an already registered {@link AdapterDelegate} for the
     *                               same view type.
     * @param delegate               The delegate to add
     * @throws IllegalArgumentException if <b>allowReplacingDelegate</b>  is false and an {@link
     *                                  AdapterDelegate} is already added (registered)
     *                                  with the same ViewType.
     * @see #addDelegate(AdapterDelegate)
     * @see #addDelegate(int, AdapterDelegate)
     */
    public AdapterDelegatesManager addDelegate(int viewType, boolean allowReplacingDelegate, @NonNull AdapterDelegate delegate) {

        if (delegate == null) {
            throw new NullPointerException("AdapterDelegate is null!");
        }

        if (!allowReplacingDelegate && delegates.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An AdapterDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered AdapterDelegate is "
                            + delegates.get(viewType));
        }

        delegates.put(viewType, delegate);

        return this;
    }

    /**
     * Removes a previously registered delegate if and only if the passed delegate is registered
     * (checks the reference of the object). This will not remove any other delegate for the same
     * viewType (if there is any).
     *
     * @param delegate The delegate to remove
     * @return self
     */
    public AdapterDelegatesManager removeDelegate(@NonNull AdapterDelegate delegate) {

        if (delegate == null) {
            throw new NullPointerException("AdapterDelegate is null");
        }

        int indexToRemove = delegates.indexOfValue(delegate);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    /**
     * Removes the adapterDelegate for the given view types.
     *
     * @param viewType The Viewtype
     * @return self
     */
    public AdapterDelegatesManager removeDelegate(int viewType) {
        delegates.remove(viewType);
        return this;
    }


    /**
     * This method must be called in {@link RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)}
     *
     * @param parent   the parent
     * @param viewType the view type
     * @return The new created ViewHolder
     * @throws NullPointerException if no AdapterDelegate has been registered for ViewHolders
     *                              viewType
     */
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AdapterDelegate delegate = delegates.get(viewType);
        if (delegate == null) {
            throw new NullPointerException("No AdapterDelegate added for ViewType " + viewType);
        }

        RecyclerView.ViewHolder vh = delegate.onCreateViewHolder(parent, viewType);
        if (vh == null) {
            throw new NullPointerException("ViewHolder returned from AdapterDelegate "
                    + delegate
                    + " for ViewType ="
                    + viewType
                    + " is null!");
        }
        return vh;
    }

    /**
     * Must be called from{@link RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int)}
     *
     * @param itemArray Adapter's data source
     * @param vh    the ViewHolder to bind
     * @param position  the position in data source  @throws NullPointerException if no AdapterDelegate has been registered for ViewHolders
     *                  viewType
     */
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(RecyclerViewItemArray itemArray, RecyclerView.ViewHolder vh, int position) {
        getDelegateForViewType(vh.getItemViewType()).onBindViewHolder(itemArray, vh, position);
    }


    /**
     * Get the {@link AdapterDelegate} associated with the given view type integer
     *
     * @param viewType The view type integer we want to retrieve the associated
     *                 delegate for.
     * @return The {@link AdapterDelegate} associated with the view type param if it exists,
     * the fallback delegate otherwise if it is set.
     * @throws NullPointerException if no delegates are associated with this view type
     *                              and if no fallback delegate is set.
     */
    @NonNull
    public AdapterDelegate getDelegateForViewType(int viewType) {
        AdapterDelegate delegate = delegates.get(viewType);
        if (delegate == null) {
            throw new NullPointerException("No AdapterDelegate added for ViewType " + viewType);
        }
        return delegate;
    }
}
