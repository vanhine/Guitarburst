# Guitarburst App Design Doc

## Overview

## Features
* P0 - Search
* P0 - Filter
* P0 - Results
* P1 - Favorites

### Search
The search feature allows the user to search across all categories.

The screen will feature an `EditText`, a list of categories, and a `Button`.

* The `EditText` is the search box.
* The list of categories are the categories which the user can select to search.
* The `Button` is the search button which initiates the search.

When a user searches, they will enter their search in the `EditText`, select the category to search, and then tap the `Button` to get the results. The [Results](#Results) are then shown.

### Filter
The filter feature allows the user to browse results based on mutiple category filters.

The screen will feature the following:
* A dropdown for composers
* A dropdown for era
* A `Slider` for difficulty
* A `Slider` for reading
* A `Button` to get the results

When a user filters, they will set the field(s) to query and then tap the `Button` to get the results. The [Results](#Results) are then shown.

### Results
The results feature allows the user to view the results of their search/filter.

The screen will feature a `RecyclerView` which contains the results. Each result represents a piece of music. Each result will contain the title of the piece, the composer, and a favorite `Button`. When the user taps the favorite `Button` that piece will be added to their [favorites list](#Favorites)

### Favorites
The favorites feature allows the user to view and remove pieces from their favorites list.

The screen will feature a  `RecyclerView` which contains their favorites. Each favorite will contain the title of the piece, the composer, and a favorite `Button`.