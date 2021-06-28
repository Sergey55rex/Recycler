package ru.netology.nmedia.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl
import javax.inject.Inject
import javax.inject.Singleton

class RemovePostWorker (
    applicationContext: Context,
    params: WorkerParameters,
    private val repository: PostRepository,
) : CoroutineWorker(applicationContext, params) {
    companion object {
        const val postKey = "post"
    }

    override suspend fun doWork(): Result {
        val id = inputData.getLong(postKey, 0L)
        if (id == 0L) {
            return Result.failure()
        }

////        val dbpw = AppDb.getInstance(context = applicationContext).postWorkDao()
////        val repository: PostRepository =
////            PostRepositoryImpl(
////                AppDb.getInstance(context = applicationContext).postDao(),
////                dbpw ,
////          )
        return try {
            Log.e("exc", "R WORKER doWork for ${id}")
            repository.removeById(id)
            Log.e("exc", "R WORKER DONE")
//            dbpw.removeById(id)

            Result.success()

        } catch (e: Exception) {            Log.e("exc", "R WORKER Exception!!")
            Result.retry()
        } catch (e: UnknownError) {
            Log.e("exc", "R WORKER failure")
            Result.failure()
        }
    }

}

@Singleton
class RemovePostsWorkerFactory @Inject constructor(
    private val repository: PostRepository,
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? = when (workerClassName) {
        RemovePostWorker::class.java.name ->
            RemovePostWorker(appContext, workerParameters, repository)
        else ->
            null
    }
}