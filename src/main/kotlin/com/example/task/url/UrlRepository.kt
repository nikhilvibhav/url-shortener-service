package com.example.task.url

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UrlRepository : MongoRepository<Url, String>