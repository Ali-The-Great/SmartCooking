package com.example.smartcooking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment


class SearchFragment : Fragment() {
    private val selectedCriteria = mutableSetOf<String>()
    private var recipeDescription: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        // Set click listeners for the criteria buttons
        val veganButton = view.findViewById<Button>(R.id.veganButton)
        val glutenFreeButton = view.findViewById<Button>(R.id.GlutenFree)

        veganButton.setOnClickListener {
            toggleCriteriaSelection("Vegan")
            updateButtonAppearance(veganButton, "Vegan")
        }

        glutenFreeButton.setOnClickListener {
            toggleCriteriaSelection("Gluten-Free")
            updateButtonAppearance(glutenFreeButton, "Gluten-Free")
        }

        // Set click listener for the search button
        val searchButton = view.findViewById<Button>(R.id.SearcherButton)
        searchButton.setOnClickListener {
            //           val queryString = buildQueryString()
            // Send the query string to Chat GPT API and handle the response
            // Implement your logic here
        }

        return view
    }

    private fun toggleCriteriaSelection(criteria: String) {
        if (selectedCriteria.contains(criteria)) {
            selectedCriteria.remove(criteria)
        } else {
            selectedCriteria.add(criteria)
        }
    }

    private fun updateButtonAppearance(button: Button, criteria: String) {
        val isSelected = selectedCriteria.contains(criteria)

        /*        val buttonText = if (isSelected) {
            // Criteria selected, show as active
            button.setBackgroundResource(R.drawable.button_selected_background)
            "Active"
        } else {
            // Criteria not selected, show as inactive
            button.setBackgroundResource(R.drawable.button_inactive_background)
            "Inactive"
        }
        button.text = buttonText
    }
*/
        fun buildQueryString(): String {
            val criteriaString = selectedCriteria.joinToString(", ")
            return "$criteriaString Recipe: $recipeDescription"
        }
    }
}


