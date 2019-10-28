package duke

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

// incoming
data class Person (
        val id: String,
        val name: String,
        val type: Set<String> = mutableSetOf(),
        var preferredTitle: String,
        var primaryEmail: String? = null,
        var overview: String? = null,
        var netid: String? =  null,
        var uid: String? =  null,
        var firstName: String? =  null,
        var lastName: String? =  null,
        var streetAddress: String? =  null,
        var locality: String? =  null,
        var region: String? =  null,
        var postalCode: String? =  null,
        var country: String? =  null,
        var modTime: String? =  null,
        val positions: List<Position> = mutableListOf(),
        val educations: List<Education> = mutableListOf(),
        val publications: List<Publication> = mutableListOf(),
        val teachingActivities: List<TeachingActivity> = mutableListOf(),
        val researchAreas: List<Concept> = mutableListOf()
)

// outgoing
//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class FlattenedPerson (
        val id: String,
        @JsonProperty("class") val clazz: String,
        val name: String,
        var type: Set<String> = mutableSetOf(),
        var overview: String? =  null,
        var netid: String? =  null,
        var uid: String? =  null,
        var firstName: String? =  null,
        var lastName: String? =  null,
        var streetAddress: String? =  null,
        var locality: String? =  null,
        var region: String? =  null,
        var postalCode: String? =  null,
        var country: String? =  null,
        var modTime: String? =  null,
        var hrJobTitle: String? = null,
        // positions
        var positions: List<String> = mutableListOf(),
        var positionType: List<String> = mutableListOf(),
        var positionOrganization: List<String> = mutableListOf(),
        var positionOrganizationParent: List<String> = mutableListOf(),
        // educations
        var educationAndTraining: List<String> = mutableListOf(),
        var educationAndTrainingMajorField: List<String> = mutableListOf(),
        var educationAndTrainingDegreeAbbreviation: List<String> = mutableListOf(),
        var educationAndTrainingEndDate: List<String> = mutableListOf(),
        var educationAndTrainingOrganization: List<String> = mutableListOf(),
        // publications
        var publications: List<String> = mutableListOf(),
        var selectedPublicationType: List<String> = mutableListOf(),
        var selectedPublicationVenue: List<String> = mutableListOf(),
        var selectedPublicationVenueType: List<String> = mutableListOf(),
        var selectedPublicationDate: List<String> = mutableListOf(),
        // teaching activities
        var teachingActivities: List<String> = mutableListOf(),
        var teachingActivityRole: List<String> = mutableListOf(),
        var researchAreas: List<String> = mutableListOf(),
        var syncIds: Set<String> = mutableSetOf()
)

class PersonConverter {

