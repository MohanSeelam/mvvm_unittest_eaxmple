package com.android.mvvmunittesteaxmple.di

class ServiceFailureException @JvmOverloads constructor(
    @JvmField val errorCode: String,
    @JvmField val errorMessage: String = errorCode
) : RuntimeException() {
    override fun toString(): String {
        return "ServiceFailureException(errorCode='$errorCode', errorMessage='$errorMessage')"
    }
}