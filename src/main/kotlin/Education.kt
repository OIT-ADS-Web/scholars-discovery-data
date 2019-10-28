package duke

data class Education (
        val id: String,
        val label: String,
        val organization: Organization,
        val majorField: String,
        val degreeAbbreviation: String,
        val endDate: String /* string or date */
)

/*
  "educationAndTraining" : [
    "B.V.S. Bachelor of Veterinary Science::n5889f585_95870c26_46bc7310",
    "Ph.D. Doctor of Philosophy::n5889f585_b88dbbed_730827c3",
    "M.V.Sc. Master of Veterinary Science::n5889f585_1a7a14e7_f1a1b214"
  ],
  "educationAndTrainingOrganization" : [
    "Sri Venkateswara Veterinary University::n5889f585_95870c26_46bc7310::n95870c26",
    "University of Maryland, College Park::n5889f585_b88dbbed_730827c3::nb88dbbed",
    "Indian Veterinary Research Institute (ICAR)::n5889f585_1a7a14e7_f1a1b214::n1a7a14e7"
  ],
  "educationAndTrainingMajorField" : [
    "Veterinary Sciences::n5889f585_95870c26_46bc7310",
    "Microbiology and Immunology::n5889f585_b88dbbed_730827c3",
    "Animal Biochemistry::n5889f585_1a7a14e7_f1a1b214"
  ],
  "educationAndTrainingDegreeAbbreviation" : [
    "B.V.S.::n5889f585_95870c26_46bc7310",
    "Ph.D.::n5889f585_b88dbbed_730827c3",
    "M.V.Sc.::n5889f585_1a7a14e7_f1a1b214"
  ],
  "educationAndTrainingEndDate" : [
    "1996-01-01T00:00:00::n5889f585_b88dbbed_730827c3",
    "1989-01-01T00:00:00::n5889f585_1a7a14e7_f1a1b214",
    "1986-01-01T00:00:00::n5889f585_95870c26_46bc7310"
  ],

 */