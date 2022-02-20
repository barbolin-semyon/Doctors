package com.example.doctors.main.doctors_list.appointment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctors.databinding.AppointmentFragmentBinding
import com.example.doctors.main.doctors_list.data.PlaceToWrite
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class AppointmentFragment : Fragment() {

    private val viewModel: AppointmentViewModel by viewModel()
    private lateinit var  binding: AppointmentFragmentBinding
    private lateinit var myAdapter: AppointmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AppointmentFragmentBinding.inflate(inflater)

        viewModel.initVariable(
            arguments?.getString("userId")!!,
            arguments?.getString("doctorId")!!
        )

        configureRecyclerView()
        configureObserverLiveData()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.enableListenerCollection(viewModel.doctorId, Date(2022, 2, 20))
    }

    override fun onPause() {
        super.onPause()
        viewModel.disableListenerCollectionPlaces()
    }

    private fun configureObserverLiveData() {
        viewModel.places.observe(viewLifecycleOwner, Observer {
            changeListRecyclerView(it.toList())
        })

        viewModel.showMessage.observe(viewLifecycleOwner, Observer { message ->
            showSnackbar(message)
        })
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun configureRecyclerView() {
        myAdapter = AppointmentAdapter(viewModel)

        with(binding.appointmentRecyclerView) {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun changeListRecyclerView(newListWithPlaces: List<PlaceToWrite>) {
        myAdapter.changeItems(newListWithPlaces)
    }


}