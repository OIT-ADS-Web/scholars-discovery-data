package duke

import com.fasterxml.jackson.databind.ObjectMapper

import java.nio.file.Files
import java.nio.file.Paths
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

// fun gatherFiles() {
//    Beginner Person document needs:
//    1) positions (just current)
//    2) educations
//    3) organizations
//    4) research areas (concepts)
//    5) publications
//    6) publication venues ...
// }
// fun convertFiles() {
//
// }
// gradle run --args='foo --bar'
fun main(args: Array<String>) {
    val mapper = ObjectMapper().registerModule(KotlinModule())
    val json = String(Files.readAllBytes(Paths.get("data-input/per0000001.json")))

    val doc = mapper.readValue<Person>(json)
    println(doc)

    // collect all ids? from syncids?
    val converter = PersonConverter()
    val personDto = converter.convert(doc)
    println(personDto)

    val file = File("data-output/per0000001.json")
    mapper.writerWithDefaultPrettyPrinter().writeValue(file, personDto)
}