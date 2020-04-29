package com.pretty.core.exception

class ApiException(errorCode: String, errorMessage: String?) : RuntimeException(errorMessage)