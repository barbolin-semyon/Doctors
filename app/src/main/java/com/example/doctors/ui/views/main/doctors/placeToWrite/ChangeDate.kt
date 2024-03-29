package com.example.doctors.ui.views.main.doctors.placeToWrite

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doctors.ui.components.ArrowsBtn
import com.example.doctors.view_model.AppointmentViewModel
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun ChangeDate(
    chooseDate: Calendar,
    changeDate: (newDate: Calendar) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        elevation = 10.dp
    ) {
        ArrowsBtn(80.dp, Color.Gray, PaddingValues(end = 16.dp)) { step ->
            getNewDate(step, chooseDate) {newDate ->
                changeDate(newDate)
            }
        }
        ListDate(getListCurrentDate(chooseDate = chooseDate))

    }
}

private fun getListCurrentDate(chooseDate: Calendar): List<Calendar> {
    val previousDate = (chooseDate.clone() as Calendar)
    previousDate.add(Calendar.DATE, -1)

    val nextDate = (chooseDate.clone() as Calendar)
    nextDate.add(Calendar.DATE, 1)

    return listOf(previousDate, chooseDate, nextDate)
}

private fun getNewDate(number: Int, currentDate: Calendar, changeDate: (newDate: Calendar) -> Unit) {
    val tempDate = Calendar.getInstance()
    tempDate.time = currentDate.time
    tempDate.add(Calendar.DATE, number)
    changeDate(tempDate)
}