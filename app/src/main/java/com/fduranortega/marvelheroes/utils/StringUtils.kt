package com.fduranortega.marvelheroes.utils

const val EMPTY_STRING = ""
const val HTTP_LABEL = "http:"
const val HTTPS_LABEL = "https:"


fun String.convertHttpToHttps(): String {
    val fixedHttp = if (this.startsWith(HTTPS_LABEL)) {
        this
    } else {
        this.replace(HTTP_LABEL, HTTPS_LABEL)
    }
    return fixedHttp
}