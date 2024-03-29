package com.example.doctors.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.doctors.view_model.DoctorsListViewModel
import com.example.doctors.entities.Doctor
import com.example.doctors.ui.components.ButtonSignOut
import com.example.doctors.ui.components.SearchText
import com.example.doctors.ui.components.spiner.KeyForSort
import com.example.doctors.ui.components.spiner.MySpinner
import com.example.doctors.ui.components.spiner.keysForSort


@Composable
fun ChooseDoctor(navController: NavController) {
    Column(Modifier.padding(top = 32.dp, start = 8.dp, end = 8.dp)) {
        val viewModel: DoctorsListViewModel = viewModel()
        if (viewModel.doctors.value == null) {
            viewModel.enableListenerCollectionDoctor(KeyForSort.RatingDescending)
        }

        ToolbarDoctor(navController = navController)

        val textState = remember { mutableStateOf("") }
        SearchText(textState = textState, labelText = "Введите фамилию доктора")

        MySpinner(
            items = keysForSort,
            hint = "Отсортировать список врачей",
            tint = MaterialTheme.colors.primaryVariant,
            padding = PaddingValues(top = 32.dp, start = 8.dp, end = 32.dp),
            onClick = {
                viewModel.disableListenerCollectionPlaces()
                viewModel.enableListenerCollectionDoctor(it)
            }
        )

        ListDoctors(
            viewModel = viewModel,
            filterSample = textState.value,
            navController = navController
        )
    }
}

@Composable
private fun ToolbarDoctor(navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Find you doctor",
            fontSize = 36.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        ButtonSignOut(navController = navController)
    }
}

@Composable
private fun ListDoctors(
    viewModel: DoctorsListViewModel,
    filterSample: String,
    navController: NavController
) {
    val doctors: List<Doctor> by viewModel.doctors.observeAsState(listOf())

    LazyColumn(Modifier.padding(start = 0.dp)) {
        items(
            items = doctors.filter { it ->
                it.name.contains(other = filterSample, ignoreCase = true)
            },
            key = { it.id }
        ) { doctor ->
            DoctorItem(doctor = doctor, navController = navController)
        }
    }
}

