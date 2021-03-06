package com.example.doctors.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.doctors.R
import com.example.doctors.Screen
import com.example.doctors.view_model.DoctorsListViewModel
import com.example.doctors.entities.Doctor
import com.example.doctors.ui.views.main.doctors.chooseDoctor.KeyForSort
import com.example.doctors.ui.views.main.doctors.chooseDoctor.keysForSort
import com.example.doctors.view_model.AuthorizationViewModel
import org.intellij.lang.annotations.JdkConstants


@Composable
fun ChooseDoctor(navController: NavController) {
    Column(Modifier.padding(top = 32.dp, start = 8.dp)) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Title()
            ButtonSignOut(navController = navController)
        }

        val viewModel: DoctorsListViewModel = viewModel()

        if (viewModel.doctors.value == null) {
            viewModel.enableListenerCollection(KeyForSort.RatingDescending)
        }

        val textState = remember { mutableStateOf("") }
        SearchDoctors(textState = textState)

        MySpinner(
            items = keysForSort,
            hint = "Отсортировать список врачей",
            tint = MaterialTheme.colors.primaryVariant,
            padding = PaddingValues(top = 32.dp, start = 8.dp, end = 32.dp),
            onClick = {
                viewModel.disableListenerCollectionPlaces()
                viewModel.enableListenerCollection(it)
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
private fun Title() {

    Column {
        Text(
            text = "Hello, Sema"
        )
        Text(
            text = "Find you doctor",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ListDoctors(
    viewModel: DoctorsListViewModel,
    filterSample: String,
    navController: NavController
) {
    val doctors: List<Doctor> by viewModel.doctors.observeAsState(listOf())
    Log.i("QWE", "sadsad${doctors.size}")
    LazyColumn {
        items(doctors.filter { it ->
            it.name.contains(other = filterSample, ignoreCase = true)
        }, key = { it.id }) { doctor ->
            DoctorItem(doctor = doctor, navController = navController)
        }
    }
}

@Composable
private fun SearchDoctors(textState: MutableState<String>) {
    OutlinedTextField(
        value = textState.value,
        onValueChange = { it -> textState.value = it },
        label = { Text("Введите фамилию доктора") },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "search"
            )
        },
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            disabledTrailingIconColor = MaterialTheme.colors.primaryVariant
        )
    )
}

@Composable
private fun ButtonSignOut(navController: NavController) {
    val authViewModel: AuthorizationViewModel = viewModel()

    TextButton(onClick = {
        authViewModel.signOut()
        navController.navigate(Screen.SignIn.route) {
            launchSingleTop = true
        }
    }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_exit),
            contentDescription = "sign out",
            modifier = Modifier
                .height(30.dp)
                .width(30.dp)
        )
    }
}

