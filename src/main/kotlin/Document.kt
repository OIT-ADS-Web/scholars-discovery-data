package duke

data class Document (
        val type: Set<String> = mutableSetOf(),
    var pmcid: String? = null,
    var doi: String? = null,
    var uri: String? = null,
    var title: String? = null
)
