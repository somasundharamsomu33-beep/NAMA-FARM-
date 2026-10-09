package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.localization.StringsEn
import com.example.ui.localization.StringsTa
import com.example.ui.theme.UzhavanDarkGreen
import com.example.ui.theme.UzhavanTextPrimary
import com.example.ui.theme.UzhavanTextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FarmerRegistrationScreen(
  isTamil: Boolean,
  onBack: () -> Unit,
  onCompleteRegistration: (name: String, district: String) -> Unit,
  onLoginClick: () -> Unit
) {
  var fullName by remember { mutableStateOf("Ramasamy") }
  var phoneNumber by remember { mutableStateOf("+91 98765 43210") }
  var district by remember { mutableStateOf("Tiruvannamalai") }
  var village by remember { mutableStateOf("Vellore") }
  var password by remember { mutableStateOf("••••••••") }
  var passwordVisible by remember { mutableStateOf(false) }

  var isDistrictExpanded by remember { mutableStateOf(false) }
  val districts = listOf(
    "Tiruvannamalai", "Vellore", "Salem", "Dharmapuri",
    "Madurai", "Coimbatore", "Thanjavur", "Erode"
  )

  Surface(
    modifier = Modifier.fillMaxSize(),
    color = Color.White
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .padding(horizontal = 24.dp, vertical = 12.dp)
        .verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column(modifier = Modifier.fillMaxWidth()) {
        // Top Bar
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          IconButton(
            onClick = onBack,
            modifier = Modifier.testTag("reg_back_button")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = UzhavanDarkGreen
            )
          }
          Text(
            text = if (isTamil) StringsTa.farmerRegTitle else StringsEn.farmerRegTitle,
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Bold,
              color = UzhavanTextPrimary,
              fontSize = 20.sp
            )
          )
        }

        Text(
          text = if (isTamil) StringsTa.step1of2 else StringsEn.step1of2,
          style = MaterialTheme.typography.bodyMedium.copy(
            color = UzhavanDarkGreen,
            fontWeight = FontWeight.SemiBold
          ),
          modifier = Modifier.padding(start = 48.dp, bottom = 20.dp)
        )

        // Field 1: Full Name
        Text(
          text = if (isTamil) StringsTa.fullName else StringsEn.fullName,
          style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.SemiBold,
            color = UzhavanTextPrimary
          )
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
          value = fullName,
          onValueChange = { fullName = it },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("full_name_input"),
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = UzhavanDarkGreen,
            unfocusedBorderColor = Color(0xFFD1DBD4)
          ),
          singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Field 2: Phone Number
        Text(
          text = if (isTamil) StringsTa.phoneNumber else StringsEn.phoneNumber,
          style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.SemiBold,
            color = UzhavanTextPrimary
          )
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
          value = phoneNumber,
          onValueChange = { phoneNumber = it },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("phone_number_input"),
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = UzhavanDarkGreen,
            unfocusedBorderColor = Color(0xFFD1DBD4)
          ),
          singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Field 3: District Dropdown
        Text(
          text = if (isTamil) StringsTa.district else StringsEn.district,
          style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.SemiBold,
            color = UzhavanTextPrimary
          )
        )
        Spacer(modifier = Modifier.height(6.dp))
        ExposedDropdownMenuBox(
          expanded = isDistrictExpanded,
          onExpandedChange = { isDistrictExpanded = !isDistrictExpanded }
        ) {
          OutlinedTextField(
            value = district,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDistrictExpanded) },
            modifier = Modifier
              .menuAnchor()
              .fillMaxWidth()
              .testTag("district_dropdown"),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = UzhavanDarkGreen,
              unfocusedBorderColor = Color(0xFFD1DBD4)
            )
          )
          ExposedDropdownMenu(
            expanded = isDistrictExpanded,
            onDismissRequest = { isDistrictExpanded = false }
          ) {
            districts.forEach { item ->
              DropdownMenuItem(
                text = { Text(item) },
                onClick = {
                  district = item
                  isDistrictExpanded = false
                }
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Field 4: Village
        Text(
          text = if (isTamil) StringsTa.village else StringsEn.village,
          style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.SemiBold,
            color = UzhavanTextPrimary
          )
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
          value = village,
          onValueChange = { village = it },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("village_input"),
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = UzhavanDarkGreen,
            unfocusedBorderColor = Color(0xFFD1DBD4)
          ),
          singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Field 5: Password
        Text(
          text = if (isTamil) StringsTa.password else StringsEn.password,
          style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.SemiBold,
            color = UzhavanTextPrimary
          )
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
          value = password,
          onValueChange = { password = it },
          visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
          trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
              Icon(
                imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                contentDescription = "Toggle password visibility",
                tint = UzhavanTextSecondary
              )
            }
          },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("password_input"),
          shape = RoundedCornerShape(12.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = UzhavanDarkGreen,
            unfocusedBorderColor = Color(0xFFD1DBD4)
          ),
          singleLine = true
        )
      }

      // Bottom Button
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 28.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Button(
          onClick = { onCompleteRegistration(fullName, district) },
          modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .testTag("reg_next_button"),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = UzhavanDarkGreen,
            contentColor = Color.White
          )
        ) {
          Text(
            text = if (isTamil) StringsTa.next else StringsEn.next,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 17.sp
            )
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = if (isTamil) StringsTa.alreadyAccountLogin else StringsEn.alreadyAccountLogin,
          style = MaterialTheme.typography.bodySmall.copy(
            color = UzhavanDarkGreen,
            fontWeight = FontWeight.SemiBold
          ),
          modifier = Modifier
            .clickable { onLoginClick() }
            .padding(8.dp)
        )
      }
    }
  }
}
