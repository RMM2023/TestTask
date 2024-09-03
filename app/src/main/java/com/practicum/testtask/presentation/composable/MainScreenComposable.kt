package com.practicum.testtask.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.practicum.testtask.data.model.Item
import com.practicum.testtask.presentation.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel()
) {
    val items by viewModel.items.collectAsState(initial = emptyList())
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF87CEEB))
                .padding(PaddingValues(0.dp,32.dp,0.dp,16.dp))
        ) {
            Text(
                text = "Список товаров",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.searchItems(it)
            },
            label = { Text("Поиск товаров") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingValues(16.dp,8.dp,16.dp,16.dp))
        )

        LazyColumn {
            items(items) { item ->
                ItemCard(
                    item = item,
                    onAmountChange = { newAmount -> viewModel.updateItemAmount(item.id, newAmount) },
                    onDelete = { viewModel.deleteItem(item) }
                )
            }
        }
    }
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ItemCard(
    item: Item,
    onAmountChange: (Int) -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(PaddingValues(0.dp,8.dp,0.dp,0.dp))
                )
                Row(modifier = Modifier.align(Alignment.CenterVertically)) {
                    IconButton(onClick = { showEditDialog = true }) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.Blue)
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.Red)
                    }
                }
            }
            FlowRow(modifier = Modifier.padding(vertical = 8.dp)) {
                item.tags.forEach { tag ->
                    AssistChip(
                        onClick = { },
                        label = { Text(tag) },
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("На складе:")
                    Text(
                        text = item.amount.toString(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("Дата добавления:")
                    Text(
                        text = formatDate(item.time),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            onConfirm = {
                onDelete()
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    if (showEditDialog) {
        EditAmountDialog(
            currentAmount = item.amount,
            onConfirm = { newAmount ->
                onAmountChange(newAmount)
                showEditDialog = false
            },
            onDismiss = { showEditDialog = false }
        )
    }
}


@Composable
fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Delete Icon",
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
            )
        },
        title = {
            Text(
                "Удаление товара",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = { Text("Вы действительно хотите удалить выбранный товар?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Да")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Нет")
            }
        }
    )
}

@Composable
fun EditAmountDialog(
    currentAmount: Int,
    onConfirm: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var amount by remember { mutableStateOf(currentAmount) }

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Edit Icon",
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
            )
        },
        title = { Text("Количество товара") },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { if (amount > 0) amount-- },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                ) {
                    Icon(
                        Icons.Outlined.Remove,
                        contentDescription = "Decrease",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = amount.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                IconButton(
                    onClick = { amount++ },
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .border(6.dp, MaterialTheme.colorScheme.primary, CircleShape)
                ) {
                    Icon(
                        Icons.Outlined.AddCircle,
                        contentDescription = "Increase",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(amount) }) {
                Text("Принять")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}