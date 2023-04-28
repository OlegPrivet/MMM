package com.app.mmm.database.repository

interface Repository {

    suspend fun <T> runInTransaction(block: suspend () -> T): T
}
