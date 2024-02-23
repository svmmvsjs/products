package com.products.core.pagination.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.products.core.data.repositories.ProductRepository
import com.products.core.pagination.db.AppDatabase
import com.products.core.pagination.model.Product
import com.products.core.pagination.model.RemoteKeys
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@ExperimentalPagingApi
class ProductMediator(
    private val productRepository: ProductRepository,
    private val appDatabase: AppDatabase
) :
    RemoteMediator<Int, Product>() {
    companion object {
        const val DEFAULT_PAGE_INDEX = 2
        const val DEFAULT_PAGE_SIZE = 10
    }

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, Product>
    ): MediatorResult {

        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> {
                return pageKeyData
            }

            else -> {
                pageKeyData as Int
            }
        }

        try {
            val response = productRepository.getProducts(page, state.config.pageSize)
            val isEndOfList = response.isEmpty()
            appDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    appDatabase.getRepoDao().clearRemoteKeys()
                    appDatabase.getProductDao().clearAllProducts()
                }
                val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = response.map {
                    RemoteKeys(repoId = it.id.toString(), prevKey = prevKey, nextKey = nextKey)
                }
                appDatabase.getRepoDao().insertAll(keys)
                appDatabase.getProductDao().insertAll(response)
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    /**
     * this returns the page key or the final end of list success result
     */
    private suspend fun getKeyPageData(
        loadType: LoadType,
        state: PagingState<Int, Product>
    ): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: DEFAULT_PAGE_INDEX
            }

            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                if (remoteKeys == null)
                    DEFAULT_PAGE_INDEX
                else remoteKeys.nextKey
                    ?: throw InvalidObjectException("Remote key should not be null for $loadType")
            }

            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
        }
    }

    /**
     * get the last remote key inserted which had the data
     */
    private suspend fun getLastRemoteKey(state: PagingState<Int, Product>): RemoteKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { product ->
                appDatabase.getRepoDao().remoteKeysResourceId(product.id.toString())
            }
    }

    /**
     * get the closest remote key inserted which had the data
     */
    private suspend fun getClosestRemoteKey(state: PagingState<Int, Product>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                appDatabase.getRepoDao().remoteKeysResourceId(repoId.toString())
            }
        }
    }

}