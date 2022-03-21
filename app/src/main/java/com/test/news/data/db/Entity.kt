package com.test.news.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["url"], unique = true)])
data class NewsEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "plainTitle") val plainTitle: String?,
    @ColumnInfo(name = "plainSubtitle") val plainSubtitle: String?,
    @ColumnInfo(name = "img") val img: String?,
    @ColumnInfo(name = "img_big") val img_big: String?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "date") val date: Long?,
    @ColumnInfo(name = "source_icon") val source_icon: String?,
    @ColumnInfo(name = "source") val source: String?,
    @ColumnInfo(name = "sponsored") val sponsored: Boolean?
)