package com.vtvhyundai.media2359demo.core

interface BaseMapper<in A, out B> {

    fun map(type: A?): B
}