package duke

data class Organization (
        val id: String,
        val label: String,
        val parent: Organization? = null
)
