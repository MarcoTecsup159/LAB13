package com.example.lab13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab13.ui.theme.LAB13Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB13Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // AnimatedVisibilityE()
                    // AnimateColorE()
                    //AnimateSizeAndPositionE()
                    //AnimatedContentE()
                    AnimationsCombinedScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedVisibilityE() {
    var isVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ElevatedButton(
            onClick = { isVisible = !isVisible },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = if (isVisible) "Ocultar" else "Mostrar"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(initialAlpha = 0.3f) +
                    expandVertically(expandFrom = Alignment.Top) +
                    slideInVertically(),
            exit = fadeOut() +
                    shrinkVertically() +
                    slideOutVertically()
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Marco Rios",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun AnimateColorE() {

    var isBlue by remember { mutableStateOf(true) }

    val blueColor = Color(0xFF2196F3)
    val greenColor = Color(0xFF4CAF50)

    val backgroundColor by animateColorAsState(
        targetValue = if (isBlue) blueColor else greenColor,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ElevatedButton(
            onClick = { isBlue = !isBlue },
            modifier = Modifier.padding(bottom = 16.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = if (isBlue) greenColor else blueColor
            )
        ) {
            Text(
                text = if (isBlue) "Cambiar a Verde" else "Cambiar a Azul",
                fontSize = 16.sp,
                modifier = Modifier.padding(8.dp),
                color = Color.White
            )
        }

        Box(
            modifier = Modifier
                .size(150.dp)
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isBlue) "Azul" else "Verde",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun AnimateSizeAndPositionE() {
    var isExpanded by remember { mutableStateOf(false) }

    //animar tamaño
    val boxSize by animateDpAsState(
        targetValue = if (isExpanded) 200.dp else 100.dp,
        animationSpec = tween(durationMillis = 600),
        label = "boxSizeAnimation"
    )

    //animar posición
    val offsetX by animateDpAsState(
        targetValue = if (isExpanded) 100.dp else 0.dp,
        animationSpec = tween(durationMillis = 600),
        label = "offsetXAnimation"
    )
    val offsetY by animateDpAsState(
        targetValue = if (isExpanded) 100.dp else 0.dp,
        animationSpec = tween(durationMillis = 600),
        label = "offsetYAnimation"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ElevatedButton(
            onClick = { isExpanded = !isExpanded },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(
                text = if (isExpanded) "Reducir y Mover Atrás" else "Expandir y Mover",
                fontSize = 16.sp
            )
        }

        Box(
            modifier = Modifier
                .size(boxSize)
                .offset(x = offsetX, y = offsetY)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Marco",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentE() {
    var currentState by remember { mutableStateOf("Cargando") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ElevatedButton(onClick = { currentState = "Cargando" }) {
                Text("Cargando")
            }
            ElevatedButton(onClick = { currentState = "Contenido" }) {
                Text("Contenido")
            }
            ElevatedButton(onClick = { currentState = "Error" }) {
                Text("Error")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        //transcion del contenido
        AnimatedContent(
            targetState = currentState,
            transitionSpec = {
                fadeIn(tween(500)) with fadeOut(tween(500))
            },
            label = "contentTransition"
        ) { targetState ->
            when (targetState) {
                "Cargando" -> {
                    Text(
                        text = "Cargando...",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                "Contenido" -> {
                    Text(
                        text = "¡Contenido cargado con éxito!",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                "Error" -> {
                    Text(
                        text = "Ha ocurrido un error. Intenta nuevamente.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                else -> {}
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimationsCombinedScreen() {
    var isDarkMode by remember { mutableStateOf(false) }
    var isBoxExpanded by remember { mutableStateOf(false) }
    var isButtonVisible by remember { mutableStateOf(true) }

    // Animacion del box
    val boxSize by animateDpAsState(
        targetValue = if (isBoxExpanded) 200.dp else 100.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "boxSize"
    )

    val boxColor by animateColorAsState(
        targetValue = if (isBoxExpanded)
            Color(0xFF2196F3) else Color(0xFF4CAF50),
        animationSpec = tween(durationMillis = 500),
        label = "boxColor"
    )

    // rotación para el ícono
    val infiniteRotation = rememberInfiniteTransition(label = "iconRotation")
    val iconRotation by infiniteRotation.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDarkMode) Color(0xFF121212) else Color.White)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(boxSize)
                    .clip(RoundedCornerShape(16.dp))
                    .background(boxColor)
                    .clickable { isBoxExpanded = !isBoxExpanded }
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isBoxExpanded) "Contraer" else "Expandir",
                    color = Color.White,
                    fontSize = if (isBoxExpanded) 18.sp else 14.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(
                visible = isButtonVisible,
                enter = slideInHorizontally() + fadeIn(),
                exit = slideOutHorizontally() + fadeOut()
            ) {
                Button(
                    onClick = { isButtonVisible = false },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Desaparecer")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para modo claro/oscuro
            IconButton(
                onClick = { isDarkMode = !isDarkMode },
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(if (isDarkMode) Color(0xFF303030) else Color(0xFFE0E0E0))
            ) {
                Icon(
                    imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = "Cambiar tema",
                    modifier = Modifier
                        .size(32.dp)
                        .graphicsLayer {
                            rotationZ = iconRotation
                        },
                    tint = if (isDarkMode) Color.White else Color.Black
                )
            }

            TextButton(
                onClick = {
                    isButtonVisible = true
                    isBoxExpanded = false
                },
                modifier = Modifier.padding(top = 32.dp)
            ) {
                Text(
                    "Resetear Animaciones",
                    color = if (isDarkMode) Color.White else Color.Black
                )
            }
        }
    }
}


