package com.tft.scrapbatch.scrapper.repository


import com.tft.scrapbatch.scrapper.entity.Item
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface ItemRepository : MongoRepository<Item, String>, QuerydslPredicateExecutor<Item>