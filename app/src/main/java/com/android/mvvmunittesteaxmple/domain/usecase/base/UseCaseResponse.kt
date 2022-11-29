package com.android.mvvmunittesteaxmple.domain.usecase.base

import com.android.mvvmunittesteaxmple.domain.model.ApiError


interface UseCaseResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(apiError: ApiError?)
}

