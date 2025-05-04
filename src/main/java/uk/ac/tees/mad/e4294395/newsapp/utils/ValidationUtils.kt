package uk.ac.tees.mad.e4294395.newsapp.utils

object ValidationUtils {
    fun checkIfValidArgNews(str: String?): Boolean {
        return str?.let {
            it !in listOf(null, " ", "{countryId}", "{langId}", "{sourceId}")
        } ?: false
    }
}