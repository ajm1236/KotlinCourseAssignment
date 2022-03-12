package com.example.assignment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Sorting(){
    val nav = rememberNavController()

    Scaffold(
        topBar = {TopBar()},
        bottomBar = { BottomNav(nav) },


    ){
        NavHost(navController = nav, startDestination = "home") {
            composable(route = "home")
            {
                Welcome()
            }
            composable(route = "expenses")
            {
                ExpensesView()
            }
            composable(route = "news")
            {
                DisplayContent()
            }
        }
    }
}

@Composable
fun TopBar(){
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color(0xFF5A14D6))
    ){

    }
}

@Composable
fun BottomNav(nav: NavHostController){
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color(0xFF5A14D6))
    ){
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_home_24),
            contentDescription = "",
            modifier = Modifier.clickable { nav.navigate("home") }
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_attach_money_24),
            contentDescription = "",
            modifier = Modifier.clickable { nav.navigate("expenses") }
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_info_24),
            contentDescription = "" ,
            modifier = Modifier.clickable { nav.navigate("news") }
        )


    }
}