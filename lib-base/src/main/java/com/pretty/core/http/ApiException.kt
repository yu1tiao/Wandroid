package com.pretty.core.http

class ApiException(errorCode: String, errorMessage: String?) : RuntimeException(errorMessage)