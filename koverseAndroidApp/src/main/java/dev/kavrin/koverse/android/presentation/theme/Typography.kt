package dev.kavrin.koverse.android.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

//val FontFamily.Cursive = FontFamily(
//    Font(R.font.plus_jakarta_sans_bold, weight = FontWeight.Bold),
//    Font(R.font.plus_jakarta_sans_normal, weight = FontWeight.Normal),
//    Font(R.font.plus_jakarta_sans_medium_italic, weight = FontWeight.Medium, style = FontStyle.Italic),
//    Font(R.font.plus_jakarta_sans_medium, weight = FontWeight.Medium, style = FontStyle.Normal),
//    Font(R.font.plus_jakarta_sans_semi_bold, weight = FontWeight.SemiBold),
//)

val typography: Typography =
     Typography(
         displaySmall = TextStyle(
             fontFamily = FontFamily.Cursive,
             fontWeight = FontWeight.Medium,
             fontSize = 24.sp,
             lineHeight = 28.sp,
             fontStyle = FontStyle.Italic
         ),
         headlineLarge = TextStyle(
             fontFamily = FontFamily.Cursive,
             fontWeight = FontWeight.SemiBold,
             fontSize = 20.sp,
             lineHeight = 26.sp,
         ),
         headlineMedium = TextStyle(
             fontFamily = FontFamily.Cursive,
             fontWeight = FontWeight.Medium,
             fontSize = 18.sp,
             lineHeight = 21.sp,
         ),
         headlineSmall = TextStyle(
             fontFamily = FontFamily.Cursive,
             fontWeight = FontWeight.Medium,
             fontSize = 16.sp,
             lineHeight = 17.sp,
         ),
        titleLarge = TextStyle(
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 21.sp,
        ),
         titleMedium = TextStyle(
             fontFamily = FontFamily.Cursive,
             fontWeight = FontWeight.Normal,
             fontSize = 16.sp,
             lineHeight = 21.sp,
         ),
        titleSmall = TextStyle(
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 17.sp,
            fontStyle = FontStyle.Italic
        ),
         bodyLarge = TextStyle(
             fontFamily = FontFamily.Cursive,
             fontWeight = FontWeight.Medium,
             fontSize = 14.sp,
             lineHeight = 21.sp,
         ),
         bodyMedium = TextStyle(
             fontFamily = FontFamily.Cursive,
             fontWeight = FontWeight.Normal,
             fontSize = 14.sp,
             lineHeight = 17.sp,
         ),
        bodySmall = TextStyle(
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            lineHeight = 25.sp,
        ),
         labelLarge = TextStyle(
             fontFamily = FontFamily.Cursive,
             fontWeight = FontWeight.SemiBold,
             fontSize = 14.sp,
             lineHeight = 26.sp,
         ),
    )



