package duke

data class Position (
        val id: String,
        val label: String,
        val type: String,
        var organization: Organization
)
