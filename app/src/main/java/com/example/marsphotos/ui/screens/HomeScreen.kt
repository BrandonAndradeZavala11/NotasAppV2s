
package com.example.marsphotos.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.marsphotos.R
import com.example.marsphotos.model.Nota
import com.example.marsphotos.model.NotaDT


@Composable
fun HomeScreen(
    viewModel: MarsViewModel,
    marsUiState: MarsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (marsUiState) {
        is MarsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())

        is MarsUiState.Success -> PhotosGridScreen(
            photos = marsUiState.photos,
            viewModel = viewModel,
            modifier = modifier)

        is MarsUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}


@Composable
fun PhotosGridScreen(
    photos: List<Nota>,
    viewModel: MarsViewModel,
    modifier: Modifier = Modifier) {
    var searchQuery by remember { mutableStateOf("") }
    var titulo by remember { mutableStateOf("") }
    var contenido by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }
    var estatus by remember { mutableStateOf("") }
    var fechacum by remember { mutableStateOf("") }
    var fechamodi by remember { mutableStateOf("") }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar Nota") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                ) {
                    TextField(
                        value = titulo,
                        onValueChange = { titulo = it },
                        label = { Text("Titulo Nota") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(2.dp)
                    )
                    TextField(
                        value = contenido,
                        onValueChange = { contenido = it },
                        label = { Text("Descripcion Nota") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(2.dp)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                ){
                    TextField(
                        value = estatus,
                        onValueChange = { estatus = it },
                        label = { Text("Estatus") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(2.dp)
                    )

                    TextField(
                        value = tipo,
                        onValueChange = { tipo = it },
                        label = { Text("Tipo") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(2.dp)
                    )
                }

            }

            item{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                ){
                    TextField(
                        value = ubicacion,
                        onValueChange = { ubicacion = it },
                        label = { Text("Ubicacion Nota") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(2.dp)
                    )

                    TextField(
                        value = fecha,
                        onValueChange = { fecha = it },
                        label = { Text("Fecha Nota") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(2.dp)
                    )
                }

            }

            item{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                ){
                    TextField(
                        value = fechamodi,
                        onValueChange = { fechamodi = it },
                        label = { Text("FechaMod Nota") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(2.dp)
                    )

                    TextField(
                        value = fechacum,
                        onValueChange = { fechacum = it },
                        label = { Text("FechaCum Nota") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(2.dp)
                    )
                }

            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center

        ) {
            Button(onClick = {
                val nuevaNota = NotaDT(
                    titulo = titulo,
                    contenido = contenido,
                    estatus = estatus.toInt(),
                    tipo = tipo.toInt(),
                    ubicacion = ubicacion,
                    fecha = fecha,
                    fechaModi = fechamodi,
                    fechaCum = fechacum

                )
                viewModel.addNotaTarea(nuevaNota)
            }) {
                Text("Agregar Nota")
            }
            Button(
                onClick = {

            }) {
                Text("Editar Nota")
            }
            titulo = ""
            contenido = ""
            estatus = ""
            tipo = ""
            ubicacion = ""
            fecha = ""
            fechamodi = ""
            fechacum = ""

        }

        val filteredPhotos = if (searchQuery.isNotBlank()) {
            photos.filter { nota ->
                nota.titulo.contains(searchQuery, ignoreCase = true) ||
                        nota.id.toString().contains(searchQuery, ignoreCase = true)
            }
        } else {
            photos
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(130.dp),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(6.dp)
        ) {
            items(items = filteredPhotos) { photo ->
                NotaTareaCard(
                    photo = photo,
                    modifier = modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    onDeleteClick = { id -> viewModel.deleteNotaTarea(id) },
                )
            }
        }
    }
}

@Composable
fun NotaTareaCard(
    photo: Nota,
    modifier: Modifier = Modifier,
    onDeleteClick: (Int) -> Unit

) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "ID: ${photo.id}",
                style = TextStyle(fontWeight = FontWeight.Normal),
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "Nombre: ${photo.titulo}",
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "Contenido: ${photo.contenido}",
                style = TextStyle(fontWeight = FontWeight.Normal),
                modifier = Modifier.padding(8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Botón Editar (con lógica para editar)
                Button(
                    onClick = { },
                ) {
                    Text("Editar", fontSize = 12.sp)
                }

                // Botón Eliminar (con lógica para eliminar)
                Button(
                    onClick = { onDeleteClick(photo.id) },
                ) {
                    Text("Elim", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }

    }
}

