package com.example.expensewizard

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensewizard.data.TransactionViewModel
import com.example.expensewizard.model.Transaction
import com.example.expensewizard.ui.theme.ExpenseWizardTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.expensewizard.client.TransactionAPI
import com.example.expensewizard.data.TransactionDatabase
import com.example.expensewizard.data.TransactionViewModelFactory
import com.example.expensewizard.repository.TransactionRepository
import com.example.expensewizard.ui.components.list.TransactionList
import com.example.expensewizard.ui.pages.addPage.AddPage
import com.example.expensewizard.ui.pages.homePage.HomePage
import com.example.expensewizard.ui.pages.updatePage.UpdatePage

class MainActivity : ComponentActivity(), ToastHandler {

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private lateinit var viewModel: TransactionViewModel;

    private lateinit var transactionRepository: TransactionRepository;
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        transactionRepository = TransactionRepository(App.getDao())
        viewModel = TransactionViewModelFactory(transactionRepository, this).create(TransactionViewModel::class.java)
        setContent {
            ExpenseWizardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Log.i("","HERE")
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomePage(navController = navController,
                                viewModel
                            )
                        }
                        composable("add") {
                            AddPage(navController = navController,
                                viewModel
                            )
                        }
                        composable("update/{transactionId}") {backStackEntry ->
                            val transactionId = backStackEntry.arguments?.getString("transactionId")
                            if (transactionId != null) {
                                UpdatePage(navController = navController,
                                    viewModel,transactionId.toLong())
                            }
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExpenseWizardTheme {
        NavComponent()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavComponent() {}


interface ToastHandler {
    fun showToast(message: String)
}

