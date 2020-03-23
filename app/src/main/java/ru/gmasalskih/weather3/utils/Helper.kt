package ru.gmasalskih.weather3.utils

import android.content.Context
import android.widget.Toast

const val DATE_PATTERN = "yyyy-MM-dd"

fun String.toast(context: Context){
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}