package duke

data class Publication (
        var id: String,
        var title: String,
        var type: String,
        var venue: PublicationVenue? = null,
        var date: String? = null
)

data class PublicationVenue (
    var id: String,
    var name: String,
    var type: String
)