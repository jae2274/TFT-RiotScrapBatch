package com.tft.scrapbatch.scrapper.repository


import com.tft.scrapbatch.scrapper.entity.Champion
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface ChampionRepository : MongoRepository<Champion, String>, QuerydslPredicateExecutor<Champion>