package com.example.lab13

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                    AnimateSizeAndPositionE()
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
