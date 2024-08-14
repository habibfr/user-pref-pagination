import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.habibfr.suitmedia_test_mobile.data.remote.api.response.User
import com.habibfr.suitmedia_test_mobile.data.remote.api.retrofit.ApiService

class UserPagingSource(private val apiService: ApiService) : PagingSource<Int, User>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getUsers(position, params.loadSize)
            println("SISEEE" + responseData.data.size)
            LoadResult.Page(
                data = responseData.data,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.data.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}