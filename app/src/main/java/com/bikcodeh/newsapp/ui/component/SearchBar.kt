package com.bikcodeh.newsapp.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bikcodeh.newsapp.ui.screen.viewmodel.MainViewModel

@Composable
fun SearchBar(mainViewModel: MainViewModel) {

    val query = mainViewModel.searchQuery

    val localFocusManager = LocalFocusManager.current
    Card(
        elevation = 6.dp,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        backgroundColor = MaterialTheme.colors.primary
    ) {
        TextField(
            value = query.value,
            onValueChange = {
                mainViewModel.updateQuery(it)
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Search", color = Color.White)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
            },
            trailingIcon = {
                if (query.value.isNotEmpty()) {
                    IconButton(onClick = {
                        query.value = ""
                        mainViewModel.clearSearch()
                    }) {
                        Icon(Icons.Default.Close, contentDescription = null, tint = Color.White)
                    }
                }
            },
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 18.sp
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (query.value.isNotEmpty()) {
                        mainViewModel.getSearchArticles(query.value)
                    }
                    localFocusManager.clearFocus()
                }
            ),
            colors = TextFieldDefaults.textFieldColors(textColor = Color.White)
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    SearchBar(viewModel())
}