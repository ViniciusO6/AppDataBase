package com.example.appdatabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.appdatabase.roomDB.Pessoa
import com.example.appdatabase.roomDB.PessoaDataBase
import com.example.appdatabase.ui.theme.AppDatabaseTheme
import com.example.appdatabase.viewModel.PessoaViewModel
import com.example.appdatabase.viewModel.Repository



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App(viewModel, this)
        }
    }

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            PessoaDataBase::class.java,
            "pessoa.db"
        ).build()
    }
    private val viewModel by viewModels<PessoaViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PessoaViewModel(Repository(db)) as T
                }
            }
        }
    )
}



@Composable
fun App(viewModel: PessoaViewModel, mainActivity: MainActivity){
    var nome by remember{
        mutableStateOf("")
    }
    var telefone by remember{
        mutableStateOf("")
    }
    var pessoa = Pessoa(
        nome,
        telefone
    )
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier
                .padding(20.dp)){
        }
        Row(
            Modifier.padding(20.dp)
        ) {
            Text(
                text = "App Database",
                fontFamily = FontFamily.Monospace,
                fontSize = 26.sp,
                fontWeight = Bold,
            )
        }
        Row(
            Modifier.padding(8.dp)
        ) {
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome: ")}
            )
        }
        Row(
            Modifier.padding(8.dp)
        ) {
            OutlinedTextField(
                value = telefone,
                onValueChange = { telefone = it },
                label = { Text("Telefone: ")}
            )
        }
        Row{
            Spacer(modifier = Modifier.height(20.dp))
        }
        Row(
            Modifier.padding(8.dp)
        ) {
            Button(onClick = {
                viewModel.upsertPessoa(pessoa)
                nome = ""
                telefone = ""
            }) {
                Text(
                    text = "Cadastrar",
                    fontSize = 20.sp,
                    fontWeight = Bold,
                )
            }



        }
        Row(
            Modifier
                .padding(20.dp)){
        }
    }


}

