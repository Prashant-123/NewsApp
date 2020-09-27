package com.newsapp.utils

object ApiConstants {
    // Constant Query Params
    const val API_KEY = "ef7d3526a21346c2bdb05a24c2a01114"
    const val BASE_URL = "https://newsapi.org/v2/"
    const val RESULTS_PER_PAGE = "10"
    const val NEWS_COUNTRY = "in"

    // API Errors
    const val ERROR_400 = "The request was unacceptable, often due to a missing or misconfigured parameter."
    const val ERROR_401 = "Your API key was missing from the request, or wasn't correct."
    const val ERROR_429 = "You made too many requests within a window of time and have been rate limited. Back off for a while."
    const val ERROR_500 = "Server Error. Something went wrong on our side."

    const val TOP_HEADLINES = "top-headlines"
}