    // uuid? collect every id (+type) for further incoming ??
    fun convert(person: Person): FlattenedPerson {
        val positions = mutableListOf<String>()
        val positionTypes = mutableListOf<String>()
        val positionOrganizations = mutableListOf<String>()
        val positionOrganizationParent = mutableListOf<String>();

        val educations = mutableListOf<String>()
        val educationOrganizations = mutableListOf<String>()
        val educationMajorFields = mutableListOf<String>()
        val educationDegreeAbbreviations = mutableListOf<String>();
        val educationEndDates = mutableListOf<String>();

        val teachingActivities = mutableListOf<String>()
        val teachingActivityRoles = mutableListOf<String>()

        val publications = mutableListOf<String>()
        val publicationTypes = mutableListOf<String>()
        val publicationVenues = mutableListOf<String>()
        val publicationVenueTypes = mutableListOf<String>()
        val publicationDates = mutableListOf<String>()

        val researchAreas = mutableListOf<String>()

        val syncIds = mutableSetOf<String>()
        //val personId = person.id
        // gather all ids into syncIds ??
        for (x in person.positions) {
            positions.add("${x.label}::${x.id}")
            positionTypes.add("${x.type}::${x.id}")
            //Veterinary Pathobiology::pos0000001::org0000001
            positionOrganizations.add("${x.organization.label}::${x.id}::${x.organization.id}")

            val parent = x.organization.parent
            parent?.let {
                //"College of Veterinary Medicine and Biomedical Sciences::pos0000001::org0000001::org0000002"
                positionOrganizationParent.add("${it.label}::${x.id}::${x.organization.id}::${it.id}")
                syncIds.add(it.id)
            }
            syncIds.add(x.id)
            syncIds.add(x.organization.id)
        }

        for (x in person.educations) {
            educations.add("${x.label}::${x.id}")
            educationDegreeAbbreviations.add("${x.degreeAbbreviation}::${x.id}")
            educationMajorFields.add("${x.majorField}::${x.id}")
            educationEndDates.add("${x.endDate}::${x.id}")
            // "University of Maryland, College Park::n5889f585_b88dbbed_730827c3::nb88dbbed",
            educationOrganizations.add("${x.organization.label}::${x.id}::${x.organization.id}")
            syncIds.add(x.id)
        }

        for (x in person.teachingActivities) {
            //"VTMI685 Directed Studies::nb1f81e64",
            teachingActivities.add("${x.label} ${x.title}::${x.id}")
            //"Instructor::nb1f81e64",
            teachingActivityRoles.add("${x.role}::${x.id}")
            syncIds.add(x.id)
        }

        for (x in person.publications) {
            // "Recombinant antigen-based enzyme-linked immunosorbent assay for diagnosis of Baylisascaris procyonis larva migrans::n159500SE",
            //title
            publications.add("${x.title}::${x.id}")
            //"AcademicArticle::n159500SE",
            publicationTypes.add("${x.type}::${x.id}")

            val venue = x.venue
            venue?.let {
                // "JOURNAL OF VETERINARY DIAGNOSTIC INVESTIGATION::n301826SE::n1040-6387ISSN",
                publicationVenues.add("${it.name}::${x.id}::${it.id}")
                publicationVenueTypes.add("${it.type}::${x.id}::${it.id}")
                // "Journal::n301799SE::n1040-6387ISSN",
                syncIds.add(it.id)
            }

            val date = x.date
            date?.let {
                // "2011-01-01T00:00:00::n159500SE",
                publicationDates.add("${it}::${x.id}")
            }
            syncIds.add(x.id)
        }

        for (x in person.researchAreas) {
            researchAreas.add("${x.value}::${x.id}")
            syncIds.add(x.id)
        }

        // classes: Person, Process, Relationship etc...
        return FlattenedPerson(
                name = person.name,
                id = person.id,
                clazz = person.javaClass.simpleName,
                type = person.type,
                overview = person.overview,
                netid = person.netid,
                uid = person.uid,
                firstName = person.firstName,
                lastName = person.lastName,
                streetAddress = person.streetAddress,
                locality = person.locality,
                region = person.region,
                postalCode = person.postalCode,
                country = person.country,
                modTime = person.modTime,
                // positions
                positions = positions,
                positionType = positionTypes,
                positionOrganization = positionOrganizations,
                positionOrganizationParent = positionOrganizationParent,
                // educations
                educationAndTraining = educations,
                educationAndTrainingMajorField = educationMajorFields,
                educationAndTrainingDegreeAbbreviation = educationDegreeAbbreviations,
                educationAndTrainingEndDate = educationEndDates,
                educationAndTrainingOrganization = educationOrganizations,
                // teaching activities
                teachingActivities = teachingActivities,
                teachingActivityRole = teachingActivityRoles,
                // publications
                publications = publications,
                selectedPublicationDate = publicationDates,
                selectedPublicationType = publicationTypes,
                selectedPublicationVenue = publicationVenues,
                selectedPublicationVenueType = publicationVenueTypes,
                // research areas
                researchAreas = researchAreas,
                // syncIds
                syncIds = syncIds
        )

    }
}

