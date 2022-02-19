package com.example.doctors.main.doctors_list.appointment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doctors.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class AppointmentFragment : Fragment() {

    private val viewModel: AppointmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.appointment_fragment, container, false)
    }

}