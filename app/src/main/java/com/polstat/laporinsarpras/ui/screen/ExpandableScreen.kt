package com.polstat.laporinsarpras.ui.screen

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.polstat.laporinsarpras.model.SampleData
import com.polstat.laporinsarpras.ui.theme.Green
import com.polstat.laporinsarpras.ui.utils.Constants.CollapseAnimation
import com.polstat.laporinsarpras.ui.utils.Constants.ExpandAnimation
import com.polstat.laporinsarpras.ui.utils.Constants.FadeInAnimation
import com.polstat.laporinsarpras.ui.utils.Constants.FadeOutAnimation
import com.polstat.laporinsarpras.ui.viewmodel.ExpandableViewModel

@ExperimentalAnimationApi
@Composable
fun ExpandableScreen(viewModel: ExpandableViewModel) {
    val cards = viewModel.cards.collectAsState()
    val expandedCard = viewModel.expandedCardList.collectAsState()

    LazyColumn {
        itemsIndexed(cards.value) { _, card ->
            ExpandableCard(
                card = card,
                onCardArrowClick = { viewModel.cardArrowClick(card.id) },
                expanded = expandedCard.value.contains(card.id)
            )
        }
    }
}
@ExperimentalAnimationApi
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ExpandableCard(
    card: SampleData,
    onCardArrowClick: () -> Unit,
    expanded: Boolean
) {
    val transitionState = remember { MutableTransitionState(expanded).apply {
        targetState = !expanded
    }}
    val transition = updateTransition(targetState = transitionState, label = "transition")
    val cardBgColor by transition.animateColor({
        tween(durationMillis = ExpandAnimation)
    }, label = "bgColorTransition") {
        if (expanded) Green else Green
    }
    val cardPaddingHorizontal by transition.animateDp({
        tween(durationMillis = ExpandAnimation)
    }, label = "paddingTransition") {
        20.dp
    }
    val cardElevation by transition.animateDp({
        tween(durationMillis = ExpandAnimation)
    }, label = "elevationTransition") {
        if (expanded) 20.dp else 5.dp
    }
    val cardRoundedCorners by transition.animateDp({
        tween(
            durationMillis = ExpandAnimation,
            easing = FastOutSlowInEasing
        )
    }, label = "cornersTransition") {
        15.dp
    }
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = ExpandAnimation)
    }, label = "rotationDegreeTransition") {
        if (expanded) 0f else 180f
    }

    Card(
        backgroundColor = cardBgColor,
        elevation = cardElevation,
        shape = RoundedCornerShape(cardRoundedCorners),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = cardPaddingHorizontal,
                vertical = 8.dp
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box {
               Row(
                   modifier = Modifier.fillMaxWidth()
               ) {
                   Column(
                       modifier = Modifier.weight(0.85f)
                   ) {
                       Text(
                           text = card.title,
                           color = Color.White,
                           textAlign = TextAlign.Start,
                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(12.dp)
                       )
                   }
                   Column(
                       modifier = Modifier.weight(0.15f)
                   ) {
                       CardArrow(
                           degrees = arrowRotationDegree,
                           onClick = onCardArrowClick
                       )
                   }
               }
            }
            ExpandableContent(expanded)
        }
    }
}

@Composable
fun CardArrow(
    degrees: Float,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        content = {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowUp,
                modifier = Modifier.rotate(degrees),
                contentDescription = "Expandable Arrow",
                tint = Color.White
            )
        }
    )
}

@ExperimentalAnimationApi
@Composable
fun ExpandableContent(expanded: Boolean = true) {
    val enterFadeIn = remember {
        fadeIn(
            animationSpec = TweenSpec(
                durationMillis = FadeInAnimation,
                easing = FastOutLinearInEasing
            )
        )
    }
    val enterExpand = remember {
        expandVertically(animationSpec = tween(ExpandAnimation))
    }
    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                durationMillis = FadeOutAnimation,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween(CollapseAnimation))
    }

    AnimatedVisibility(
        visible = expanded,
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
        ) {
            Text(
                text = "Make It Easy Description",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                color = Green
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun ExpandableScreenPrevew() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
        val expandableViewModel = ExpandableViewModel()
        ExpandableScreen(viewModel = expandableViewModel)
    }
}