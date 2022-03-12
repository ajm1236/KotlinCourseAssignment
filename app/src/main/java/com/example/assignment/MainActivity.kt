package com.example.assignment


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignment.ui.theme.AssignmentTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AssignmentTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Sorting()
                }
            }
        }
    }
}


@Composable
fun Welcome() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFA277F0)),
        contentAlignment = Alignment.Center,


    ){

        Text(text = "Expense Tracker\n\n",fontSize = 50.sp)
        Text("Track your expenses here!", fontSize = 25.sp)
    }

}

@Composable
fun ExpensesView(){
    var expenseString = remember { mutableStateOf("")}
    var costString = remember { mutableStateOf("")}

    val eVM: ExpenseData = viewModel(LocalContext.current as ComponentActivity)
    val fs = Firebase.firestore

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFA277F0))
        .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center


    ){
        Text("Add expense item and cost here", fontSize = 25.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)
        WorkingInput(label = "Expense", state = expenseString )
        WorkingInput(label = "Cost", state = costString )

        Spacer(modifier = Modifier.height(5.dp))

        OutlinedButton(onClick = {
            eVM.expenses.add(Expenses(expenseString.value, costString.value.toFloat()))
            fs.collection("expenses")
                .add(Expenses(expenseString.value, costString.value.toFloat()))
                .addOnSuccessListener{u -> Log.i("***", "Expense added")}
                .addOnFailureListener{e -> Log.i("***", e.toString())}
            expenseString.value = ""
            costString.value = ""
           }
        ){
            Text("Add an Expense" )
        }// end of button content
    }
}

@Composable
fun WorkingInput(label: String, state: MutableState<String>){
    OutlinedTextField(
        value = state.value,
        onValueChange = {
            state.value = it
                        },
        label = { Text(label)}
    )
}

@Composable
fun DisplayContent(){
    val eVM: ExpenseData = viewModel(LocalContext.current as ComponentActivity)
//    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFA277F0))
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Text("Expenses", fontSize = 45.sp, fontWeight = FontWeight.Bold)
        eVM.expenses.forEach{
            Text(it.expense + "= " + "€"+ it.cost, fontSize = 25.sp)
        }
        Column(
            modifier = Modifier
                .background(Color(0xFFA277F0))
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ){
            Text(calculateCost(), fontSize = 30.sp, fontWeight = FontWeight.Bold)
        }


    }
}

@Composable
fun calculateCost(): String {
    val eVM: ExpenseData = viewModel(LocalContext.current as ComponentActivity)

    var total= 0.0
    val string1: String
    eVM.expenses.forEach {
        total += it.cost
    }
    val roundedTotal = BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN)
    string1 = "Total: €$roundedTotal"
    return string1
}
// --Unsuccessful attempt at getting firestore data--
//@Composable
//fun DisplayExpenses() {
//    val eVM: ExpenseData = viewModel()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFA277F0))
//            .padding(15.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text("Expenses", fontSize = 45.sp, fontWeight = FontWeight.Bold)
//        eVM.expenses.value.forEach {
//            Text(it.expense + "= " + "€" + it.cost, fontSize = 20.sp)
//        }
//        //Text(calculateCost(), fontSize = 25.sp, fontWeight = FontWeight.Bold)
//        OutlinedButton(onClick = { eVM.getExpenses() }) {
//            Text("Get Expenses")
//        }
//
//    }
//}