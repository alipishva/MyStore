package com.arp.mynikestore.common

import com.arp.mynikestore.R
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber

class NikeExceptionMapper {

    companion object {
        fun map(throwable : Throwable) : NikeException {
            if (throwable is HttpException) {
                try {
                    val errorJsonObject = JSONObject(throwable.response()?.errorBody() !!.string())
                    val errorMessage = errorJsonObject.getString("error")
                    return NikeException(if (throwable.code() == 401) NikeException.Type.AUTH else NikeException.Type.SIMPLE , serverMessage = errorMessage)

                } catch (exception : Exception) {
                    Timber.e(exception)
                }
            }
            return NikeException(NikeException.Type.SIMPLE , R.string.unknown_error)

        }
    }

}