package com.yundom.kache.config

sealed class Policy
object LRU : Policy()
object FIFO : Policy()