package dev.gumil.profiles.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.gumil.profiles.R

private val SourceSansPro = FontFamily(
    Font(R.font.source_sans_pro_regular),
    Font(R.font.source_sans_pro_semibold, FontWeight.SemiBold),
    Font(R.font.source_sans_pro_bold, FontWeight.Bold)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = SourceSansPro,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.4.sp
    ),
    h5 = TextStyle(
        fontFamily = SourceSansPro,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        letterSpacing = 1.6.sp
    ),
    h4 = TextStyle(
        fontFamily = SourceSansPro,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        letterSpacing = 2.4.sp
    ),
    h6 = TextStyle(
        fontFamily = SourceSansPro,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        letterSpacing = 1.33.sp
    )
)
