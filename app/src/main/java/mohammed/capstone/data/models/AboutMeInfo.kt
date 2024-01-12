package mohammed.capstone.data.models

/**
 * Data class representing information about a the portfolio owner for the 'About Me' section.
 *
 * @property name The name of the portfolio owner.
 * @property occupation The occupation or professional title of the person.
 * @property bio A brief biography or description of the person.
 * @property profileImageRes A resource identifier for the person's profile image.
 */
data class AboutMeInfo(
    val name: String,
    val occupation: String,
    val bio: String,
    val profileImageRes: Int
)
