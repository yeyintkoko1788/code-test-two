package com.yeyint.movieapp.common

import androidx.room.TypeConverter

class Converter {

    @TypeConverter
    fun fromArray(strings: List<String>) : String {
        var string = ""
        for(s in strings) {
            string += ("$s, ");
        }

        return string;
    }

    @TypeConverter
    fun toArray(concatenatedStrings: String) : List<String> {
        val myStrings  = emptyList<String>().toMutableList()

        for(s in concatenatedStrings.split(",") ){
            myStrings += ("$s, ");
        }

        return myStrings.toList()
    }
}
